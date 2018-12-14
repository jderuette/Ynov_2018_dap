package fr.ynov.dap.dap.controller.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.model.ContactModel;
import fr.ynov.dap.dap.service.google.GoogleContactService;

/**
 * The Class GoogleContactController.
 */
@RestController
@RequestMapping("/google/contacts")
public class GoogleContactController {

    //TODO zal by Djer |Audit Code| Tes outils d'audit de code t'indique que l'indetition n'est pas correct (tabulations au lieu d'espace). Configure ton formater dans ton IDE !
	/** The contact service. */
	@Autowired
	private GoogleContactService contactService;
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
		Integer contactGoogle = contactService.getNbContactFromGoogle(user);
		return new ContactModel(contactGoogle);
	}
}
