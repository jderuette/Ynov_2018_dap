package fr.ynov.dap.GoogleMaven;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

@Service
public class GoogleService {
	protected final static JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance(); 
	

	protected static Config configuration;
	//TODO elj by Djer Ne force pas à une category vide, Laisse Log4Jfaire, ou utilise le nom, qualifié, de la classe
	private static final Logger logger = LogManager.getLogger("");

	static String initList[] = { GmailScopes.GMAIL_READONLY, CalendarScopes.CALENDAR_READONLY , PeopleServiceScopes.CONTACTS_READONLY};
	static NetHttpTransport HTTP_TRANSPORT = null ;
	static ArrayList<String> list = new ArrayList<String>(Arrays.asList(initList));
	private static java.util.List<String> allSCOPES = list;

	public static void init() throws UnsupportedOperationException, GeneralSecurityException, IOException {
		HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	}
	
	/*protected static Credential getCredential(NetHttpTransport UserId) throws IOException {

		InputStream in = GMailService.class.getResourceAsStream(configuration.getClientSecretFile());
		if (in != null || allSCOPES==null) {
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

			return new AuthorizationCodeInstalledApp(
					new GoogleAuthorizationCodeFlow.Builder(UserId, JSON_FACTORY, clientSecrets, allSCOPES)
							.setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
							.setAccessType("offline").build(),
					new LocalServerReceiver()).authorize("user");
		} else {
			System.out.println("null");
			return null;

		}
	}*/
	//TODO elj by Djer Pourquoi static ? Les autres classes en hérite, et y donc acces (si protected)
	protected static  Credential getCredentials(NetHttpTransport HTTP_TRANSPORT) throws IOException {

		 final java.util.logging.Logger buggyLogger = java.util.logging.Logger.getLogger(FileDataStoreFactory.class.getName());
	        buggyLogger.setLevel(java.util.logging.Level.SEVERE);
		LocalServerReceiver localReceiver = new LocalServerReceiver.
                Builder().setPort(5050).build();
		
		InputStream in = GMailService.class.getResourceAsStream("/credentials.json");
		
			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, allSCOPES)
					.setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
					.setAccessType("offline").build();
            
			return new AuthorizationCodeInstalledApp( flow, localReceiver).authorize("user");
		 
	
	
	}
    
	//TODO elj by Djer Pourquoi static ? Les autres classes en hérite, et y donc acces (si protected)
	protected static GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {

	      InputStream in = GoogleService.class.getResourceAsStream("/credentials.json");
	      InputStreamReader reader = new InputStreamReader(in);
	        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);

	        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, allSCOPES)
	                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(configuration.getTokensDirectoryPath())))
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
	protected static Config getConfiguration() {
		return configuration;
	}
	
}
