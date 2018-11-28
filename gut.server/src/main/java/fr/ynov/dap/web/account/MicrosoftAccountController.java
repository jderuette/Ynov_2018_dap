package fr.ynov.dap.web.account;

import java.io.IOException;
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

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.google.GoogleAccount;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.data.microsoft.MicrosoftUser;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.repository.MicrosoftAccountRepository;
import fr.ynov.dap.service.microsoft.MicrosoftService;
import fr.ynov.dap.service.microsoft.OutlookService;
import fr.ynov.dap.service.microsoft.auth.IdToken;
import fr.ynov.dap.service.microsoft.auth.TokenResponse;
import fr.ynov.dap.service.microsoft.helper.OutlookServiceBuilder;

@Controller
public class MicrosoftAccountController {
	
	@Autowired 
	AppUserRepository 
	appUserRepository;
	
	@Autowired 
	MicrosoftAccountRepository 
	microsoftAccountRepository;
	
	
	@RequestMapping(value = "/account/add/microsoft/{accountName}",
			method = RequestMethod.POST)
	public String addMicrosoftAccount(
			@PathVariable final String accountName,
			@RequestParam final String userKey,
			final HttpServletRequest request) {
		UUID state = UUID.randomUUID();
		UUID nonce = UUID.randomUUID();

		// Save the state and nonce in the session so we can
		// verify after the auth process redirects back
		HttpSession session = request.getSession();
		session.setAttribute("expected_state", state);
		session.setAttribute("expected_nonce", nonce);
		
		session.setAttribute("accountName", accountName);
		session.setAttribute("userKey", userKey);

		
		String loginUrl = MicrosoftService.getLoginUrl(state, nonce);
		
		return "redirect:" + loginUrl;
	}
	
	@RequestMapping(value = "/authorize", 
			method = RequestMethod.POST)
	public String authorize(Model model,
			@RequestParam("code") String code,
			@RequestParam("id_token") String idToken,
			@RequestParam("state") UUID state,
			HttpServletRequest request) {
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
					TokenResponse tokenResponse = MicrosoftService.getTokenFromAuthCode(code, idTokenObj.getTenantId());
					
					session.setAttribute("accessToken", tokenResponse.getAccessToken());
					session.setAttribute("userConnected", true);
					session.setAttribute("userName", idTokenObj.getName());
					
					//Récup du userApp
					String userKey = (String) session.getAttribute("userKey");
					String accountName = (String) session.getAttribute("accountName");
					//insert
					if (userKey != null) {
						if (appUserRepository.findByUserKey(userKey) != null) {
							AppUser currentAppUser = new AppUser(userKey);
							MicrosoftAccount newMicrosoftAccount =
									new MicrosoftAccount(accountName,
											tokenResponse.getAccessToken(),
											idTokenObj.getTenantId());
							if(!currentAppUser.getGoogleAccounts().contains(newMicrosoftAccount)) {
								microsoftAccountRepository.save(newMicrosoftAccount);
							}
							appUserRepository.save(new AppUser(userKey));
							model.addAttribute("callbackMsg", "New account");
						} else {
							model.addAttribute("callbackMsg", "user account dont exist");
						}
					} else {
						model.addAttribute("callbackMsg", "user account name missing");
					}
					
					// Get user info
					OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(), null);
					MicrosoftUser user;
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
	
	@RequestMapping("/microsoft/logout")
	public String logout(HttpServletRequest request) {
	  HttpSession session = request.getSession();
	  session.invalidate();
	  return "redirect:/index.html";
	}
}