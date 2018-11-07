package fr.ynov.dap.dap.service;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;

import fr.ynov.dap.dap.Config;

/**
 * Google Service.
 * @author loic
 */
public class GoogleService {
	/**
	 * logger for log
	 */
    private static final Logger LOGGER = LogManager.getLogger(GoogleService.class);
    /**
     * config variable.
     */
    @Autowired
	private Config cfg;
	/**
	 * flow variable.
	 */
	private GoogleAuthorizationCodeFlow flow;

	/**
	 * getter config.
	 * @return cfg
	 */
	protected Config getCfg() {
		return cfg;
	}

	/**
	 * get credential for services.
	 * @param httpTransport *http transport*
	 * @param path *path of credentials file*
	 * @param userId *id of user*
	 * @return Credential
	 * @throws GeneralSecurityException 
	 * @throws IOException *IOException*
	 */
	protected Credential getCredentials(
        final NetHttpTransport httpTransport, final String userId) throws IOException, GeneralSecurityException {
        Credential authorize = null;
		authorize = new AuthorizationCodeInstalledApp(
	    getFlow(), new LocalServerReceiver()).authorize(userId);
        return authorize;
    }
	/**
	 * Get flow for googleAccount.
	 * @return GoogleAuthorizationCodeFlow
	 * @throws GeneralSecurityException *GeneralSecurityException*
	 * @throws IOException *IOException*
	 */
	public GoogleAuthorizationCodeFlow getFlow() throws GeneralSecurityException, IOException {
		// Load client secrets.
        InputStream in = GoogleService.class.getResourceAsStream(cfg.getCredentialsFilePath());

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(cfg.getJsonFactory(), new InputStreamReader(in));

        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		return new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, cfg.getJsonFactory(), clientSecrets, cfg.getScopes())
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(cfg.getTokenDirectoryPath())))
                .setAccessType("offline")
                .build();
	}
}
