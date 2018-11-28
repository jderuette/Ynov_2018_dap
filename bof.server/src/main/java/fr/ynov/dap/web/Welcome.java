package fr.ynov.dap.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.model.MailModel;
import fr.ynov.dap.service.Google.GoogleGmailService;

@Controller
public class Welcome {

	@Autowired
	GoogleGmailService mailService;
	
	@RequestMapping("/")
	public String welcome(ModelMap model) throws IOException, Exception {
		return "Welcome";
	}
	
}
