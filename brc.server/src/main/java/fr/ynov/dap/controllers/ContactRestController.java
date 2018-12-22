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

/**
 * The Class ContactRestController.
 */
@RestController
//TODO brc by Djer |Rest API| Ton API serait encore plus claire avec des URL du type /contact/nb, contact/google/nb, contact/microsoft/nb ({Module}/{action}, ou {Module}/{porvidier}/{action})
public class ContactRestController {

	/** The app user repository. */
  //TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired
	AppUserRepostory appUserRepository;

	/** The outlook service. */
	//TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired
	OutlookService outlookService;
	
	/** The contact service. */
	//TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired 
	ContactService contactService;
	
	/**
	 * get number of contacts.
	 *
	 * @param model the model
	 * @param request the request
	 * @param redirectAttributes the redirect attributes
	 * @param userKey the user key
	 * @return the nb contact response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	@RequestMapping("/nbContact")
	 public NbContactResponse getNbContacts(Model model, HttpServletRequest request,
			 RedirectAttributes redirectAttributes, @RequestParam final String userKey) throws IOException, GeneralSecurityException {
		
		AppUser currentUser = appUserRepository.findByUserkey(userKey);
		
		NbContactResponse msNbContact = outlookService.getNbContactForAccount(currentUser);
		NbContactResponse googleNbContact = contactService.getNbContactFromAccount(currentUser);
		
		Integer totalContact = msNbContact.getNbContact() + googleNbContact.getNbContact();
		
		return new NbContactResponse(totalContact);

		
	}
	
	/**
	 * Gets the number of google contact.
	 *
	 * @param userKey the user key
	 * @return the contact response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/google/nbContact")
	public NbContactResponse GetGoogleNbContact (@RequestParam final String userKey) throws IOException, Exception {
		
		AppUser currentUser = appUserRepository.findByUserkey(userKey);

		return contactService.getNbContactFromAccount(currentUser);
	}
	
	
	/**
	 * Gets the ms nb contact.
	 *
	 * @param userKey the user key
	 * @return the nb contact response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/microsoft/nbContact")
	public NbContactResponse GetMsNbContact (@RequestParam final String userKey) throws IOException, Exception {
		
		AppUser currentUser = appUserRepository.findByUserkey(userKey);

		return outlookService.getNbContactForAccount(currentUser);
	}
}
