package fr.ynov.dap.dap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
//TODO brc by Djer : Configure les "save actions" dans Eclipse (cf mémo eclipse) ca t'évieras de laisser trainer des imports inutiles
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;

/**
 * The Class GoogleService.
 */
public class GoogleService {
	
	/** The cfg. */
	@Autowired
	protected Config cfg;
	
	/** The flow. */
	protected GoogleAuthorizationCodeFlow flow;
	
	/** The logger. */
	private static Logger logger = LogManager.getLogger(GoogleService.class);
	
	/**
	 * Gets the credentials.
	 *
	 * @param HTTP_TRANSPORT the http transport
	 * @param path the path
	 * @param userId the user id
	 * @return the credentials
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String path, String userId) throws IOException {
    // Load client secrets.
		
    InputStream in = Config.class.getResourceAsStream(path);
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(cfg.getJSON_FACTORY(), new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    flow = new GoogleAuthorizationCodeFlow.Builder(
         HTTP_TRANSPORT, cfg.getJSON_FACTORY(), clientSecrets, cfg.getSCOPES())
         .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(cfg.getTOKENS_DIRECTORY_PATH())))
         .setAccessType("offline")
         .build();
    return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userId);
    }
	
	/**
	 * Gets the flow.
	 *
	 * @return the flow
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected GoogleAuthorizationCodeFlow getFlow() throws GeneralSecurityException, IOException {
		
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String path = cfg.CREDENTIALS_FILE_PATH();
        InputStream in = Config.class.getResourceAsStream(path);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(cfg.getJSON_FACTORY(), new InputStreamReader(in));
        //TODO brc by Djer Mais ... ? qu'est-ce qui n'est pas claire dans "secret" !!!?? C'est secret ne le met PAS (en entier) dans les logs !!!!
        logger.info("secret : " + clientSecrets);

        return new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, cfg.getJSON_FACTORY(), clientSecrets, cfg.getSCOPES())
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(cfg.getTOKENS_DIRECTORY_PATH())))
                .setAccessType("offline")
                .build();
	}
}
