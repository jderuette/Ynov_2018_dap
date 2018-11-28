package fr.ynov.dap.google.service;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.GoogleAccount;
import fr.ynov.dap.models.NbMailResponse;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * The Class GmailService.
 */
@Service
public class GmailService extends GoogleService{
	
	/** The logger. */
	private final static Logger logger = LogManager.getLogger(GmailService.class);	
	
    /**
     * Result mail inbox.
     *
     * @param accountName the account name
     * @return the gmail response
     * @throws Exception the exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
	public NbMailResponse resultMailInbox(String accountName) throws Exception, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, jsonFactory, getCredentials(HTTP_TRANSPORT, cfg.getCredentialsFilePath(), accountName))
                .setApplicationName(cfg.getApplicationName())
                .build();
        // Print the labels in the user's account.
        String user = "me";
        
        Label label = service.users().labels().get(user, "INBOX").execute();
              
        return new NbMailResponse(label.getMessagesUnread());
    }
	
	/**
	 * Gets the nb unread mail for account.
	 *
	 * @param appUser the app user
	 * @return the nb unread mail for account
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	public Integer getNbUnreadMailForAccount(AppUser appUser) throws IOException, Exception {
		
		if (appUser.getGoogleAccounts().size() == 0) {
        	logger.info("no google account found for userkey : " + appUser.getUserKey());
            return 0;
        }
		
		int totalUnreadMail = 0;

		for(GoogleAccount account : appUser.getGoogleAccounts()) {
			String accountName = account.getName();

			if(accountName != null) {
				NbMailResponse response = this.resultMailInbox(accountName);
				totalUnreadMail += response.getNbUnreadMail();
			}
		}
		
		return totalUnreadMail;
	}

}
