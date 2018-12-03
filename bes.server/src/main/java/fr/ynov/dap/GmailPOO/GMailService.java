package fr.ynov.dap.GmailPOO;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
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


//FIXME bes by Djer |POO| Semble être du "vieux code" suppprime le !
//@RestController
public class GMailService extends GoogleService {
	
	private static GMailService instance;


	/**
	 * @return the instance
	 */
//TODO bes by DJer Inutile, Spring le fait pour toi sur un @RestController
	public static GMailService getInstance() {
		if (instance == null) {
			instance = new GMailService();
		}
		return instance;
	}

	//TODO bes by Djer cf ma remarque sur le CalendarService
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
		
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(configuration.getApplicationName()).build();
		return service;
	}
	

	/**
	 * 
	 * @param user
	 * @return nomber of unread mail
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	//@GetMapping("/getNbUnreadEmails")
	//TODO bes by Djer Pourquoi "/sbaer" ? Le "nom" de l'utilsiateur est déja "capturé" sur la partie suivante de l'URL.
	@RequestMapping("/saber/{user}")
	public static int getNbUnreadEmails(@PathVariable final  String user) throws IOException, GeneralSecurityException {
         
		ListMessagesResponse response = getService().users().messages().list(user).setQ("in:unread").execute();
		int i = 0;
		List<Message> messages = new ArrayList<Message>();
		//TODO bes by Djer Attention, cette boucle gére la pagination, il y a pluseurs "Messages" dans chaque page
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

		return i;

	}
	@GetMapping("/bob")
	public static String bob() throws IOException, GeneralSecurityException {
		return ""+getNbUnreadEmails("")+"";
	}
}

