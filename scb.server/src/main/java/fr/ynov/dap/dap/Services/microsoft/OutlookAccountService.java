package fr.ynov.dap.dap.services.microsoft;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.data.OutlookAccount;
import fr.ynov.dap.dap.data.OutlookAccountRepository;
import fr.ynov.dap.dap.data.Account.AccountType;
import fr.ynov.dap.dap.helpers.AuthHelper;
import fr.ynov.dap.dap.models.IdToken;
import fr.ynov.dap.dap.models.OutlookUser;
import fr.ynov.dap.dap.models.TokenResponse;
import fr.ynov.dap.dap.services.google.GoogleService;

@Service
public class OutlookAccountService extends GoogleService{
	@Autowired
	AppUserRepository repository;
	
	@Autowired
	OutlookAccountRepository outlookRepository;
	
	@Autowired
	AuthHelper authHelper;
	
	
	public String AddAccount(String userKey, String accountName, HttpServletRequest request) {
		AppUser currentUser = repository.findByName(userKey);
		String loginUrl = getRedirectionUrl(request);

		if(currentUser == null) {
			return "this user doesn't exist";
		}
		else {
			OutlookAccount outlookAccount = outlookRepository.findByName(accountName);
			if(outlookAccount != null) {
				return "AccountAlreadyCreated";
			}
			OutlookAccount account = new OutlookAccount();
			account.setOwner(currentUser);
			account.setName(accountName);
    	    account.setAccountType(AccountType.Outlook);

			currentUser.addAccount(account);
			request.getSession().setAttribute("accountNameMicrosoft", accountName);
			repository.save(currentUser);	
		}
		return "redirect:"+loginUrl;
	}
	
	public String getRedirectionUrl(HttpServletRequest request) {
		UUID state = UUID.randomUUID();
	    UUID nonce = UUID.randomUUID();
	    
	    // Save the state and nonce in the session so we can
	    // verify after the auth process redirects back
	    HttpSession session = request.getSession();
	    session.setAttribute("expected_state", state);
	    session.setAttribute("expected_nonce", nonce);
		String loginUrl = authHelper.getLoginUrl(state, nonce);
		return loginUrl;
	}
	
	
	public void authorize(HttpServletRequest request, UUID state, String code, String idToken) {
		HttpSession session = request.getSession();
	    UUID expectedState = (UUID) session.getAttribute("expected_state");
	    UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
	    	
	    // Make sure that the state query parameter returned matches
	    // the expected state
	    if (state.equals(expectedState)) {
	    	IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
	    	if (idTokenObj != null) {
	    	  TokenResponse tokenResponse = authHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
	    			 
	    	  OutlookAccount otAccount = outlookRepository.findByName(request.getSession().getAttribute("accountNameMicrosoft").toString());
	    	  tokenResponse.setTenantID(idTokenObj.getTenantId());
	    	  otAccount.setIdToken(tokenResponse);
	    	  
	    	  outlookRepository.save(otAccount);
	    	} 
	    }
	}
}
