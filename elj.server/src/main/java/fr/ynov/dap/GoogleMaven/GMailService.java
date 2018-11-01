package fr.ynov.dap.GoogleMaven;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

@RestController
public class GMailService extends GoogleService {

	//private static Config maConfig = getConfiguration();

	private static GMailService instance;
	//TODO elj by Djer Ne force pas à une category vide, Laisse Log4Jfaire, ou utilise le nom, qualifié, de la classe
	private static final Logger logger = LogManager.getLogger("");
	/**
	 * @return the instance
	 */

	public static GMailService getInstance() {
		if (instance == null) {
			instance = new GMailService();
		}
		return instance;
	}

	public GMailService get() {

		return this;
	}

	/**
	 * 
	 * @return service gmail
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */

	public static Gmail getService() throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT)).setApplicationName(configuration.getApplicationName()).build();
		return service;
	}

	/**
	 * 
	 * @param user
	 * @return nomber of unread mail
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@RequestMapping("/UnreadMails/{user}")
	public static int getNbUnreadEmails(@PathVariable final String user) throws IOException, GeneralSecurityException {
		
		int i = 0;
		try {
			//final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName());
		  //  buggyLogger.setLevel(java.util.logging.Level.SEVERE);
			ListMessagesResponse response = getService().users().messages().list(user).setQ("in:unread").execute();
            
			List<Message> messages = new ArrayList<Message>();
			while (response.getMessages() != null) {
				i++;
				messages.addAll(response.getMessages());
				if (response.getNextPageToken() != null) {
					String pageToken = response.getNextPageToken();
                    
					response = getService().users().messages().list(user).setQ("in:unread").setPageToken(pageToken).execute();
				} else {
					break;
				}
			}
			System.out.println("Vous Avez "+i+" Mails non lu");
			return i;
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		return i;
	}

}
