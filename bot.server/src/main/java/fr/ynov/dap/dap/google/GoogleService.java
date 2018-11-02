package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;


import fr.ynov.dap.dap.Config;


/**
 * The Class GoogleService.
 */
public class GoogleService {

	/** The configuration. */
	@Autowired
	protected Config configuration;
	

	/**
	 * Gets the credentials.
	 *
	 * @param HTTP_TRANSPORT the http transport
	 * @param userId the user id
	 * @return the credentials
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String userId) throws IOException {
        //Une grosse partie de code est celui de "getFLow()"
	    // Load client secrets.
        InputStream in = GoogleService.class.getResourceAsStream(configuration.getCredentialsFilePath());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(configuration.getJSON_FACTORY(), new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, configuration.getJSON_FACTORY(), clientSecrets, configuration.getSCOPES())
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(configuration.getTOKENS_DIRECTORY_PATH())))
                .setAccessType("offline")
                .build();
        //TODO bot by Djer AuthorizationCodeInstalledApp ne fonctionen pas ne mode "web". Utiliser flow.loadCredential(userId);
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userId);
    }
	
	/**
	 * Gets the flow.
	 *
	 * @return the flow
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected GoogleAuthorizationCodeFlow getFlow() throws IOException {
		InputStream in = GoogleService.class.getResourceAsStream(configuration.getCredentialsFilePath());
		//TODO bot by Djer Chargement d'un fichier externe ?
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(configuration.getJSON_FACTORY(), new InputStreamReader(in));

		return new GoogleAuthorizationCodeFlow.Builder(
		        new NetHttpTransport(), configuration.getJSON_FACTORY(), clientSecrets, configuration.getSCOPES())
				.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(configuration.getTOKENS_DIRECTORY_PATH())))
				.setAccessType("offline").build();
	}
	
	/**
	 * Gets the configuration.
	 *
	 * @return the configuration
	 */
	protected Config getConfiguration() {
		return configuration;
	}
	
}
