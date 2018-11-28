package fr.ynov.dap.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.AppUserRepostory;
import fr.ynov.dap.microsoft.AuthHelper;
import fr.ynov.dap.microsoft.contract.OutlookApiService;
import fr.ynov.dap.microsoft.contract.OutlookServiceBuilder;
import fr.ynov.dap.microsoft.models.*;
import fr.ynov.dap.microsoft.service.OutlookService;

/**
 * The Class ContactsController.
 */
@Controller
public class ContactsController {
	
	/** The app user repository. */
	@Autowired
	AppUserRepostory appUserRepository;
	
	/** The outlook service. */
	@Autowired
	OutlookService outlookService;
	
	
	/**
	 * Contacts.
	 *
	 * @param model the model
	 * @param request the request
	 * @param redirectAttributes the redirect attributes
	 * @param userKey the user key
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping("/microsoft/contacts")
	public String contacts(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam final String userKey) throws IOException {

		AppUser currentUser = appUserRepository.findByUserkey(userKey);
		ArrayList<Contact> contacts = outlookService.getContactForAccount(currentUser);

		model.addAttribute("userKey", userKey);
		model.addAttribute("contacts", contacts);

		return "contacts";
	}
}