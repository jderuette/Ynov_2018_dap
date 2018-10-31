package fr.ynov.dap.dap;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import fr.ynov.dap.dap.models.GmailResponse;
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
    //TODO brc by Djer final ? public ?
	static Logger logger = LogManager.getLogger(GmailService.class);	
	
    /**
     * Result mail inbox.
     *
     * @param userId the user id
     * @return the gmail response
     * @throws Exception the exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public GmailResponse resultMailInbox(String userId) throws Exception, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, cfg.getJSON_FACTORY(), getCredentials(HTTP_TRANSPORT, cfg.CREDENTIALS_FILE_PATH(), userId))
                .setApplicationName(cfg.getAPPLICATION_NAME())
                .build();
        // Print the labels in the user's account.
        String user = "me";
        
        Label label = service.users().labels().get(user, "INBOX").execute();
        //TODO brc by Djer Pourquoi journaliser le UserHome ici ??
        logger.info("userhome : " + System.getProperty("user.home"));
        logger.info("Nb messages : " + label.getMessagesTotal());
        logger.info("Nb messages unread : " + label.getMessagesUnread());
        
        return new GmailResponse(label.getMessagesTotal(), label.getMessagesUnread());
    }

}
