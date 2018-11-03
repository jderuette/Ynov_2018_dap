package fr.ynov.dap.dap.services;

import java.io.IOException;
import java.security.GeneralSecurityException;


import org.springframework.stereotype.Service;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;


/**
 * The Class GmailService.
 */
@Service
public class GmailService extends GoogleService {
	
	/**
	 * Instantiates a new gmail service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public GmailService() throws IOException, GeneralSecurityException {
		super();
	}
	
	
	/**
	 * Gets the service.
	 *
	 * @param user the user
	 * @return the service
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	private static Gmail getService(final String user) throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, cfg.getJSON_FACTORY(), getCredentials(user))
                .setApplicationName(cfg.getAPPLICATION_NAME())
                .build();
        
		return service;
      
	}
	
	/**
	 * Nbr email unread.
	 *
	 * @param user the user
	 * @return the integer
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static Integer nbrEmailUnread(final String user) throws GeneralSecurityException, IOException {
        String userEMAIL = user == null ? getDefaultUser() : user;
        Gmail gmail = getService(userEMAIL);
        //TODO mot by Djer Attention tu oblige a utilsier une adresse mail comme "user".
        // dans get(xxxxxx, "INBOX") il faut préciser un user Google ("me" par defaut)
        Label label = gmail.users().labels().get(userEMAIL, "INBOX").execute();

        return label.getMessagesUnread();

    }
}
