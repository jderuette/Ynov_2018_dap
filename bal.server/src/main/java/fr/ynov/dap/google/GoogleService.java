package fr.ynov.dap.google;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

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
import com.google.api.services.oauth2.Oauth2Scopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.Config;

/**
 * The Class GoogleService.
 *
 * @param <T> the generic type
 */
public abstract class GoogleService<T> {
	
	/** The configuration. */
	@Autowired
	protected Config configuration;

	/** The json factory. */
	protected static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();;
	
	/** The scopes. */
	protected static List<String> SCOPES = new ArrayList<String>();
    
    /**
     * Instantiates a new google service.
     */
    public GoogleService() {
    	SCOPES.add(GmailScopes.GMAIL_LABELS);
		SCOPES.add(CalendarScopes.CALENDAR_EVENTS_READONLY);
		SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
		SCOPES.add(Oauth2Scopes.USERINFO_EMAIL);
	
    }
    
    /**
     * Gets the configuration.
     *
     * @return the configuration
     */
	public Config getConfiguration() {
		return configuration;
	}
	
	/**
	 * Gets the json factory.
	 *
	 * @return the json factory
	 */
	public static JsonFactory getJSON_FACTORY() {
		return JSON_FACTORY;
	}
	
	/**
	 * Gets the scopes.
	 *
	 * @return the scopes
	 */
	public static List<String> getSCOPES() {
		return SCOPES;
	}
	
	/**
	 * Sets the json factory.
	 *
	 * @param jSON_FACTORY the new json factory
	 */
	public static void setJSON_FACTORY(JsonFactory jSON_FACTORY) {
		JSON_FACTORY = jSON_FACTORY;
	}
	
	/**
	 * Sets the scopes.
	 *
	 * @param sCOPES the new scopes
	 */
	public static void setSCOPES(List<String> sCOPES) {
		SCOPES = sCOPES;
	}

	/**
	 * Gets the class name.
	 *
	 * @return the class name
	 */
	protected abstract String getClassName();

    /**
     * Gets the credentials.
     *
     * @param googleAccountName the google account name
     * @return the credentials
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    public Credential getCredentials(final String googleAccountName) throws IOException, GeneralSecurityException {
		GoogleAuthorizationCodeFlow flow = getFlow();
        return flow.loadCredential(googleAccountName);
    }
	
	/**
	 * Gets the flow.
	 *
	 * @return the flow
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {

        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        InputStreamReader file = new InputStreamReader(new FileInputStream(configuration.getGoogleCredentialsPath()),
                Charset.forName("UTF-8"));
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(getJSON_FACTORY(), file);
            final String tokenFile = getConfiguration().getCredentialFolder();
            FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(
                    new File(getConfiguration().getDatastoreFolder() + File.separator + tokenFile));
            return new GoogleAuthorizationCodeFlow.Builder(httpTransport, getJSON_FACTORY(), clientSecrets, SCOPES)
                    .setDataStoreFactory(fileDataStoreFactory).setAccessType("offline").build();
        
	}
	
    /**
     * Gets the service.
     *
     * @param accountName the account name
     * @return the service
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    public T getService(final String accountName)
            throws IOException, GeneralSecurityException {

        Credential credential = getCredentials(accountName);
        final String appName = getConfiguration().getApplicationName();
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return getGoogleClient(credential, httpTransport, appName);
    }

	/**
	 * Gets the google client.
	 *
	 * @param credentail the credentail
	 * @param httpTransport the http transport
	 * @param appName the app name
	 * @return the google client
	 */
	protected abstract T getGoogleClient(Credential credentail, NetHttpTransport httpTransport, String appName);
}
