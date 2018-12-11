package fr.ynov.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

public class GoogleService {

	protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private List<String> scopes;
	protected NetHttpTransport http_transport;
	@Autowired
	protected Config configuration;

	public GoogleService() throws GeneralSecurityException, IOException {
		this.init();
	}

	public void setMaConfig(Config configuration) {
		this.configuration = configuration;
	}

	private void init() throws IOException, GeneralSecurityException {
		http_transport = GoogleNetHttpTransport.newTrustedTransport();
		scopes = new ArrayList<String>();
		scopes.add(CalendarScopes.CALENDAR_READONLY);
		scopes.add(GmailScopes.GMAIL_READONLY);
		scopes.add(PeopleServiceScopes.CONTACTS_READONLY);
	}

	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT
	 *            The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException
	 *             If the credentials.json file cannot be found.
	 * @throws GeneralSecurityException
	 *             google error
	 */
	protected Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String userId)
			throws IOException, GeneralSecurityException {

		return getFlow().loadCredential(userId);

	}

	protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

		InputStreamReader in = new InputStreamReader(new FileInputStream(configuration.getCredentialFolder()),
				Charset.forName("UTF-8"));
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, in);

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, scopes)
						.setDataStoreFactory(
								new FileDataStoreFactory(new java.io.File(configuration.getClientSecretFile())))
						.setAccessType("offline").build();
		return flow;
	}

	public DataStore<StoredCredential> getDataStore() throws IOException, GeneralSecurityException {
		return getFlow().getCredentialDataStore();

	}
}
