package fr.ynov.dap.google.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.Config;

/**
 * The Class GoogleService.
 */
@Component("google")
public class GoogleService {
	
	/** The cfg. */
	@Autowired
	protected Config cfg;
	
	/** The flow. */
	protected GoogleAuthorizationCodeFlow flow;
	
	/** The json factory. */
	protected JsonFactory jsonFactory;
	
    /** The scopes. */
    protected List<String> scopes;
	
	/** The Constant logger. */
    //TODO brc by Djer |POO| Devrait être écris en Majuscule (car static et final)
    //TODO brc by Djer |Audit Code| static devrait être avant final (PMD/Checkstyle te préviennent de cette inversion)
	private final static Logger logger = LogManager.getLogger(GoogleService.class);


	/**
	 * Instantiates a new google service.
	 */
	public GoogleService() {
    	this.jsonFactory = JacksonFactory.getDefaultInstance();
    	this.scopes = new ArrayList<String>();
    	scopes.add(CalendarScopes.CALENDAR_READONLY);
    	scopes.add(GmailScopes.GMAIL_LABELS);
    	scopes.add(PeopleServiceScopes.CONTACTS_READONLY);
	}
	
	/**
	 * Gets the credentials.
	 *
	 * @param HTTP_TRANSPORT the http transport
	 * @param path the path
	 * @param accountName the account name
	 * @return the credentials
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String path, String accountName) throws IOException {
	    // Build flow and trigger user authorization request.
	    try {
			flow = getFlow();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
		    //TODO brc by Djer |Log4J| "e.printStackTrace()" affiche directement dans la console. Utilise une LOG à la place
			e.printStackTrace();
		}
	    return flow.loadCredential(accountName);
    }
	
	/**
	 * Gets the flow.
	 *
	 * @return the flow
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected GoogleAuthorizationCodeFlow getFlow() throws GeneralSecurityException, IOException {
		
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String path = cfg.getCredentialsFilePath();
        InputStreamReader file = new InputStreamReader(new FileInputStream(path),Charset.forName("UTF-8"));
        logger.info("factory : " + jsonFactory);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, file);

        return new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, jsonFactory, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(cfg.getTokensDirectoryPath())))
                .setAccessType("offline")
                .build();
	}
	
	/**
	 * Gets the credential data store.
	 *
	 * @return the credential data store
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public DataStore<StoredCredential> getCredentialDataStore() throws GeneralSecurityException, IOException {
		GoogleAuthorizationCodeFlow flow = getFlow();
		return flow.getCredentialDataStore();
	}
}
