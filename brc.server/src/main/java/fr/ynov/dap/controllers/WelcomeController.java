package fr.ynov.dap.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.AppUserRepostory;
import fr.ynov.dap.google.service.GmailService;
import fr.ynov.dap.models.NbMailResponse;

import org.springframework.ui.ModelMap;

@Controller
public class WelcomeController {

	@Autowired 
	GmailService gmailService;
	
	@Autowired
	AppUserRepostory appUserRepository;
	
	@RequestMapping("/google/welcome")
	public String welcome(ModelMap model, @PathVariable final String accountName,
			@RequestParam final String userKey) throws IOException, Exception {
		
		AppUser currentUser = appUserRepository.findByUserkey(userKey);

		NbMailResponse output = new NbMailResponse(gmailService.getNbUnreadMailForAccount(currentUser));
		model.addAttribute("nbEmails",output.getNbUnreadMail());
		model.addAttribute("userKey", userKey);
		return "welcome";
	}
}
