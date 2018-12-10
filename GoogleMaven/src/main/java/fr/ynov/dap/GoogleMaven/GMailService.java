package fr.ynov.dap.GoogleMaven;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

@Service
public class GMailService extends GoogleService {

	//private static Config maConfig = getConfiguration();
	//private static GMailService instance;
	private final Logger logger = LogManager.getLogger();
	/**
	 * @return the instance
	 */

	/*public GMailService getInstance() {
		if (instance == null) {
			instance = new GMailService();
		}
		return instance;
	}*/

	/**
	 * 
	 * @return service gmail
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public Gmail getService(String userKey) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, userKey)).setApplicationName(configuration.getApplicationName()).build();
		return service;
	}

	/**
	 * 
	 * @param user
	 * @return number of unread mail with google api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */

	public String getNbUnreadEmails(String userKey) throws IOException, GeneralSecurityException {

		int i = -1;
		try {
			
			ListMessagesResponse response = getService(userKey).users().messages().list("me").setQ("in:unread").execute();

			List<Message> messages = new ArrayList<Message>();
			i=0;
			while (response.getMessages() != null) {
			    //TODO elj by Djer |API Google| Attention ton "i" s'incremente UNE fois par Pages !! Ton "vrai" nombre d'emails non lu est dans messages.length (à la fin de toutes les itérations)
				i++;
				
				messages.addAll(response.getMessages());
				if (response.getNextPageToken() != null) {
					String pageToken = response.getNextPageToken();

					response = getService(userKey).users().messages().list("me").setQ("in:unread").setPageToken(pageToken).execute();
				} else {
					break;
				}
			}
	
		} catch (IOException e) {
		    //TODO elj by Djer |Log4J| Contextualise tes messages et evite d'ajouter e.getmessages dans TON message
			logger.info(e.getMessage());
		}

		return Integer.toString(i);
		
	}

}
