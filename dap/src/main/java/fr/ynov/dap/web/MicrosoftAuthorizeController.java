package fr.ynov.dap.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.AuthHelper;
import fr.ynov.dap.service.MicrosoftIdTokenService;
import fr.ynov.dap.service.OutlookService;
import fr.ynov.dap.service.OutlookServiceBuilder;
import fr.ynov.dap.service.OutlookUserService;
import fr.ynov.dap.service.MicrosoftTokenResponseService;

@Controller
/**
 * Vérifie la bonne connexion de l'utilisateur microsoft.
 * @author abaracas
 *
 */
public class MicrosoftAuthorizeController {

	@RequestMapping(value="/authorize", method=RequestMethod.POST)
	/**
	 * Valide, ou non, la connexionS
	 * @param code code
	 * @param idToken id
	 * @param state state
	 * @param request request
	 * @return l'url de base de microsoft
	 */
	public String authorize(
			@RequestParam("code") String code, 
			@RequestParam("id_token") String idToken,
			@RequestParam("state") UUID state,
			HttpServletRequest request) {
		// Get the expected state value from the session
		HttpSession session = request.getSession();
		UUID expectedState = (UUID) session.getAttribute("expected_state");
		UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
		session.removeAttribute("expected_state");
		session.removeAttribute("expected_nonce");
		
		// Make sure that the state query parameter returned matches
		// the expected state
		if (state.equals(expectedState)) {
			MicrosoftIdTokenService idTokenObj = MicrosoftIdTokenService.parseEncodedToken(idToken, expectedNonce.toString());
			if (idTokenObj != null) {
				MicrosoftTokenResponseService tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
				session.setAttribute("tokens", tokenResponse);
				session.setAttribute("userConnected", true);
				session.setAttribute("userName", idTokenObj.getName());
				session.setAttribute("userTenantId", idTokenObj.getTenantId());
				// Get user info
				OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(), null);
				OutlookUserService user;
				try {
					user = outlookService.getCurrentUser().execute().body();
					session.setAttribute("userEmail", user.getEmailAddress());
				} catch (IOException e) {
					session.setAttribute("error", e.getMessage());
				}
			} else {
				session.setAttribute("error", "ID token failed validation.");
			}
		} else {
			session.setAttribute("error", "Unexpected state returned from authority.");
		}
		return "redirect:/mail";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/mail";
	}
}
