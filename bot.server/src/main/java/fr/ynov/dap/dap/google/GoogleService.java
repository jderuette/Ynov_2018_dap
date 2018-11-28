package fr.ynov.dap.dap.google;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.dap.Config;

/**
 * The Class GoogleService.
 */
public class GoogleService {

	/** The configuration. */
	@Autowired
	protected Config configuration;
	
	/** The json factory. */ 
	protected static final JacksonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	/** The scopes. */
	protected final List<String> scopes = new ArrayList<String>();
	
	/**
	 * Instantiates a new google service.
	 */
	public GoogleService() {
		this.scopes.add(CalendarScopes.CALENDAR_READONLY);
		this.scopes.add(GmailScopes.GMAIL_LABELS);
		this.scopes.add(PeopleServiceScopes.CONTACTS_READONLY);
	}

	/**
	 * Gets the scopes.
	 *
	 * @return the scopes
	 */
	public List<String> getScopes() {
		return scopes;
	}

	/**
	 * Gets the credentials.
	 *
	 * @param HTTP_TRANSPORT the http transport
	 * @param userId the user id
	 * @return the credentials
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String userId) throws IOException {
        GoogleAuthorizationCodeFlow flow = this.getFlow();        
        return flow.loadCredential(userId);
    }
	
	/**
	 * Gets the flow.
	 *
	 * @return the flow
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public GoogleAuthorizationCodeFlow getFlow() throws IOException {
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JACKSON_FACTORY,
        		new InputStreamReader(new FileInputStream(configuration.getCredentialsFilePath()), Charset.forName("UTF-8")));
		return new GoogleAuthorizationCodeFlow.Builder(
		        new NetHttpTransport(), JACKSON_FACTORY, clientSecrets, getScopes())
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(configuration.getTokensDirectoryPath())))
				.setAccessType("offline").build();
	}
	
	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	public Config getConfiguration() {
		return configuration;
	}
	
}
