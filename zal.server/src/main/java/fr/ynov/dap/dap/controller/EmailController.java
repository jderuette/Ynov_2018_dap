package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.model.MailUnreadModel;
import fr.ynov.dap.dap.service.google.GmailService;
import fr.ynov.dap.dap.service.microsoft.OutlookService;

/**
 * The Class GmailController.
 */
@RestController
@RequestMapping("/mail")
public class EmailController {

	/** The gmail service. */
	@Autowired
	private GmailService gmailService;

	/** The outlook service. */
	@Autowired
	private OutlookService outlookService;

	/** The app user repository. */
	@Autowired
	private AppUserRepository appUserRepository;

	/**
	 * Gets the mail inbox unread.
	 *
	 * @param userKey
	 *            the user key
	 * @return the mail inbox unread
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException
	 *             the general security exception
	 */
	@RequestMapping("/unread")
	public MailUnreadModel getMailInboxUnread(@RequestParam final String userKey)
			throws IOException, GeneralSecurityException {
		AppUser user = appUserRepository.findByUserkey(userKey);

		Integer nbEmailUnreadGoogle = gmailService.getMailInBoxUnread(user);

		Integer nbEmailUnreadMicrosoft = outlookService.getMailInboxUnreadMicrosoftAccount(user);

		return new MailUnreadModel(nbEmailUnreadGoogle + nbEmailUnreadMicrosoft);
	}
}
