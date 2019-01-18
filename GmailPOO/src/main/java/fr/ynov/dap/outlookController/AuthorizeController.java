package fr.ynov.dap.outlookController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.authOutlook.AuthHelper;
import fr.ynov.dap.authOutlook.IdToken;
import fr.ynov.dap.authOutlook.TokenResponse;
import fr.ynov.dap.data.Account;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.metier.Data;
import fr.ynov.dap.outlookService.OutlookService;
import fr.ynov.dap.outlookService.OutlookServiceBuilder;
import fr.ynov.dap.outlookService.OutlookUser;
import javassist.NotFoundException;

@Controller
public class AuthorizeController {
	@Autowired
	Data dataBase;

	@RequestMapping(value = "/ajouter.html", method = RequestMethod.POST)
	public String authorize(@RequestParam("code") String code, @RequestParam("id_token") String idToken,
			@RequestParam("state") UUID state, HttpServletRequest request, Model model) {
		// Get the expected state value from the session

		HttpSession session = request.getSession();
		UUID expectedState = (UUID) session.getAttribute("expected_state");
		UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
		session.removeAttribute("expected_state");
		session.removeAttribute("expected_nonce");

		// Make sure that the state query parameter returned matches
		// the expected state
		model.addAttribute("add", "Add Microsoft account");
		if (state.equals(expectedState)) {
			IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
			if (idTokenObj != null) {
				TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
				session.setAttribute("tokens", tokenResponse);
				session.setAttribute("userConnected", true);
				session.setAttribute("userName", idTokenObj.getName());
				session.setAttribute("userTenantId", idTokenObj.getTenantId());
				// Get user info
				OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(),
						null);
				OutlookUser user;
				try {
					user = outlookService.getCurrentUser().execute().body();
					session.setAttribute("userEmail", user.getMail());
					String userKey = (String) session.getAttribute("userKey");
					String accountName = (String) session.getAttribute("accountName");
					dataBase.ajouterAccountMicrosoft(userKey, user.getMail(), accountName, tokenResponse,
							idTokenObj.getTenantId());
					model.addAttribute("onSuccess", accountName + "  ajouté avec succes ");

				} catch (IOException | NotFoundException e) {
					session.setAttribute("error", e.getMessage());
					model.addAttribute("error", e.getMessage());
				}
			} else {
				session.setAttribute("error", "ID token failed validation.");
				model.addAttribute("error", "ID token failed validation.");
			}
		} else {
			session.setAttribute("error", "Unexpected state returned from authority.");
			model.addAttribute("error", "Unexpected state returned from authority.");
		}

		return "Info";
	}

	public String Url(Model model, HttpServletRequest request) {
		UUID state = UUID.randomUUID();
		UUID nonce = UUID.randomUUID();

		// Save the state and nonce in the session so we can
		// verify after the auth process redirects back
		HttpSession session = request.getSession();
		session.setAttribute("expected_state", state);
		session.setAttribute("expected_nonce", nonce);
		String loginUrl = AuthHelper.getLoginUrl(state, nonce);
		return loginUrl;
	}

	@RequestMapping(value = "/add/microsoft/account/{accountName}")
	public String addAccountMicrosoft(@PathVariable("accountName") final String accountName,
			@RequestParam(name = "userKey") String userKey, final HttpServletRequest request, final HttpSession session,
			Model model) throws GeneralSecurityException {
		String response = "Info";

		try {
			AppUser user = dataBase.consulterUser(userKey);
			if (user != null) {
				Account account = dataBase.getAccount(accountName);
				if (account != null) {
					model.addAttribute("error", accountName + " existe déjà  ");
				} else {
					String urlOutlook = Url(model, request);
					session.setAttribute("accountName", accountName);
					session.setAttribute("userKey", userKey);
					response = "redirect:" + urlOutlook;
				}
			} else {
				model.addAttribute("error", userKey + " n'existe Pas");
			}
		} catch (Exception e) {
			model.addAttribute("error", userKey + "  " + e.getMessage());
		}

		return response;
	}

}
