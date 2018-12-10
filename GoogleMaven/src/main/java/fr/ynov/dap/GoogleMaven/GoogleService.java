package fr.ynov.dap.GoogleMaven;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
public class GoogleService {
	protected final static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance(); 
	
    @Autowired
	protected Config configuration;
	static String initList[] = { GmailScopes.GMAIL_READONLY, CalendarScopes.CALENDAR_READONLY , PeopleServiceScopes.CONTACTS_READONLY};
	static NetHttpTransport HTTP_TRANSPORT = null ;
	static ArrayList<String> list = new ArrayList<String>(Arrays.asList(initList));
	private static java.util.List<String> allSCOPES = list;

	public static void init() throws UnsupportedOperationException, GeneralSecurityException, IOException {
		HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	}
	
	/**
	 * 
	 * @param user
	 * @return Get Credential method
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	protected Credential getCredentials(NetHttpTransport HTTP_TRANSPORT,String userKey) throws IOException, GeneralSecurityException {
		HTTP_TRANSPORT= GoogleNetHttpTransport.newTrustedTransport();
		//TODO elj by Djer |log4J| Utilise une constante "LOG" dans la classe et ne créé PAS un logger à chaque fois ! 
		 final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName());
	        buggyLogger.setLevel(java.util.logging.Level.SEVERE);

		InputStream in = new FileInputStream(new File(configuration.getCredentialFolder()));
		
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, allSCOPES)
					.setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
					.setAccessType("offline").build();
            return flow.loadCredential(userKey);
	
	}
    
	public GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
		HTTP_TRANSPORT= GoogleNetHttpTransport.newTrustedTransport();
	      InputStream in = new FileInputStream(new File(configuration.getCredentialFolder()));
	      InputStreamReader reader = new InputStreamReader(in);
	        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);

	        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, allSCOPES)
	                //TODO elj by Djer |Design Patern| Tu devrais utiliser ta conf au lieu d'une chaine ne "dur"
	                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
	                .setAccessType("offline")
	                .build();
	        return flow;
	}

	/**
	 * @param configuration the configuration to set
	 */
	public void setConfiguration(Config configuration) {
		this.configuration = configuration;
	}

	/**
	 * @return the configuration
	 */
	public Config getConfiguration() {
		return configuration;
	}
	
}
