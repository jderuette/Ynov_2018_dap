package fr.ynov.dap.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.service.google.GmailService;
import fr.ynov.dap.dap.service.google.GoogleAccountService;
import fr.ynov.dap.dap.service.google.GoogleServices;

@Controller
public class Welcome {
	
	
	
	@Autowired 
	public AppUserRepository repo;
	
	
	
	@RequestMapping("/")
	public String welcome(ModelMap model, @RequestParam("userKey") String user) {
		AppUser userRepo = repo.findByUserkey(user);
		if (userRepo == null ) {
			model.addAttribute("content", "login"); 
			return "index";
		}
		
			
			model.addAttribute("userKey", "?userKey=" + user);
			model.addAttribute("googleAccount", userRepo.getGoogleAccounts());
			model.addAttribute("microsoftAccount", userRepo.getMicrosoftAccounts());
			model.addAttribute("content", "welcomeViews"); 
			
		
		
		return "index";
		
		
	}

}
