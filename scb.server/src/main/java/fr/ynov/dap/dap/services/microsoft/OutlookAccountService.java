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
	//TODO scb by Djer |POO| Pourquoi public ?
    @Autowired
	AppUserRepository repository;
	
  //TODO scb by Djer |POO| Pourquoi public ?
	@Autowired
	OutlookAccountRepository outlookRepository;
	
	//TODO scb by Djer |POO| Pourquoi public ?
	@Autowired
	AuthHelper authHelper;
	
	//TODO scb by Djer |POO| Le nom des méthodes ne doivent pas commencer par une majuscule
	public String AddAccount(String userKey, String accountName, HttpServletRequest request) {
		AppUser currentUser = repository.findByName(userKey);
		String loginUrl = getRedirectionUrl(request);

		if(currentUser == null) {
		  //TODO scb by Djer |POO| Evite les multiples return dans une même méthode
            //TODO scb by Djer |MVC| Ton controller utilise le retour comme nom d'une Vue. Idéalement renvoie ici une valeur **metier** (un Boolean semble plus adapté ici), et laisse le controller décider quoi faire (et quel vue afficher) en fonction du retour métier du service
			return "this user doesn't exist";
		}
		else {
			OutlookAccount outlookAccount = outlookRepository.findByName(accountName);
			if(outlookAccount != null) {
			  //TODO scb by Djer |POO| Evite les multiples return dans une même méthode
	            //TODO scb by Djer |MVC| Ton controller utilise le retour comme nom d'une Vue. Idéalement renvoie ici une valeur **metier** (un Boolean semble plus adapté ici), et laisse le controller décider quoi faire (et quel vue afficher) en fonction du retour métier du service
				return "AccountAlreadyCreated";
			}
			//TODO scb vy Djer |Rest API| Ne sauvegarde pas ici, l'utilsiateur peut refuser de partager ces informations et ut auras un "compte fantiome". Sauvegarde dans le "authorize"
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
	    	//TODO scb by Djer |Log4J| (else) une petite log ?
	    }
	  //TODO scb by Djer |Log4J| (else) une petite log ?
	}
}
