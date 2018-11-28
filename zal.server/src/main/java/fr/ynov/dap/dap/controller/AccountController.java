package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import fr.ynov.dap.dap.Config;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.microsoft.IdToken;
import fr.ynov.dap.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.dap.service.google.GoogleAccountService;
import fr.ynov.dap.dap.service.microsoft.MicrosoftAccountService;
import fr.ynov.dap.dap.service.microsoft.OutlookCredentialService;
import fr.ynov.dap.dap.utils.BuildRedirectUri;
import fr.ynov.dap.dap.utils.Constants;

/**
 * The Class AccountController.
 */
@Controller
public class AccountController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(AccountController.class);

	/** The google account service. */
	@Autowired
	private GoogleAccountService googleAccountService;

	/** The app user repository. */
	@Autowired
	private AppUserRepository appUserRepository;

	/** The microsoft account service. */
	@Autowired
	private MicrosoftAccountService microsoftAccountService;

	/** The config. */
	@Autowired
	protected Config config;

	/**
	 * Adds the acount.
	 *
	 * @param accountName
	 *            the account name
	 * @param userKey
	 *            the user key
	 * @param request
	 *            the request
	 * @param session
	 *            the session
	 * @param response
	 *            the response
	 * @return the string
	 * @throws GeneralSecurityException
	 *             the general security exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@RequestMapping("/add/account/google/{accountName}")
	public String addAcount(@PathVariable final String accountName, @RequestParam final String userKey,
			final HttpServletRequest request, final HttpSession session, final HttpServletResponse response)
			throws GeneralSecurityException, IOException {
		final String redirectUri = new BuildRedirectUri().buildRedirectUri(request, config.getoAuth2CallbackUrl());
		String url = googleAccountService.addAccount(accountName, userKey, redirectUri, session);
		return url;
	}

	/**
	 * Adds the microsoft account.
	 *
	 * @param microsoftAccountName
	 *            the microsoft account name
	 * @param userKey
	 *            the user key
	 * @param request
	 *            the request
	 * @param session
	 *            the session
	 * @param response
	 *            the response
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = "/add/account/microsoft/{microsoftAccountName}")
	public void addMicrosoftAccount(@PathVariable final String microsoftAccountName, @RequestParam final String userKey,
			final HttpServletRequest request, final HttpSession session, final HttpServletResponse response)
			throws IOException {

		UUID state = UUID.randomUUID();
		UUID nonce = UUID.randomUUID();

		session.setAttribute(Constants.SESSION_EXPECTED_STATE, state);
		session.setAttribute(Constants.SESSION_EXPECTED_NONCE, nonce);
		session.setAttribute(Constants.SESSION_USER_KEY, userKey);

		String url = microsoftAccountService.getLoginUrl(state, nonce);

		response.sendRedirect(url);
	}

	/**
	 * Authorize.
	 *
	 * @param code            the code
	 * @param idToken            the id token
	 * @param state            the state
	 * @param request            the request
	 * @return the string
	 */
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String authorize(@RequestParam("code") final String code, @RequestParam("id_token") final String idToken,
			@RequestParam("state") final UUID state, final HttpServletRequest request) {
		HttpSession session = request.getSession();
		UUID expectedState = (UUID) session.getAttribute(Constants.SESSION_EXPECTED_STATE);
		UUID expectedNonce = (UUID) session.getAttribute(Constants.SESSION_EXPECTED_NONCE);
		String userKey = (String) session.getAttribute(Constants.SESSION_USER_KEY);

		if (state.equals(expectedState)) {
			IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());

			if (idTokenObj != null) {

				TokenResponse tokenResponse = OutlookCredentialService.getTokenFromAuthCode(code,
						idTokenObj.getTenantId());
				AppUser user = appUserRepository.findByUserkey(userKey);
				if (user != null) {
					MicrosoftAccount account = new MicrosoftAccount();
					user.setUserKey(userKey);
					account.setToken(tokenResponse);
					account.setTenantId(idTokenObj.getTenantId());
					account.setToken(tokenResponse);
					account.setEmail(idTokenObj.getEmail());
					user.addMicrosoftAccount(account);
					appUserRepository.save(user);
				} else {
					LOGGER.error("User not Found");
				}
			} else {
				LOGGER.error("Error while parsing encoded Token");
			}
		} else {
			LOGGER.error("Unexpected state return");
		}
		return "index";
	}

	/**
	 * Logout.
	 *
	 * @param request
	 *            the request
	 * @return the string
	 */
	@RequestMapping("/logout")
	public String logout(final HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/index";
	}
}
