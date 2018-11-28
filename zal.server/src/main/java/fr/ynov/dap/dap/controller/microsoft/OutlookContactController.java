package fr.ynov.dap.dap.controller.microsoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.model.ContactModel;
import fr.ynov.dap.dap.service.microsoft.OutlookService;

/**
 * The Class OutlookContactController.
 */
@RestController
@RequestMapping("/outlook/contacts")
public class OutlookContactController {

	/** The outlook service. */
	@Autowired
	private OutlookService outlookService;

	/** The app user repository. */
	@Autowired
	private AppUserRepository appUserRepository;

	/**
	 * Gets the number of contacts.
	 *
	 * @param userKey
	 *            the user key
	 * @return the number of contacts
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping("/number")
	public ContactModel getNumberOfContacts(@RequestParam final String userKey) throws Exception {
		AppUser user = appUserRepository.findByUserkey(userKey);
		Integer nbContactOutlook = outlookService.getNbContactFromOutlook(user);
		return new ContactModel(nbContactOutlook);
	}
}
