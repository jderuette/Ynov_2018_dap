package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.model.ContactModel;
import fr.ynov.dap.dap.model.MasterModel;
import fr.ynov.dap.dap.service.ContactService;

/**
 * 
 * @author Florent
 * Handle all the request of the contact service
 */
@RestController
@RequestMapping(value="/contact")
public class GoogleContactController {
	/**
	 * Automatically injected attribute by the Autowired annotation
	 */
	@Autowired
	private ContactService contactService;

	/**
	 * 
	 * @param userID Id of the user to access data
	 * @return The request response
	 * @throws Exception
	 * Map the path /count to the associated service method
	 */
	@RequestMapping(value="/count")
	public MasterModel getNbContact(@RequestParam final String userID) throws GeneralSecurityException, IOException {
		return contactService.getNbContacts(userID);
	}
	
}
