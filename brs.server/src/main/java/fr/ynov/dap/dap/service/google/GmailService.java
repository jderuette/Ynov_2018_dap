package fr.ynov.dap.dap.service.google;



import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.dap.controller.google.GmailController;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;


/**
 * The Class GmailService.
 */
@Service
public class GmailService extends GoogleServices {
	
	@Autowired 
	public AppUserRepository repo;

	/**
	 * Instantiates a new gmail service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	private GmailService() throws IOException, GeneralSecurityException {
		super();
	}
	
	/**
	 * Gets the label info.
	 *
	 * @param user the user
	 * @return the label info
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public Label getLabelInfo(String userKey) throws IOException, GeneralSecurityException {
	
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, getJSON_FACTORY(), getCredentials(userKey))
                .setApplicationName(getConfiguration().getApplicationName())
                .build();
        
        Label label = service.users().labels().get("me", "INBOX").execute();
        

		return label;
	}
	public Integer getNbMailUnread(String userKey) {
		
		Integer nbMailUnread = 0;
		AppUser user = repo.findByUserkey(userKey);
		if(user == null) {
			return 0;
		}
		for (GoogleAccount googleAccount : user.getGoogleAccounts()) {
			try {
				nbMailUnread += getLabelInfo(googleAccount.getAccountName()).getMessagesUnread();
				//TODO brs by Djer |Log4J| Contextualise tes messages (Qu'est qui a été fait avec le "googleAccount" ? )
				LOG.info("Google account : ",googleAccount);
			} catch (IOException e) {
				LOG.error("Google account : ",googleAccount);
				LOG.error(e);
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				LOG.error("Google account : ",googleAccount);
				LOG.error(e);
				e.printStackTrace();
			}
        }
		return nbMailUnread;
	}
}

