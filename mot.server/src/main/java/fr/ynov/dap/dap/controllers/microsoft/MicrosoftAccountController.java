package fr.ynov.dap.dap.controllers.microsoft;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.ynov.dap.dap.data.google.AppUser;
import fr.ynov.dap.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.dap.data.interfaces.OutlookInterface;
import fr.ynov.dap.dap.data.microsoft.IdToken;
import fr.ynov.dap.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.dap.data.microsoft.OutlookUser;
import fr.ynov.dap.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.dap.services.microsoft.MicrosoftAccountService;
import fr.ynov.dap.dap.services.microsoft.OutlookServiceBuilder;

/**
 * The Class MicrosoftAccountController.
 */
@Controller
@RequestMapping("/microsoft")
public class MicrosoftAccountController extends MicrosoftAccountService {

	@Autowired
	AppUserRepository appUserRepository;

	protected Logger LOG = LogManager.getLogger(MicrosoftAccountController.class);

	/**
	 * Adds the account.
	 *
	 * @param accountName the account name
	 * @param userKey     the user key
	 * @param model       the model
	 * @param request     the request
	 * @param response    the response
	 * @return Thymeleaf template Login Success
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//TODO mot by Djer |Spring| le "required = true" est la valeur par defaut, tu n'es pas obligé de le préciser
	@RequestMapping("/add/account/{accountName}")
	public @ResponseBody String addAccount(@PathVariable(value = "accountName") final String accountName,
			@RequestParam(value = "userKey", required = true) final String userKey, Model model,
			HttpServletRequest request, HttpServletResponse response) throws IOException {
		UUID state = UUID.randomUUID();
		UUID nonce = UUID.randomUUID();

		// Save the state and nonce in the session so we can
		// verify after the auth process redirects back
		HttpSession session = request.getSession();
		session.setAttribute("expected_state", state);
		session.setAttribute("expected_nonce", nonce);

		session.setAttribute("userKey", userKey);
		session.setAttribute("accountName", accountName);

		String loginUrl = getLoginUrl(state, nonce);
		// model.addAttribute("loginUrl", loginUrl);
		// Name of a definition in WEB-INF/defs/pages.xml

		response.sendRedirect(loginUrl);
		//TODO mot by Djer |API Microsoft| Si l'utilisateur à été redirigé, alors c'estr "/autorize" qu idétermnera la page à afficher. Si l'utilisateur n'est pas redirigé, c'est plutot une page "d'erreur technique" qu'il faut afficher

		return "loginSuccess";
	}

	/**
	 * Authorize.
	 *
	 * @param code    the code
	 * @param idToken the id token
	 * @param state   the state
	 * @param request the request
	 * @param model   the model
	 * @return ThymeLeaf Template Login Success
	 */
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String authorize(@RequestParam("code") String code, @RequestParam("id_token") String idToken,
			@RequestParam("state") UUID state, HttpServletRequest request, Model model) {
		{
			// Get the expected state value from the session
			HttpSession session = request.getSession();
			UUID expectedState = (UUID) session.getAttribute("expected_state");
			UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");

			String userKey = (String) session.getAttribute("userKey");
			String userAccount = (String) session.getAttribute("accountName");

			String tenantId = "";
			TokenResponse tokenResponse = null;

			// Make sure that the state query parameter returned matches
			// the expected state
			if (state.equals(expectedState)) {
				IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
				if (idTokenObj != null) {
					tokenResponse = getTokenFromAuthCode(code, idTokenObj.getTenantId());
					tenantId = idTokenObj.getTenantId();

					OutlookInterface outlookService = OutlookServiceBuilder
							.getOutlookService(tokenResponse.getAccessToken(), null);

					OutlookUser user;

					try {
						user = outlookService.getCurrentUser().execute().body();
						session.setAttribute("userEmail", user.getMail());
					} catch (IOException e) {
						session.setAttribute("error", e.getMessage());
						LOG.error("Error Authorize getCurrentUser", e.getMessage());
					}
					session.setAttribute("userName", idTokenObj.getName());
				} else {
					session.setAttribute("error", "ID token failed validation.");
					LOG.error("ID token failed validation.");
				}
			} else {
				session.setAttribute("error", "Unexpected state returned from authority.");
				LOG.error("Unexpected state returned from authority.");
			}

			AppUser AppUser = appUserRepository.findByUserKey(userKey);
			if (AppUser == null) {
				AppUser = new AppUser();
				AppUser.setUserKey(userKey);
			}

			MicrosoftAccount MicrosoftAccount = new MicrosoftAccount();
			MicrosoftAccount.setName(userAccount);
			MicrosoftAccount.setTenantId(tenantId);
			MicrosoftAccount.setToken(tokenResponse);

			AppUser.addMicrosoftAccount(MicrosoftAccount);

			appUserRepository.save(AppUser);

			model.addAttribute("successMsg", "Auth success user :" + userAccount);
			model.addAttribute("userName", session.getAttribute("userName"));
			model.addAttribute("tokens", tokenResponse);

			return "loginSuccess";
		}
	}

	/**
	 * Logout.
	 *
	 * @param request the request
	 * @return thymeleaf template logoutSuccess
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "logoutSuccess";
	}
}
