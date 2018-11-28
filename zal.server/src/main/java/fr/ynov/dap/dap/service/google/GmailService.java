package fr.ynov.dap.dap.service.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.google.GoogleAccount;

import java.io.IOException;
import java.security.GeneralSecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Gmail service.
 * 
 * @author loic
 */
@Service
public class GmailService extends GoogleService {
	/**
	 * logger for log.
	 */
	private static Logger logger = LogManager.getLogger(GmailService.class);

	/**
	 * Constructor.
	 */
	public GmailService() {
	}

	/**
	 * get service for retrieve mail.
	 *
	 * @param accountName
	 *            the account name
	 * @return service
	 */
	public final Gmail getGmailGoogleService(final String accountName) {
		NetHttpTransport httpTransport;
		Gmail service = null;
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			service = new Gmail.Builder(httpTransport, this.jsonFactory, getCredentials(httpTransport, accountName))
					.setApplicationName(getCfg().getApplicationName()).build();
		} catch (GeneralSecurityException | IOException e) {
			logger.error("Error while loading credentials", e);
		}
		return service;
	}

	/**
	 * Get All mail inbox.
	 *
	 * @param accountName
	 *            the account name
	 * @return label
	 */
	public final Label getLabel(final String accountName) {
		Gmail service;
		Label label = null;
		try {
			service = getGmailGoogleService(accountName);
			label = service.users().labels().get("me", "INBOX").execute();
		} catch (IOException e) {
			logger.error("Error when get mail inbox", e);
		}
		return label;
	}

	/**
	 * get mail inbox unread.
	 *
	 * @param user
	 *            the user
	 * @return string
	 */
	public final Integer getMailInBoxUnread(AppUser user) {
		if (user.getGoogleAccounts().size() == 0) {
			return 0;
		}

		Integer nbUnreadMail = 0;

		for (GoogleAccount account : user.getGoogleAccounts()) {
			nbUnreadMail += getLabel(account.getName()).getMessagesUnread();
		}

		return nbUnreadMail;
	}

}
