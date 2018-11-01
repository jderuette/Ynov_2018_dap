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
@Controller
public class GoogleService {
	/**
	 * logger for log
	 */
  //TODO zal by Djer Devrait être static FINAL (et en majuscule du coup).
    private static Logger logger = LogManager.getLogger(GoogleService.class);
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
	//TODO zal by Djer Pouruqoi public ? Protected devrait suffir il me semble.
	public Config getCfg() {
		return cfg;
	}
    /**
     * setter config.
     * @param scfg *cfg*
     */
	//TODO zal by Djer Elle est injecté, tu veux vraiment la liasser modifiable ? (y compris APRES l'initialisation du flow ?)
	public void setCfg(final Config scfg) {
		this.cfg = scfg;
	}
    /**
     * setter flow.
     * @param sflow *flow*
     */
	//TODO zal by Djer Pourquoi laisser modifier le flow de l'extérieur ? C'est très risqué ! 
	public void setFlow(final GoogleAuthorizationCodeFlow sflow) {
		this.flow = sflow;
	}
	/**
	 * get credential for services.
	 * @param httpTransport *http transport*
	 * @param path *path of credentials file*
	 * @param userId *id of user*
	 * @return Credential
	 * @throws IOException *IOException*
	 */
	protected Credential getCredentials(
        final NetHttpTransport httpTransport,
			final String path, final String userId) {
        // Load client secrets.
        InputStream in = Config.class.getResourceAsStream(path);
        GoogleClientSecrets clientSecrets;
        Credential authorize = null;
        //TODO zal by Djer Code en grosse partie deja dans "getFLow()".
		try {
			clientSecrets = GoogleClientSecrets.load(cfg.getJsonFactory(), new InputStreamReader(in));
			this.flow = new GoogleAuthorizationCodeFlow.Builder(
	                httpTransport, cfg.getJsonFactory(), clientSecrets, cfg.getScopes())
	                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(cfg.getTokenDirectoryPath())))
	                .setAccessType("offline")
	                .build();
			//TODO zal by Djer en mode Web AuthorizationCodeInstalledApp n'est pas top. Utilise flow.loadCredential(userId) à la place
			authorize = new AuthorizationCodeInstalledApp(
					flow, new LocalServerReceiver()).authorize(userId);
		} catch (IOException e) {
			logger.error("Error while get credentials", e);
		}
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
