package fr.ynov.dap.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.dap.GmailService;
import fr.ynov.dap.models.GmailResponse;

import org.springframework.ui.ModelMap;

@Controller
public class WelcomeController {

	@Autowired 
	GmailService gmailService;
	
	@RequestMapping("/google/{accountName}")
	public String welcome(ModelMap model, @PathVariable final String accountName) throws IOException, Exception {
		GmailResponse output = gmailService.resultMailInbox(accountName);
		model.addAttribute("nbEmails",output.getNbUnreadMail());
		return "welcome";
	}
	
	@RequestMapping("/google")
	public String defaultRoute() throws IOException, Exception {
		return "succes";
	}
}
