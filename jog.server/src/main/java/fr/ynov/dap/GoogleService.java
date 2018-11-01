package fr.ynov.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

public class GoogleService {

	protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	//TODO jog by Djer Si en majuscule, devrait etre FINAL !
	private static List<String> SCOPES;
	protected NetHttpTransport HTTP_TRANSPORT;
	@Autowired
	protected Config configuration;

	public GoogleService() throws GeneralSecurityException, IOException {
		this.init();
	}

	public void setMaConfig(Config configuration) {
		this.configuration = configuration;
	}

	private void init() throws IOException, GeneralSecurityException {
		HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		SCOPES = new ArrayList<String>();
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	protected Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String userId) throws IOException {

	  //TODO jog by Djer Attention, tu ajoutes des scopes dans la liste à chaque "getCredential" !
		SCOPES.add(CalendarScopes.CALENDAR_READONLY);
		SCOPES.add(GmailScopes.GMAIL_READONLY);
		SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
		
		//TODO jog by Djer Une GROSSE partie de ce code est déja dans le "getFlow".
// Load client secrets.
		InputStream in = GoogleService.class.getResourceAsStream(configuration.getCredentialFolder());
		//TODO jog by Djer Chargement d'un fichier Externe au jar ?
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(
								new FileDataStoreFactory(new java.io.File(configuration.getClientSecretFile())))
						.setAccessType("offline").build();
		//TODO jog by Djer En mode Web il faut éviter le "AuthorizationCodeInstalledApp", renvoie directement flow.loadCredential(user);
		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userId);
	}

	protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		InputStream in = GoogleService.class.getResourceAsStream(configuration.getCredentialFolder());
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(
								new FileDataStoreFactory(new java.io.File(configuration.getClientSecretFile())))
						.setAccessType("offline").build();
		return flow;
	}
}