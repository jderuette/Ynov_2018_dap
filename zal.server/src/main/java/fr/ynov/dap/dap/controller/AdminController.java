package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.microsoft.MicrosoftAccountRepository;
import fr.ynov.dap.dap.service.AdminService;
import fr.ynov.dap.dap.service.microsoft.OutlookService;

/**
 * The Class AdminController.
 */
@Controller
public class AdminController {

	/** The admin service. */
	@Autowired
	private AdminService adminService;

	/** The app user repository. */
	@Autowired
	private AppUserRepository appUserRepository;

	/** The outlook service. */
	@Autowired
	private OutlookService outlookService;

	/** The microsoft account repository. */
	@Autowired
	private MicrosoftAccountRepository microsoftAccountRepository;

	/**
	 * Gets the credential data store.
	 *
	 * @param model
	 *            the model
	 * @return the credential data store
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException
	 *             the general security exception
	 */
	@RequestMapping("/admin")
	public String getCredentialDataStore(final ModelMap model) throws IOException, GeneralSecurityException {

		model.addAttribute("credentials",
				adminService.getAllCredentialList(adminService.getCredentialsGoogleDataStore(),
						adminService.getCredentialsMicrosoft(microsoftAccountRepository)));
		return "admin";
	}

	/**
	 * Gets the list mail.
	 *
	 * @param model
	 *            the model
	 * @param userKey
	 *            the user key
	 * @return the list mail
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@RequestMapping("/admin/mails")
	public String getListMail(ModelMap model, @RequestParam final String userKey) throws IOException {

		AppUser user = appUserRepository.findByUserkey(userKey);
		model.addAttribute("messages", outlookService.getNextMessages(user));
		return "mail";
	}

	/**
	 * Gets the list contact.
	 *
	 * @param model
	 *            the model
	 * @param userKey
	 *            the user key
	 * @return the list contact
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping("/admin/contacts")
	public String getListContact(ModelMap model, @RequestParam final String userKey) throws Exception {
		AppUser user = appUserRepository.findByUserkey(userKey);
		model.addAttribute("contacts", outlookService.getContacts(user));

		return "contacts";
	}
}
