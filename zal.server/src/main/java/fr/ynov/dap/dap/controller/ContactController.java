package fr.ynov.dap.dap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.model.ContactModel;
import fr.ynov.dap.dap.service.google.GoogleContactService;
import fr.ynov.dap.dap.service.microsoft.OutlookService;

/**
 * The Class ContactController.
 */
@RestController
public class ContactController {

	/** The contact service. */
	@Autowired
	private GoogleContactService contactService;

	/** The app user repository. */
	@Autowired
	private AppUserRepository appUserRepository;

	/** The outlook service. */
	@Autowired
	private OutlookService outlookService;

	/**
	 * Gets the number of contacts.
	 *
	 * @param userKey
	 *            the user key
	 * @return the number of contacts
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping(value = "/contacts")
	public ContactModel getNumberOfContacts(@RequestParam final String userKey) throws Exception {
		AppUser user = appUserRepository.findByUserkey(userKey);

		Integer contactOutlook = outlookService.getNbContactFromOutlook(user);
		Integer contactGoogle = contactService.getNbContactFromGoogle(user);

		return new ContactModel(contactGoogle + contactOutlook);
	}
}
