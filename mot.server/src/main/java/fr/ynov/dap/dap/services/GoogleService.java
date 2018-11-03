package fr.ynov.dap.dap.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;

import fr.ynov.dap.dap.Config;

/**
 * The Class GoogleService.
 */
public class GoogleService {
	
	/**
	 * Instantiates a new google service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public GoogleService() throws IOException, GeneralSecurityException {
		//TODO mot by Djer Appeler super ?
	}
	
	/** The cfg. */
	@Autowired	
	//TODO mot by Djer Pourquoi static ?
	static Config cfg;
	
	/**
	 * Gets the config.
	 *
	 * @return the config
	 */
	//TODO mot by Djer Inutile.
	@Autowired	
		public static Config getConfig() {
		return cfg;
	}

	/**
	 * Sets the configuration.
	 *
	 * @param cfg the new configuration
	 */
	//TODO mot by Djer Inutile, tu fait l'autowire sur l'attribut deja !
	@Autowired	
	public void setConfiguration(Config cfg) {
		GoogleService.cfg = cfg;
	}
	

	/** The default user. */
	//TODO mot by Djer Evite de laisser trainer ton adresse email !
	private static String defaultUser = "tho_aaaa_@_aaaa_.com";

    /**
     * Gets the default user.
     *
     * @return the default user
     */
    protected static String getDefaultUser() {
        return defaultUser;
    }
	
	/**
	 * Gets the credentials.
	 *
	 * @param userId the user id
	 * @return the credentials
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	protected static Credential getCredentials(final String userId) throws IOException, GeneralSecurityException {
	    //TODO mot by Djer AuthorizationCodeInstalledApp fonctionne mal en mode "web". Utilise getFlow.loadCredential(userId)
	    return new AuthorizationCodeInstalledApp(getFlow(), new LocalServerReceiver()).authorize(userId);
	}

	/**
	 * Gets the flow.
	 *
	 * @return the flow
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public static GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
		//TODO mot by Djer Ce TODO ne semble plus d'actualité ! 
	    // TODO Auto-generated method stub
		
		InputStream in = Config.class.getResourceAsStream(cfg.getCredentialFilePath());
		//TODO mot by Djer Chargement d'un fichier externe au JAR ?
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(cfg.getJSON_FACTORY(), new InputStreamReader(in));

		
		return new GoogleAuthorizationCodeFlow.Builder(
	               GoogleNetHttpTransport.newTrustedTransport(), cfg.getJSON_FACTORY(), clientSecrets, cfg.getScopes())
	               .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(cfg.getTOKENS_DIRECTORY_PATH())))
	               .setAccessType("offline")
	               .build();
	}
	
}
