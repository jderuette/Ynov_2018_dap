package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.services.google.CalendarService;
import fr.ynov.dap.services.google.GmailService;
import fr.ynov.dap.services.google.GoogleService;
import fr.ynov.dap.utils.ExtendsUtils;
import fr.ynov.dap.utils.JSONResponse;

@Controller
public class Welcome extends ExtendsUtils {
	
	@Autowired
	AppUserRepository appUserRepo;
	
	@RequestMapping("/")
	public String welcome(ModelMap model, @RequestParam(value="userKey", required=false) String user) {		
		if (user == null || user.length() == 0) {
			return "pleaselogin";
		}
		
		AppUser currentUser = appUserRepo.findByUserKey(user);
		
		LOG.info("login ok with : " + currentUser.getUserKey());

		model.addAttribute("userKey", "?userKey=" + user);
		model.addAttribute("googleAccount", currentUser.getGoogleAccounts());
		model.addAttribute("microsoftAccount", currentUser.getMicrosoftAccounts());
		
		return "welcome";
	}
}
