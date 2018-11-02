package fr.ynov.dap.dap.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import fr.ynov.dap.dap.config;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The Class GoogleServices.
 */
public class GoogleServices {

	/** The instance. */
	@SuppressWarnings("unused")
	//TODO brs by Djer Si tu n'a pas besoin de cet attribut, supprime le au lieu de le masquer
	private static GoogleServices INSTANCE = null;

	/** The configuration. */
	@Autowired
	//TODO brs by Djer Pourquoi static ?
	static config configuration;

	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	@Autowired
	public static config getConfiguration() {
		return configuration;
	}

	/**
	 * Sets the configuration.
	 *
	 * @param configuration the new configuration
	 */
	@Autowired
	public void setConfiguration(config configuration) {
		GoogleServices.configuration = configuration;
	}

	/**
	 * Instantiates a new google services.
	 *
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public GoogleServices() throws IOException, GeneralSecurityException {
	    //TODO brs by Djer Appeler super ?
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
	//TODO brs by Djer Paramètre "path" plus utilisé ! 
	//TODO brs by Djer paramère HTTP_TRANSPORT plus utilisé !
	protected static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String path, String user)
			throws IOException, GeneralSecurityException {
		// Load client secrets.
	    //TODO brs by Djer Pas le bon "logger"? Contectualise ton message.
		Log.debug("je suis un credentials");

		GoogleAuthorizationCodeFlow flow = getFlow();
		// Build flow and trigger user authorization request.

		//TODO brs by Djer AuthorizationCodeInstalledApp pose probleme en mode "web", utilise flow.loadCredential(user)
		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(user);
	}

	/**
	 * Gets the flow.
	 *
	 * @return the flow
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public static GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		InputStreamReader in = new InputStreamReader(
				new FileInputStream(new File(configuration.getCREDENTIALS_FILE_PATH())), Charset.forName("UTF-8"));
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(configuration.getJSON_FACTORY(), in);
		return new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, configuration.getJSON_FACTORY(), clientSecrets,
				configuration.getScopes())
						.setDataStoreFactory(
								new FileDataStoreFactory(new java.io.File(configuration.getTOKENS_DIRECTORY_PATH())))
						.setAccessType("offline").build();
	}

}
