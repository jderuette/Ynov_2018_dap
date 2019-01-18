package fr.ynov.dap.googleService;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import fr.ynov.dap.Config;

@Service
public class GoogleService {
	protected final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	@Autowired
	protected Config configuration;
	protected  Logger log = LogManager.getLogger();
	protected NetHttpTransport HTTP_TRANSPORT;
	private GoogleClientSecrets clientSecrets;
	private InputStream in;

	/**
	 * @throws IOException
	 * @throws GeneralSecurityException
	 * @throws UnsupportedOperationException
	 * 
	 */

	public GoogleService() throws UnsupportedOperationException, GeneralSecurityException, IOException {

		log.info("GoogleService created" + this.toString());

	}

	/**
	 * @return the clientSecrets
	 */
	public GoogleClientSecrets getClientSecrets() {
		return clientSecrets;
	}

	/**
	 * @param clientSecrets the clientSecrets to set
	 */
	public void setClientSecrets(GoogleClientSecrets clientSecrets) {
		this.clientSecrets = clientSecrets;
	}

	/**
	 * @return the in
	 */
	public InputStream getIn() {
		return in;
	}

	/**
	 * @param in the in to set
	 */
	public void setIn(InputStream in) {
		this.in = in;
	}

	protected Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String user)
			throws IOException, GeneralSecurityException {
		Credential credencial = null;
		if (getIn() != null || configuration.getAllSCOPES() == null) {

			credencial = getFlow().loadCredential(user);

		} else {

			log.info("problem getCredencials user=" + user);

		}
		return credencial;
	}

	public GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
		
// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				getClientSecrets(), configuration.getAllSCOPES())
						.setDataStoreFactory(
								new FileDataStoreFactory(new java.io.File(configuration.getCredentialFolder())))
						.setAccessType("offline").build();
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

	@PostConstruct
	public void init() throws UnsupportedOperationException, GeneralSecurityException, IOException {

		try {

			 HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			setIn(GoogleService.class.getResourceAsStream(configuration.getClientSecretFile()));
			setClientSecrets(GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in)));
 
		} catch (Exception e) {

			log.error("error while intializing Google Service   " + e.getMessage());

		}

	}
}
