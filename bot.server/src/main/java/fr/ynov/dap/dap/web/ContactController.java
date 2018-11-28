package fr.ynov.dap.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.google.GoogleContactService;
import fr.ynov.dap.dap.microsoft.OutlookContactService;

/**
 * The Class ContactController.
 */
@RestController
@RequestMapping("/contact")
public class ContactController {

	/** The contact service. */
	@Autowired
	private GoogleContactService contactService;
	
	/** The outlook contact service. */
	@Autowired
	private OutlookContactService outlookContactService;
	
	/**
	 * Gets the contacts.
	 *
	 * @param userId the user id
	 * @return the contacts
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping("/getNbContacts")
	public Map<String, Integer> getContacts(@RequestParam("userKey") final String userId) throws GeneralSecurityException,
	IOException {
		Map<String, Integer> response = new HashMap<>();
		response.put("google", contactService.getNbContactsForAllAccounts(userId));
		response.put("outlook", outlookContactService.getNbContactsForAllAccounts(userId));
		return response;
	}
	
}
