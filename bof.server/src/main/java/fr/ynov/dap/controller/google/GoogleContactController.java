package fr.ynov.dap.controller.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.model.ContactModel;
import fr.ynov.dap.model.MasterModel;
import fr.ynov.dap.service.Google.GoogleContactService;

/**
 * 
 * @author Florent
 * Handle all the request of the contact service
 */
@RestController
@RequestMapping(value="/google/contact")
public class GoogleContactController {
	/**
	 * Automatically injected attribute by the Autowired annotation
	 */
	@Autowired
	private GoogleContactService contactService;

	/**
	 * 
	 * @param userID Id of the user to access data
	 * @return The request response
	 * @throws Exception
	 * Map the path /count to the associated service method
	 */
	@RequestMapping(value="/count")
	public MasterModel getNbContact(@RequestParam final String userID) throws GeneralSecurityException, IOException {
		return new ContactModel(contactService.getNbContacts(userID));
	}
	
}
