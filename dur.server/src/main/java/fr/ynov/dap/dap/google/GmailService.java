package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;

import fr.ynov.dap.dap.Config;

// TODO: Auto-generated Javadoc
/**
 * The Class GmailService.
 */
@Service
public class GmailService extends GoogleService{
	
	/**
	 * Gets the service.
	 *
	 * @param user the user
	 * @return the service
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	Gmail getService(String user) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, this.GetCredentials(user))
                .setApplicationName(Config.APPLICATION_NAME)
                .build();
        return service;
	}
	
	/**
	 * Gets the nb unread emails.
	 *
	 * @param user the user
	 * @return the nb unread emails
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public Integer getNbUnreadEmails(String user) throws IOException, GeneralSecurityException {
	    //TODO dur by Djer Ne fonction QUE si j'utilise "me" comme userId (get(xxxx, "INBOX") xxxxx doit etre un ID Google.
        Label label = this.getService(user).users().labels().get(user, "INBOX").execute();
		return label.getMessagesUnread();
	}
	
	/**
	 * Gets the mail labels.
	 *
	 * @param user the user
	 * @return the mail labels
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public List<Label> getMailLabels(String user) throws IOException, GeneralSecurityException{
        ListLabelsResponse listResponse = this.getService(user).users().labels().list("me").execute();
        List<Label> labels = listResponse.getLabels();
        return labels;
	}
}
