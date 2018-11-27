package fr.ynov.dap.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.AppUserRepostory;
import fr.ynov.dap.google.service.CalendarService;
import fr.ynov.dap.google.service.ContactService;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.models.NbContactResponse;

@RestController
public class ContactRestController {

	@Autowired
	AppUserRepostory appUserRepository;

	@Autowired
	OutlookService outlookService;
	
	/** The contact service. */
	@Autowired 
	ContactService contactService;
	
	@RequestMapping("/nbContact")
	 public NbContactResponse contacts(Model model, HttpServletRequest request,
			 RedirectAttributes redirectAttributes, @RequestParam final String userKey) throws IOException, GeneralSecurityException {
		
		AppUser currentUser = appUserRepository.findByUserkey(userKey);
		
		NbContactResponse msNbContact = outlookService.getNbContactForAccount(currentUser);
		NbContactResponse googleNbContact = contactService.getNbContactFromAccount(currentUser);
		
		Integer totalContact = msNbContact.getNbContact() + googleNbContact.getNbContact();
		
		return new NbContactResponse(totalContact);

		
	}
	
	/**
	 * Gets the last event.
	 *
	 * @param userId the user id
	 * @return the contact response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/google/nbContact")
	public NbContactResponse GetGoogleNbContact (@RequestParam final String userKey) throws IOException, Exception {
		
		AppUser currentUser = appUserRepository.findByUserkey(userKey);

		return contactService.getNbContactFromAccount(currentUser);
	}
	
	
	@RequestMapping("/microsoft/nbContact")
	public NbContactResponse GetMsNbContact (@RequestParam final String userKey) throws IOException, Exception {
		
		AppUser currentUser = appUserRepository.findByUserkey(userKey);

		return outlookService.getNbContactForAccount(currentUser);
	}
}
