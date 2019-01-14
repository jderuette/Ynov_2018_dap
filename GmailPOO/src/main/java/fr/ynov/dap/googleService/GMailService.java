package fr.ynov.dap.googleService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

@Service
public class GMailService extends GoogleService {

	private Logger log = LogManager.getLogger();

	/**
	 * @throws IOException
	 * @throws GeneralSecurityException
	 * @throws UnsupportedOperationException
	 * 
	 */

	public GMailService() throws UnsupportedOperationException, GeneralSecurityException, IOException {
		super();

	}

	/**
	 * 
	 * @return service Gmail
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */

	public Gmail getService(String user) throws GeneralSecurityException, IOException {

		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, user))
				.setApplicationName(configuration.getApplicationName()).build();
		return service;
	}

	/**
	 * 
	 * @param user
	 * @return nomber of unread mail
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */

	public List<Message> getListEmails(String user) throws IOException, GeneralSecurityException {
		log.info("Traitement pour le compte " + user);
		
		ListMessagesResponse response = getService(user).users().messages().list("me").setQ("in:unread").execute();

		List<Message> messages = new ArrayList<Message>();

		while (response.getMessages() != null) {

			messages.addAll(response.getMessages());
			if (response.getNextPageToken() != null) {
				String pageToken = response.getNextPageToken();

				response = getService(user).users().messages().list("me").setQ("in:unread").setPageToken(pageToken)
						.execute();
			} else {
				break;
			}
		}
		return messages;

	}

}
