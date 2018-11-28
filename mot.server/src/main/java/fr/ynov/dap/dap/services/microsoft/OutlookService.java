package fr.ynov.dap.dap.services.microsoft;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.data.google.AppUser;
import fr.ynov.dap.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.dap.data.interfaces.OutlookInterface;
import fr.ynov.dap.dap.data.microsoft.Message;
import fr.ynov.dap.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.dap.data.microsoft.PagedResult;
import fr.ynov.dap.dap.data.microsoft.TokenResponse;

/**
 * The Class OutlookService.
 */
@Service
public class OutlookService extends MicrosoftService {

	@Autowired
	AppUserRepository appUserRepository;

	protected Logger LOG = LogManager.getLogger(OutlookService.class);

	/**
	 * Gets the unread emails.
	 *
	 * @param user the user
	 * @return the unread emails for All account Microsoft
	 */
	public Integer getUnreadEmails(String user) {
		Integer nbUnreadEmails = 0;

		AppUser appUser = appUserRepository.findByUserKey(user);

		if (appUser == null) {
			return nbUnreadEmails;
		}

		for (MicrosoftAccount m : appUser.getMicrosoftAccount()) {
			nbUnreadEmails += getNbEmailsByAccount(m);
		}

		return nbUnreadEmails;
	}

	/**
	 * Gets the list message.
	 *
	 * @param userKey the user key
	 * @param name    the name
	 * @return the list message
	 */
	public Message[] getListMessage(String userKey, String name) {
		Message[] messages = null;

		AppUser appUser = appUserRepository.findByUserKey(userKey);

		if (appUser == null) {
			return messages;
		}

		for (MicrosoftAccount m : appUser.getMicrosoftAccount()) {
			if (m.getName().equals(name)) {
				messages = getListMailByAccount(m);
			}

		}

		return messages;
	}

	/**
	 * Gets the nb emails by account.
	 *
	 * @param account the account
	 * @return the nb emails Unread by account
	 */
	private Integer getNbEmailsByAccount(MicrosoftAccount account) {
		Integer response;
		if (account.getToken() == null) {
			// No tokens, user needs to sign in
			LOG.error("error", "please sign in to continue.");
			return 0;
		}

		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());
		OutlookInterface outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), null);

		// Retrieve messages from the inbox
		String folder = "inbox";
		// Sort by time received in descending order
		String sort = "receivedDateTime DESC";
		String properties = "isRead";
		Integer maxResults = 10;

		PagedResult<Message> messages = null;
		Integer nbUnreadEmails = 0;

		try {

			messages = outlookService.getMessages(folder, sort, properties, maxResults).execute().body();
			Message[] messageList = messages.getValue();

			for (int i = 0; i < messageList.length; i++) {
				if (!messageList[i].getIsRead()) {
					nbUnreadEmails++;
				}
			}
			response = nbUnreadEmails;
			return response;
		} catch (IOException e) {
			response = 0;
			LOG.error("Error during CountMicrosoftMail", e);
		}

		return response;
	}

	/**
	 * Gets the list mail by account.
	 *
	 * @param account the account
	 * @return the list mail by account
	 */
	public Message[] getListMailByAccount(MicrosoftAccount account) {
		Message[] response;
		if (account.getToken() == null) {
			// No tokens, user needs to sign in
			LOG.error("error", "please sign in to continue.");
			return null;
		}

		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());
		OutlookInterface outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), null);

		// Retrieve messages from the inbox
		String folder = "inbox";
		// Sort by time received in descending order
		String sort = "receivedDateTime DESC";
		String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
		Integer maxResults = 10;

		PagedResult<Message> messages = null;

		try {

			messages = outlookService.getMessages(folder, sort, properties, maxResults).execute().body();

			response = messages.getValue();
			return response;
		} catch (IOException e) {
			response = null;
			LOG.error("Error during listMailByAccount", e.getMessage());
		}

		return response;
	}

}
