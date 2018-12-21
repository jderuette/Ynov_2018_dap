package fr.ynov.dap.services.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class GoogleService.
 */

@Service
public class GoogleService {

	@Autowired
	private Config conf;

	protected Config getConfig() {
		return conf;
	}
	

	protected Logger LOG = LogManager.getLogger(getClass().getName());

	/**
	 * Gets the json factory.
	 *
	 * @return the json factory
	 */
	//TODO thb by Djer |POO| Ton attribut est protected, ce getter est donc inutile
	protected JsonFactory getJSON_FACTORY() {
		return JSON_FACTORY;
	}

	/** The json factory. */
	//TODO thb by Djer |POO| Devrait être static final (cela justifirait ausis qu'il soit ecris en majuscule)
	protected JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	//TODO thb by Djer |POO| si tu l'écris en majuscule doit être static final
	protected List<String> SCOPES = new ArrayList<String>();

	/**
	 * Instantiates a new google service.
	 */
	public GoogleService() {
		SCOPES.add(GmailScopes.GMAIL_LABELS);
		SCOPES.add(CalendarScopes.CALENDAR_READONLY);
		SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
	}

	/**
	 * Creates an authorized Credential object.
	 *
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @param user           the user
	 * @return An authorized Credential object.
	 * @throws IOException              If the credentials.json file cannot be
	 *                                  found.
	 * @throws GeneralSecurityException the general security exception
	 */
	public Credential getCredentials(NetHttpTransport HTTP_TRANSPORT, String user)
			throws IOException, GeneralSecurityException {
//      LocalServerReceiver receier = new LocalServerReceiver.Builder().setPort(8888).build();
//    	return new AuthorizationCodeInstalledApp(getFlow(), receier).authorize(user);
		// lien à donner au client
		return getFlow().loadCredential(user);
	}

	/**
	 * Gets the flow.
	 *
	 * @return the flow
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
		NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		// Load client secrets.
		//TODO thb by Djer |Design Patern| Cette méthode ne peut charger un fichier QUE à l'intérieur du jar. Utilise un InputStream à la place
		InputStream in = GoogleService.class.getResourceAsStream(getConfig().getCredentialsPath());
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(getConfig().getTokensPath())))
						.setAccessType("offline").build();

		return flow;
	}
}
