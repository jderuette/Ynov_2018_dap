package fr.ynov.dap.dap.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.ContactService;
import fr.ynov.dap.dap.models.ContactResponse;

/**
 * The Class ContactController.
 */
@RestController
public class ContactController {

	/** The contact service. */
	@Autowired 
	ContactService contactService;
	
	/**
	 * Gets the last event.
	 *
	 * @param userId the user id
	 * @return the contact response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/contact")
	public ContactResponse GetLastEvent (@RequestParam final String userId) throws IOException, Exception {
		
		return contactService.resultContact(userId);
	}
	
}
