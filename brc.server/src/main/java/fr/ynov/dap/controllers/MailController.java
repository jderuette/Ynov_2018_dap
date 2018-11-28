package fr.ynov.dap.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.AppUserRepostory;
import fr.ynov.dap.microsoft.service.OutlookService;

/**
 * The Class MailController.
 */
@Controller
public class MailController {
	
	/** The app user repository. */
	@Autowired
	AppUserRepostory appUserRepository;
	
	/** The outlook service. */
	@Autowired
	OutlookService outlookService;

	/**
	 * Mail.
	 *
	 * @param model the model
	 * @param request the request
	 * @param redirectAttributes the redirect attributes
	 * @param userKey the user key
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping("/microsoft/mail")
	public String mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam final String userKey) throws IOException {
		
        AppUser currentUser = appUserRepository.findByUserkey(userKey);
        
        model.addAttribute("userKey", userKey);
        model.addAttribute("response", outlookService.getInboxMailForAccount(currentUser));
        
		return "mail";
	}
	
}
