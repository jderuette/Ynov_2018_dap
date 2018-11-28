package fr.ynov.dap.dap.services.google;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

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

import fr.ynov.dap.dap.Config;

/**
 * The Class GoogleService.
 */
@Service
public class GoogleService {
	/** The cfg. */
	@Autowired
	private Config cfg;

	/**
	 * Gets the config.
	 *
	 * @return the config
	 */

	protected Config getCfg() {
		return cfg;
	}

	private final List<String> SCOPES = new ArrayList<String>();

	public GoogleService() throws IOException, GeneralSecurityException {
		SCOPES.add(CalendarScopes.CALENDAR_READONLY);
		SCOPES.add(GmailScopes.GMAIL_LABELS);
		SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
	}

	protected JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** The default user. */
	private String defaultUser = "";

	/**
	 * Gets the default user.
	 *
	 * @return the default user
	 */
	protected String getDefaultUser() {
		return defaultUser;
	}

	public List<String> getScopes() {
		return SCOPES;
	}

	/**
	 * Gets the credentials.
	 *
	 * @param userId the user id
	 * @return the credentials
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	protected Credential getCredentials(NetHttpTransport HTTP_TRANSPORT, String userKey)
			throws IOException, GeneralSecurityException {
		return getFlow().loadCredential(userKey);
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

		InputStream in = Config.class.getResourceAsStream(getCfg().getCredentialPath());
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(getCfg().getTokensPath())))
						.setAccessType("offline").build();
		return flow;
	}
}
