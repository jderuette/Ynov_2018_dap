package fr.ynov.dap.googleController;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.googleService.GMailService;

@Controller
public class GmailController {
	@Autowired
	GMailService gmailservice;

	@RequestMapping("/UnreadMail/google/{accountName}")
	public String getUnReadGoogleEmails(@PathVariable("accountName") String accountName, Model model)
			throws IOException, GeneralSecurityException {

		model.addAttribute("add", "Nb Unread Mail for :" + accountName);
		model.addAttribute("onSuccess", gmailservice.getListEmails(accountName).size());
		return "Info";

	}
}
