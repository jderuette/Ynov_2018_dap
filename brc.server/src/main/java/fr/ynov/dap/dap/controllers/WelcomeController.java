package fr.ynov.dap.dap.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.GmailService;
import fr.ynov.dap.dap.models.GmailResponse;

import org.springframework.ui.ModelMap;

@Controller
public class WelcomeController {

	@Autowired 
	GmailService gmailService;
	
	@RequestMapping("/{accountName}")
	public String welcome(ModelMap model, @PathVariable final String accountName, @RequestParam final String userKey) throws IOException, Exception {
		GmailResponse output = gmailService.resultMailInbox(accountName);
		model.addAttribute("nbEmails",output.getNbUnreadMail());
		return "welcome";
	}
	
	@RequestMapping("/")
	public String succes() {
		return "succes";
	}
}
