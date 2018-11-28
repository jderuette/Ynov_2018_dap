package fr.ynov.dap.dap.service.google;

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

import Utils.LoggerUtils;
import fr.ynov.dap.dap.Config;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class GoogleServices.
 */

public abstract class GoogleServices extends LoggerUtils {

	

	/** The configuration. */
	@Autowired
	private Config configuration;

	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	@Autowired
	public  Config getConfiguration() {
		return configuration;
	}
	private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	public JsonFactory getJSON_FACTORY() {
		return JSON_FACTORY;
	}

	/**
	 * Sets the configuration.
	 *
	 * @param configuration the new configuration
	 */
	@Autowired
	public void setConfiguration(final Config cfg) {
		this.configuration = cfg;
	}

	private final List<String> SCOPES = new ArrayList<String>();
	
	public List<String> getSCOPES() {
		return SCOPES;
	}

	public GoogleServices() throws IOException, GeneralSecurityException {
		super();
		SCOPES.add(CalendarScopes.CALENDAR_READONLY);
		SCOPES.add(GmailScopes.GMAIL_LABELS);
		SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
	}

	/**
	 * Gets the credentials.
	 *
	 * @param HTTP_TRANSPORT the http transport
	 * @param path           the path
	 * @param user           the user
	 * @return the credentials
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	protected  Credential getCredentials(String userKey)
			throws IOException, GeneralSecurityException {
		GoogleAuthorizationCodeFlow flow = getFlow();
		return flow.loadCredential(userKey);
	}

	/**
	 * Gets the flow.
	 *
	 * @return the flow
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		InputStreamReader in = new InputStreamReader(
				new FileInputStream(new File(configuration.getCredentialsFilePath())), Charset.forName("UTF-8"));
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(getJSON_FACTORY(), in);
		return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, getJSON_FACTORY(), clientSecrets,
				getSCOPES())
						.setDataStoreFactory(
								new FileDataStoreFactory(new java.io.File(configuration.getTokensDirectoryPath())))
						.setAccessType("offline").build();
	}

}
