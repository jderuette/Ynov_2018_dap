package fr.ynov.dap.dap.services;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.services.google.ContactService;
import fr.ynov.dap.dap.services.microsoft.ContactMicrosoftService;

/**
 * The Class ContactAllAccountService.
 */
@Service
public class ContactAllAccountService {

	@Autowired
	private ContactService contactGoogleService;

	@Autowired
	private ContactMicrosoftService contactMicrosoftService;

	/**
	 * Gets the all contact.
	 *
	 * @param user the user
	 * @return the sum of all contact
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public Integer getAllContact(String user) throws IOException, GeneralSecurityException {
		Integer sumContact = 0;

		sumContact = contactGoogleService.getNbContact(user) + contactMicrosoftService.getContacts(user);

		return sumContact;
	}
}
