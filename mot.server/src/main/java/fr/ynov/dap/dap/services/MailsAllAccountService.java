package fr.ynov.dap.dap.services;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.services.google.GmailService;
import fr.ynov.dap.dap.services.microsoft.OutlookService;

/**
 * The Class MailsAllAccountService.
 */
@Service
public class MailsAllAccountService {

	@Autowired
	private GmailService gmailService;

	@Autowired
	private OutlookService outlookService;

	/**
	 * Gets the all unread mail.
	 *
	 * @param user the user
	 * @return the sum of all unread mail
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public Integer getAllUnreadMail(String user) throws IOException, GeneralSecurityException {
		Integer sumEmailUnread = 0;

		sumEmailUnread = outlookService.getUnreadEmails(user) + gmailService.nbrEmailUnread(user);

		return sumEmailUnread;
	}

}
