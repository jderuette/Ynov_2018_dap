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
import com.google.api.services.people.v1.PeopleServiceScopes;
import com.google.api.services.oauth2.Oauth2Scopes;

import fr.ynov.dap.*;


/**
 * The Class GoogleService.
 */
public abstract class GoogleService<T> {
	
	/** The configuration. */
	@Autowired
	protected Config configuration;

	/** The Constant JSON_FACTORY. */
	protected static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();;
	
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
  //TODO cha by Djer |POO| Public inutile, scope est protected, les classe filles y ont accès, et il n'y a qu'elles qui y accede.
	public Config getConfiguration() {
		return configuration;
	}

	//TODO cha by Djer |POO| Public inutile, scope est protected, les classe filles y ont accès, et il n'y a qu'elles qui y accede.
	public static JsonFactory getJSON_FACTORY() {
		return JSON_FACTORY;
	}
	//TODO cha by Djer |POO| Public inutile, scope est protected, les classe filles y ont accès, et il n'y a qu'elles qui y accede.
	public static List<String> getSCOPES() {
		return SCOPES;
	}

	//TODO cha by Djer |POO| Utilisé nullpart (heureusement !). Ne crée pas de setter si ca n'est pas nécéssaire. Et soit TRES prudent avec un setter static !
	public static void setJSON_FACTORY(JsonFactory jSON_FACTORY) {
		JSON_FACTORY = jSON_FACTORY;
	}

	//TODO cha by Djer |POO| Utilisé nullpart (heureusement !). Ne crée pas de setter si ca n'est pas nécéssaire. Et soit TRES prudent avec un setter static !
	public static void setSCOPES(List<String> sCOPES) {
		SCOPES = sCOPES;
	}
	
	 protected abstract String getClassName();

	/**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
	 * @throws GeneralSecurityException 
     */
	 //TODO cha by Djer |POO| Pourquoi public ? Protected serait largement suffisant. Evite d'exposer ton code si ca n'est pas nécéssaire
    public Credential getCredentials(final String googleAccountName) throws IOException, GeneralSecurityException {

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = getFlow();
        
        return flow.loadCredential(googleAccountName);
        //return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userId);
    }
	
	/**
	 * Gets the flow.
	 *
	 * @return the flow
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException 
	 */
	protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
		/*InputStream in = GoogleService.class.getResourceAsStream(configuration.getCREDENTIALS_FILE_PATH());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(getJSON_FACTORY(), new InputStreamReader(in));*/

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
     * Create new Gmail service for user.
     * @param accountName Current user
     * @return Instance of gmail services provided by Google API
     * @throws GeneralSecurityException Exception
     * @throws IOException Exception
     * @throws NoConfigurationException Thrown when no configuration found
     */
    public T getService(final String accountName)
            throws IOException, GeneralSecurityException {

        Credential credential = getCredentials(accountName);

        final String appName = getConfiguration().getApplicationName();
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        return getGoogleClient(credential, httpTransport, appName);
    }

	protected abstract T getGoogleClient(Credential credentail, NetHttpTransport httpTransport, String appName);
}
