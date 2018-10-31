package fr.ynov.dap.dapM2.Services;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import fr.ynov.dap.dapM2.Config;

/**
 * The Class GmailService.
 */
public class GmailService extends GoogleService {
	
	/** The cfg. */
	private Config cfg;
	
	/**
	 * Instantiates a new gmail service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public GmailService() throws IOException, GeneralSecurityException {
		super();
		cfg = Config.getInstance();
	}

	/**
	 * Prints inbox.
	 *
	 * @param user the user
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public void print(String user) throws IOException, GeneralSecurityException {
        Label label = getLabel(user);
        System.out.printf("- %s\n", label.getName());
        System.out.printf("Nb messages : %s\n", label.getMessagesTotal());
        System.out.printf("Nb messages unread : %s\n", label.getMessagesUnread());
	}
	
	
	/**
	 * Gets the label.
	 * TODO thb by Djer "The" label, oui, mais quel "the" ?
	 * @param user the user
	 * @return the label
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	//TODO thb by Djer Le nom de cette méthode pourait indiquer qu'elle ne fait que "INBOX". Ou le rendre paramètrable via un paramètre.
	public Label getLabel(String user) throws IOException, GeneralSecurityException {
		if (user == null) {
			user = "benjamin.thomas.sso@gmail.com";
		}
		
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, cfg.getJSON_FACTORY(), getCredentials(HTTP_TRANSPORT, user))
                .setApplicationName(cfg.getAPPLICATION_NAME())
                .build();
 
        
        Label label = service.users().labels().get(user, "INBOX").execute();
        
		return label;
	}
}
