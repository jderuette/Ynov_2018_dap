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

@Controller
public class MailController {
	
	@Autowired
	AppUserRepostory appUserRepository;
	
	@Autowired
	OutlookService outlookService;

	@RequestMapping("/microsoft/mail")
	public String mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam final String userKey) throws IOException {
		
        AppUser currentUser = appUserRepository.findByUserkey(userKey);
        
        model.addAttribute("userKey", userKey);
        model.addAttribute("response", outlookService.getInboxMailForAccount(currentUser));
        
		return "mail";
	}
	
}
