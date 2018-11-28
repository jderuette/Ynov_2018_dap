package fr.ynov.dap.controllers.microsoft;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.IdToken;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.data.OutlookUser;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.data.interfaces.OutlookServiceInterface;
import fr.ynov.dap.services.microsoft.MicrosoftAccountService;
import fr.ynov.dap.services.microsoft.OutlookServiceBuilder;

/**
 * The Class MicrosoftAccountController.
 */
@Controller
public class MicrosoftAccountController extends MicrosoftAccountService {

	/** The app user repo. */
	@Autowired
	AppUserRepository appUserRepo;

	/**
	 * Adds the account.
	 *
	 * @param accountName the account name
	 * @param userKey     the user key
	 * @param model       the model
	 * @param request     the request
	 * @param response    the response
	 * @return the string
	 */
	@RequestMapping("/account/add/microsoft/{accountName}")
	public @ResponseBody String addAccount(@PathVariable(value = "accountName") final String accountName,
			@RequestParam(value = "userKey", required = true) String userKey, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		if (userKey.isEmpty()) {
			LOG.error("user key can't be empty");
		}

		UUID state = UUID.randomUUID();
		UUID nonce = UUID.randomUUID();

		// Save the state and nonce in the session so we can
		// verify after the auth process redirects back
		HttpSession session = request.getSession();
		session.setAttribute("expected_state", state);
		session.setAttribute("expected_nonce", nonce);

		session.setAttribute("userId", userKey);
		session.setAttribute("accountName", accountName);

		String loginUrl = getLoginUrl(state, nonce);

		try {
			response.sendRedirect(loginUrl);
		} catch (IOException e) {
			LOG.error("login error", e);
		}

		return loginUrl;
	}

	/**
	 * Authorize.
	 *
	 * @param code    the code
	 * @param idToken the id token
	 * @param state   the state
	 * @param request the request
	 * @param model   the model
	 * @return the string
	 */
	@RequestMapping(value = "/microsoft/authorize", method = RequestMethod.POST)
	public String authorize(@RequestParam("code") String code, @RequestParam("id_token") String idToken,
			@RequestParam("state") UUID state, HttpServletRequest request, Model model) {
		{
			// Get the expected state value from the session
			HttpSession session = request.getSession();
			UUID expectedState = (UUID) session.getAttribute("expected_state");
			UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");

			String userId = (String) session.getAttribute("userId");
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

					OutlookServiceInterface outlookService = OutlookServiceBuilder
							.getOutlookService(tokenResponse.getAccessToken());

					OutlookUser user;

					try {
						user = outlookService.getCurrentUser().execute().body();
						session.setAttribute("userEmail", user.getMail());
					} catch (IOException e) {
						session.setAttribute("error", e.getMessage());
					}
				} else {
					session.setAttribute("error", "ID token failed validation.");
				}
			} else {
				session.setAttribute("error", "Unexpected state returned from authority.");
			}

			LOG.info("authCode : " + code + " - idToken :" + idToken);

			if (session.getAttribute("error") != null) {
				String error = (String) session.getAttribute("error");

				LOG.error(error);
				model.addAttribute("errorMsg", error);
				return "error";
			}

			AppUser user = appUserRepo.findByUserKey(userId);
			if (user == null) {
				user = new AppUser();
				user.setUserKey(userId);
			}

			MicrosoftAccount newMicrosoftAccount = new MicrosoftAccount();
			newMicrosoftAccount.setName(userAccount);
			newMicrosoftAccount.setTenantId(tenantId);
			newMicrosoftAccount.setToken(tokenResponse);

			user.addMicrosoftAccounts(newMicrosoftAccount);

			appUserRepo.save(user);

			LOG.info("Create new user " + userAccount + " and add new microsoft account");

			model.addAttribute("successMsg", "Auth success user :" + userAccount);
			return "redirect:/?userKey=" + userId;
		}
	}

	/**
	 * Logout.
	 *
	 * @param model   the model
	 * @param request the request
	 * @return the string
	 */
	@RequestMapping("/logout")
	public String logout(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		model.addAttribute("successMsg", "Logout success");
		return "success";
	}
}
