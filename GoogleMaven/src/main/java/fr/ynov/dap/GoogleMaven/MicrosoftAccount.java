package fr.ynov.dap.GoogleMaven;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;

import fr.ynov.dap.GoogleMaven.data.AppUser;
import fr.ynov.dap.GoogleMaven.repository.AppUserRepostory;
import fr.ynov.dap.GoogleMaven.repository.GoogleAccountRepository;
import fr.ynov.dap.GoogleMaven.repository.MicrosoftAccountRepository;

@Controller
public class MicrosoftAccount {
	@Autowired
	AppUserRepostory appUserRepostory;
	
	@Autowired
	MicrosoftAccountRepository microsoftAccountRepository;
	@RequestMapping("/account/add/{accountName}/{userKey}")
	public String addAccount(@PathVariable final String accountName, @PathVariable final String userKey, final HttpServletRequest request,
			final HttpSession session) throws GeneralSecurityException, InstantiationException, IllegalAccessException {
		//System.out.println("YOYO");
		
	    AppUser myuser = appUserRepostory.findByUserKey(userKey);
		
	    if (myuser == null){
			myuser = new AppUser(userKey);
			appUserRepostory.save(myuser);
		}
			
		
	
		
		return "response";
	}
}
