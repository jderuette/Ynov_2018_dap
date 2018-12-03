package fr.ynov.dap.GmailPOO.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.Outlook.auth.AuthHelper;
import fr.ynov.Outlook.auth.IdToken;
import fr.ynov.Outlook.auth.TokenResponse;
import fr.ynov.Outlook.service.OutlookService;
import  fr.ynov.Outlook.service.OutlookServiceBuilder;
import  fr.ynov.Outlook.service.OutlookUser;
import fr.ynov.dap.GmailPOO.metier.Data;
import javassist.NotFoundException;

@Controller
public class AuthorizeController {
@Autowired Data dataBase;
	@RequestMapping(value="/ajouter.html", method=RequestMethod.POST)
	public String authorize(
			@RequestParam("code") String code, 
			@RequestParam("id_token") String idToken,
			@RequestParam("state") UUID state,
			HttpServletRequest request,
			Model model) {
		// Get the expected state value from the session
		
		HttpSession session = request.getSession();
		UUID expectedState = (UUID) session.getAttribute("expected_state");
		UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
		session.removeAttribute("expected_state");
		session.removeAttribute("expected_nonce");
		
		// Make sure that the state query parameter returned matches
		// the expected state
		model.addAttribute("add","Add Microsoft account");
		if (state.equals(expectedState)) {
			IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
			if (idTokenObj != null) {
				TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
				session.setAttribute("tokens", tokenResponse);
				session.setAttribute("userConnected", true);
				session.setAttribute("userName", idTokenObj.getName());
				session.setAttribute("userTenantId", idTokenObj.getTenantId());
				// Get user info
				OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(), null);
				OutlookUser user;
				try {
					user = outlookService.getCurrentUser().execute().body();
					session.setAttribute("userEmail", user.getMail());
					String userKey=(String) session.getAttribute("userKey");
					String accountName=(String) session.getAttribute("accountName");
					 dataBase.ajouterAccountMicrosoft(userKey,
						user.getMail(),accountName,tokenResponse,idTokenObj.getTenantId());
					model.addAttribute("onSuccess", accountName + "  ajouté avec succes ");
					
				} catch (IOException | NotFoundException e) {
					session.setAttribute("error", e.getMessage());
					model.addAttribute("error",e.getMessage());
				}
			} else {
				session.setAttribute("error", "ID token failed validation.");
				model.addAttribute("error","ID token failed validation.");
			}
		} else {
			session.setAttribute("error", "Unexpected state returned from authority.");
			model.addAttribute("error","Unexpected state returned from authority.");
		}
		
		return "addUser";
	}
	public String index(Model model, HttpServletRequest request) {
		UUID state = UUID.randomUUID();
		UUID nonce = UUID.randomUUID();
		
		// Save the state and nonce in the session so we can
		// verify after the auth process redirects back
		HttpSession session = request.getSession();
		session.setAttribute("expected_state", state);
		session.setAttribute("expected_nonce", nonce);
		
		String loginUrl = AuthHelper.getLoginUrl(state, nonce);
		model.addAttribute("loginUrl", loginUrl);
		// Name of a definition in WEB-INF/defs/pages.xml
		//TODO bes by Djer |API Microsoft| Tu devrais renvoyer l'URL construite, et pas "index". Le nom de ta méthode n'est pas claire du tout non plus.
		return "index";
	}
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/index.html";
	}
}
