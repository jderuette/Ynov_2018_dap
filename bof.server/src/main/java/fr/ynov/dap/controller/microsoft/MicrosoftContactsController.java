package fr.ynov.dap.controller.microsoft;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.model.AppUserModel;
import fr.ynov.dap.model.ContactModel;
import fr.ynov.dap.model.MasterModel;
import fr.ynov.dap.model.microsoft.OutlookAccountModel;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.service.microsoft.MicrosoftContactsService;

@Controller
@RequestMapping("/microsoft")
public class MicrosoftContactsController {
	
	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	MicrosoftContactsService microsoftContactsService;
	
	@RequestMapping("/contacts")
	public MasterModel contacts(@RequestParam final String userKey,Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		int nbOfContact = 0;
		AppUserModel appUser = appUserRepository.findByUserKey(userKey);
		for (OutlookAccountModel outlookAccount : appUser.getMicrosoftAccounts()) {
			nbOfContact += microsoftContactsService.countContacts(outlookAccount);
		}
		return new ContactModel(nbOfContact);
	}
}
