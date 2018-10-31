package fr.ynov.dap.dapM2.Services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;

import fr.ynov.dap.dapM2.Config;

/**
 * The Class GoogleService.
 */
public class GoogleService {
	
	/** The cfg. */
    //FIXME thb by Djer Configurable ?? utilise de l'IOC.
	Config cfg;
	
	/**
	 * Instantiates a new google service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	//TODO thb by Djer cette méthode est vraiment succeptible de lever ces exceptions ???
	public GoogleService() throws IOException, GeneralSecurityException {
		cfg = Config.getInstance();
	}

	/**
	 * Gets the credentials.
	 *
	 * @param HTTP_TRANSPORT the http transport
	 * @param user the user
	 * @return the credentials
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	protected Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String user) throws IOException, GeneralSecurityException {
	       GoogleAuthorizationCodeFlow flow = getFlow();
	       //Pour éviter des "url qui s'affiche", renvoie le flow.loadCredential(user).
	       return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(user);
	}

	
	
	/**
	 * Gets the flow.
	 *
	 * @return the flow
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
		InputStream in = Config.class.getResourceAsStream(cfg.getCredentials());
	    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(cfg.getJSON_FACTORY(), new InputStreamReader(in));
		
		return new GoogleAuthorizationCodeFlow.Builder(
	               GoogleNetHttpTransport.newTrustedTransport(), cfg.getJSON_FACTORY(), clientSecrets, cfg.getScopes())
	               .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(cfg.getTOKENS_DIRECTORY_PATH())))
	               .setAccessType("offline")
	               .build();
	}
}
