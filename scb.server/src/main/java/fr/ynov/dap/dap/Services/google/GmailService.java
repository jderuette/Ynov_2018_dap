package fr.ynov.dap.dap.Services.google;

import java.io.IOException;

import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import fr.ynov.dap.dap.Config;


@Service
public class GmailService extends GoogleService{
	public GmailService() { 
		super();
	}
	/**
	 * 
	 * @param user
	 * @return return the service that provides info on mails
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	Gmail getService(String user) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, this.GetCredentials(user))
                //TODO scb by Djer Et la conf injectée ?
                .setApplicationName(Config.APPLICATION_NAME)
                .build();
        return service;
	}
	
	/**
	 * 
	 * @param user
	 * @return number of unread messages
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Integer getNbUnreadEmails(String user) throws IOException, GeneralSecurityException {
        Label label = this.getService(user).users().labels().get("me", "INBOX").execute();
		return label.getMessagesUnread();
	}
	
	/**
	 * 
	 * @param user
	 * @return all mails labels
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public List<Label> getMailLabels(String user) throws IOException, GeneralSecurityException{
        ListLabelsResponse listResponse = this.getService(user).users().labels().list("me").execute();
        List<Label> labels = listResponse.getLabels();
        return labels;
	}
	

}
