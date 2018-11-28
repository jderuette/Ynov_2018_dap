package fr.ynov.dap.dap.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.services.ContactAllAccountService;

/**
 * The Class ContactAllAccountController.
 */
@RestController
public class ContactAllAccountController {

	@Autowired
	private ContactAllAccountService contactAllAccountService;

	/**
	 * Nb contact all account.
	 *
	 * @param model   the model
	 * @param userKey the user key
	 * @return the nb contact of all account
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	@RequestMapping("/nbContactAllAccount")
	public Integer nbContactAllAccount(ModelMap model, @RequestParam(value = "userKey", required = true) String userKey)
			throws IOException, GeneralSecurityException {
		Integer nbContact = 0;

		nbContact = contactAllAccountService.getAllContact(userKey);

		return nbContact;
	}
}
