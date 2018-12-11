package fr.ynov.dap;

import java.io.IOException;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

@Service

public class GmailService extends GoogleService {

	private GmailService() throws GeneralSecurityException, IOException {
		super();

	}

	private Gmail getService(String userId) throws IOException, GeneralSecurityException {
		Gmail service = new Gmail.Builder(http_transport, JSON_FACTORY, super.getCredentials(http_transport, userId))
				.setApplicationName(configuration.getApplicationName()).build();
		return service;
	}

	/**
	 * Fonction qui retourne les emails non-lus
	 * 
	 * @param user
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */

	public int getUnreadMessageCount(String userKey, String gUser) throws IOException, GeneralSecurityException {

		ListMessagesResponse listMessagesResponse = getService(userKey).users().messages().list(gUser).setQ("in:unread")
				.execute();
		List<Message> messages = new ArrayList<Message>();
		while (listMessagesResponse.getMessages() != null) {
			messages.addAll(listMessagesResponse.getMessages());
			if (listMessagesResponse.getNextPageToken() != null) {
				String pageToken = listMessagesResponse.getNextPageToken();
				listMessagesResponse = getService(userKey).users().messages().list(gUser).setPageToken(pageToken)
						.execute();
			} else {
				break;
			}
		}
		int unreadMessageCount = 0;
		for (Message message : messages) {
			unreadMessageCount++;
		}

		return unreadMessageCount;
	}

}