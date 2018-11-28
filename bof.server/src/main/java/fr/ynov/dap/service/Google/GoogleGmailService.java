package fr.ynov.dap.service.Google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.Gmail.Users.Messages;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import fr.ynov.dap.model.MailModel;
import fr.ynov.dap.model.MasterModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Florent
 * Service class for all the Gmail request
 */
@Service
public class GoogleGmailService extends GoogleService {

	
	public GoogleGmailService() throws GeneralSecurityException, IOException {
		super();
	}

	/**
	 * 
	 * @param userID id of user who try to access to the service
	 * @return Gmail Instance of Gmail service
	 * @throws GeneralSecurityException
	 * @throws IOException
	 * 
	 */
	private Gmail getService(String accountName) throws GeneralSecurityException, IOException{
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, cfg.getJSON_FACTORY(),
				getCredentials(accountName))
						.setApplicationName(cfg.getAPPLICATION_NAME()).build();
		return service;
	}


	/**
	 * 
	 * @param userID id of user who try to access to the service
	 * @return MailModel The request response formated in JSON
	 * @throws Exception
	 */
	public MasterModel getInboxMail(String accountName) throws Exception, IOException {
		Gmail service = getService(accountName);
		String user = "me";
		Label label = service.users().labels().get(user, "INBOX").execute();
		return new MailModel(label.getMessagesTotal());
	}
	
	
	

}
