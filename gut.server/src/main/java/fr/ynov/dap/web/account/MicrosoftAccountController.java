package fr.ynov.dap.web.account;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.service.microsoft.MicrosoftService;
import fr.ynov.dap.service.microsoft.OutlookService;
import fr.ynov.dap.service.microsoft.auth.IdToken;
import fr.ynov.dap.service.microsoft.auth.TokenResponse;
import fr.ynov.dap.service.microsoft.helper.OutlookServiceBuilder;

@Controller
public class MicrosoftAccountController {
	
	@Autowired MicrosoftService microsoftService;
	
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String authorize(@RequestParam("code") String code, @RequestParam("id_token") String idToken,
			@RequestParam("state") UUID state, HttpServletRequest request) {
		{
			// Get the expected state value from the session
			HttpSession session = request.getSession();
			UUID expectedState = (UUID) session.getAttribute("expected_state");
			UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");

			// Make sure that the state query parameter returned matches
			// the expected state
			if (state.equals(expectedState)) {
//				session.setAttribute("authCode", code);
//				session.setAttribute("idToken", idToken);
				IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
				if (idTokenObj != null) {
					TokenResponse tokenResponse = microsoftService.getTokenFromAuthCode(code, idTokenObj.getTenantId());
					session.setAttribute("accessToken", tokenResponse.getAccessToken());
					session.setAttribute("userConnected", true);
					session.setAttribute("userName", idTokenObj.getName());
					// Get user info
					OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(), null);
					MicrosoftAccount user;
					try {
					  user = outlookService.getCurrentUser().execute().body();
					  session.setAttribute("userEmail", user.getEmailAddress());
					} catch (IOException e) {
					  session.setAttribute("error", e.getMessage());
					}
					session.setAttribute("userTenantId", idTokenObj.getTenantId());
				} else {
					session.setAttribute("error", "ID token failed validation.");
				}
			} else {
				session.setAttribute("error", "Unexpected state returned from authority.");
			}
			return "mail";
		}
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
	  HttpSession session = request.getSession();
	  session.invalidate();
	  return "redirect:/index.html";
	}
}