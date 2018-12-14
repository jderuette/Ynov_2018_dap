package fr.ynov.dap.dap.service.google;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import fr.ynov.dap.dap.Config;

/**
 * The Class GoogleService.
 */
public abstract class GoogleService {

    //TODO zal by Djer |IDE| Ton ide t'indique que ca n'est pas utilisé. Utilise-le ou supprim-le s'il n'est vraiment pas utile.
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LogManager.getLogger(GoogleService.class);

	/** The cfg. */
	@Autowired
	private Config cfg;

	/**
	 * Gets the cfg.
	 *
	 * @return the cfg
	 */
	protected Config getCfg() {
		return cfg;
	}

	/** The json factory. */
	protected JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

	/** The scopes. */
	//TODO zal by Djer |POO| Met cette attribut en static cela évitera que chaque classe fille est sa propre instance
	private final List<String> scopes = new ArrayList<String>();

	/**
	 * Instantiates a new google service.
	 */
	public GoogleService() {
		scopes.add(CalendarScopes.CALENDAR_READONLY);
		scopes.add(PeopleServiceScopes.CONTACTS_READONLY);
		scopes.add(GmailScopes.GMAIL_LABELS);
	}

	/**
	 * Gets the credentials.
	 *
	 * @param httpTransport
	 *            the http transport
	 * @param accountName
	 *            the account name
	 * @return the credentials
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException
	 *             the general security exception
	 */
	protected Credential getCredentials(final NetHttpTransport httpTransport, final String accountName)
			throws IOException, GeneralSecurityException {
		return getFlow().loadCredential(accountName);
	}

	/**
	 * Gets the flow.
	 *
	 * @return the flow
	 * @throws GeneralSecurityException
	 *             the general security exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public GoogleAuthorizationCodeFlow getFlow() throws GeneralSecurityException, IOException {
		// Load client secrets.
		InputStreamReader in = new InputStreamReader(
		        //TODO zal by Djer |POO| Utilise "file.separator"
				new FileInputStream(cfg.getCredentialsFilePath() + "/" + cfg.getClientSecretFile()),
				Charset.forName("UTF-8"));

		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, in);

		final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		return new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory, clientSecrets, scopes)
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(cfg.getTokenDirectoryPath())))
				.setAccessType("offline").build();
	}

}
