package fr.ynov.dap.dap.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

/**
 * The Class GMailService.
 */
@Service
public class GMailService extends GoogleService {

	/**
	 * Instantiates a new g mail service.
	 */
	public GMailService() {
		super();
	}

	/**
	 * Gets the service.
	 *
	 * @param userId the user id
	 * @return the service
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Gmail getService(String userId) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, configuration.getJSON_FACTORY(),
				getCredentials(HTTP_TRANSPORT, userId)).setApplicationName(configuration.getAPPLICATION_NAME()).build();
		return service;
	}

	
	/**
	 * Gets the nb mail inbox.
	 *
	 * @param userId the user id
	 * @return the nb mail inbox
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public GmailResponse  getNbMailInbox(String userId) throws IOException, GeneralSecurityException {
		Gmail service = getService(userId);
		String user = "me";
		Label listResponse = service.users().labels().get(user, "INBOX").execute();
		int MailUnReadCount = listResponse.getMessagesUnread();
		GmailResponse gmailRes = new GmailResponse(MailUnReadCount);
		return gmailRes;
	}
}
