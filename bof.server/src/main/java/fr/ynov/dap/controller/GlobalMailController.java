package fr.ynov.dap.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.model.AppUserModel;
import fr.ynov.dap.model.MailModel;
import fr.ynov.dap.model.MasterModel;
import fr.ynov.dap.model.Google.GoogleAccountModel;
import fr.ynov.dap.model.microsoft.OutlookAccountModel;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.service.Google.GoogleGmailService;
import fr.ynov.dap.service.microsoft.MicrosoftMailService;

@Controller
@RequestMapping("/mail")
public class GlobalMailController {
	
	@Autowired
	GoogleGmailService mailService;
	
	@Autowired
	MicrosoftMailService microsoftMailService;
	
	@Autowired
	AppUserRepository appUserRepository;
	
	@RequestMapping("/count")
	public String getInboxMails(@RequestParam final String userKey, Model model) throws IOException, Exception {
		
		int inboxSum = 0;
		if(null != appUserRepository.findByUserKey(userKey)) {
			AppUserModel appUser =  appUserRepository.findByUserKey(userKey);
			for (GoogleAccountModel googleAccount : appUser.getGoogleAccounts()) {
				MailModel inbox = (MailModel) mailService.getInboxMail(googleAccount.getAccountName());
				inboxSum += inbox.getNbOfEmail();
			}
			
			for(OutlookAccountModel microsoftAccount :appUser.getMicrosoftAccounts()) {
				MailModel inbox = (MailModel) microsoftMailService.getNumberOfInboxMail(microsoftAccount);
				inboxSum += inbox.getNbOfEmail();
			}
		}
		
		model.addAttribute("globalCount", inboxSum);
		return "GlobalInbox";
		
	}
	
}
