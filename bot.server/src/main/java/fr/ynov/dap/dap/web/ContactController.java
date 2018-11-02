package fr.ynov.dap.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.google.ContactService;

/**
 * The Class ContactController.
 */
@RestController
@RequestMapping(value="/contact")
public class ContactController {

	/** The contact service. */
	@Autowired
	private ContactService contactService;
	
	/**
	 * Gets the contacts.
	 *
	 * @param userId the user id
	 * @return the contacts
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value="/getContacts")
	public int getContacts(@RequestParam("userKey") final String userId) throws GeneralSecurityException, IOException {
		return contactService.getContacts(userId);
	}
}
