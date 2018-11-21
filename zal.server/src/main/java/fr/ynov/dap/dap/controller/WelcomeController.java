package fr.ynov.dap.dap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.model.MailModel;
import fr.ynov.dap.dap.service.GmailService;

@Controller

public class WelcomeController {

	@Autowired
	GmailService gmailService;
	
	@RequestMapping("/")
	public String welcome(){
		return "welcome";
	}
	
    @RequestMapping("/mail/{userId}")
    public String getMailInView(final ModelMap model, @PathVariable final String userId) {
		MailModel mailModel = gmailService.getMailInBoxUnread(userId);
		model.addAttribute("nbEmails", mailModel.getUnreadMessages());
		return "mail";
    }
}
