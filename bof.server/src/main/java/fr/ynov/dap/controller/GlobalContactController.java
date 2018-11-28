package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.model.AppUserModel;
import fr.ynov.dap.model.Google.GoogleAccountModel;
import fr.ynov.dap.model.microsoft.OutlookAccountModel;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.service.Google.GoogleContactService;
import fr.ynov.dap.service.microsoft.MicrosoftContactsService;

@Controller
@RequestMapping("/contacts")
public class GlobalContactController {
	@Autowired
	MicrosoftContactsService microsoftContactsService;

	@Autowired
	GoogleContactService googleContactsService;

	@Autowired
	AppUserRepository appUserRepository;

	@RequestMapping("/count")
	public String countAllContacts(@RequestParam final String userKey, Model model) throws GeneralSecurityException, IOException {

		AppUserModel appUser = appUserRepository.findByUserKey(userKey);
		
		if(appUser != null) {
			int sumOfContacts = 0;
			
			for (GoogleAccountModel googleAccount : appUser.getGoogleAccounts()) {
				sumOfContacts += googleContactsService.getNbContacts(googleAccount.getAccountName());
			}
			
			for(OutlookAccountModel microsoftAccount : appUser.getMicrosoftAccounts()) {
				sumOfContacts += microsoftContactsService.countContacts(microsoftAccount);
			}
			model.addAttribute("nbOfContacts", sumOfContacts);
			return "GlobalContacts";
		}
		model.addAttribute("errorMessage", "userKey non reconnu");
		return "Error";
	}
}
