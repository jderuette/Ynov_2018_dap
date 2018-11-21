package fr.ynov.dap.dap.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.dap.model.MailModel;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gmail service.
 * @author loic
 */
@Service
public class GmailService extends GoogleService {
    /**
	 * logger for log.
	 */
	private static Logger logger = LogManager.getLogger(GmailService.class);
	/**
	 * Constructor.
	 */
	public GmailService() {
	}
	/**
	 * get service for retrieve mail.
	 * @param userId *id of user*
	 * @return service
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
    public final Gmail getGmailGoogleService(final String accountName) {
    	NetHttpTransport httpTransport;
    	Gmail service = null;
		try {
			httpTransport = GoogleNetHttpTransport.newTrustedTransport();
			service = new Gmail.Builder(httpTransport, getCfg().getJsonFactory(),
		        		getCredentials(httpTransport, accountName))
		                .setApplicationName(getCfg().getApplicationName())
		                .build();
		} catch (GeneralSecurityException | IOException e) {
			logger.error("Error while loading credentials", e);
		}
        return service;
    }
    /**
     * Get All mail inbox.
     * @param userId *id of user*
     * @return label
     * @throws IOException
     * @throws GeneralSecurityException
     */
   public final Label getLabel(final String accountName) {
    	Gmail service;
    	Label label = null;
		try {
			service = getGmailGoogleService(accountName);
	        label = service.users().labels().get("me", "INBOX").execute();
		} catch (IOException e) {
			logger.error("Error when get mail inbox", e);
		}
		return label;
    }
    /**
     * get mail inbox unread.
     * @param userId *id of user*
     * @return string
     */
    public final MailModel getMailInBoxUnread(final String accountName) {
    	Label label = getLabel(accountName);
    	return new MailModel(label.getMessagesTotal(), label.getMessagesUnread(), label.getName());
    }

}
