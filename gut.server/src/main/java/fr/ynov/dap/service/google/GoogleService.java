package fr.ynov.dap.service.google;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.Config;


public abstract class GoogleService {

	@Autowired private Config loadConfig;
	
	protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static List<String> scopes = null;
	private static final String user ="me";
	private static final Logger logger = LogManager.getLogger();
	
	/**
	 * récupère la config
	 * @return Config
	 */
	protected Config getConfiguration() {
		return loadConfig;
	}

	/**
	 * Met a jour la configuration
	 * @param configuration
	 */
	public void setConfiguration(Config configuration) {
	    //TODO gut by Djer |POO| Pourquoi une méthode Vide ? 
	}

	/**
	 * Initialisation des scopes pour les APIs Google
	 */
	public void init() {
		logger.debug("Initialisation des scopes pour les apis google");
		ArrayList<String> myscopes = new ArrayList<String>(
				Arrays.asList(
						GmailScopes.GMAIL_LABELS,
						GmailScopes.GMAIL_READONLY,
						CalendarScopes.CALENDAR_READONLY,
						PeopleServiceScopes.CONTACTS_READONLY
						)
				);
	    scopes = myscopes;
	    logger.debug("Chargement de la configuration");
	    //TODO gut by Djer |Spring| Pourquoi faire un "setConfig" avec comme valeur l'attribut qui est lui même, en théorie, mis à jour par le set Config ? 
	    setConfiguration(loadConfig);
	    
	}
	
	/** 
	 * charger l'autorisation accorde pour un utilisateur
	 * @param userKey
	 * @return Credential
	 * @throws IOException
	 */
	protected Credential getCredentials(String userKey) 
			throws IOException {
		logger.debug("Recuperation de l'autorisation pour un utilisateur");
		GoogleAuthorizationCodeFlow flow = getFlow();
		return flow.loadCredential(userKey);
        //return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userKey);
    }

	/**
	 * Récupération de l'utilisateur
	 * @return String
	 */
	protected static String getUser() {
		return user;
	}

	/**
	 * Flow de connection utilisé pour sauvegarde l'utilsiateur dans GoogleAccount
	 * @return GoogleAuthorizationCodeFlow
	 * @throws IOException
	 */
	public GoogleAuthorizationCodeFlow getFlow() throws IOException {
		
		InputStream in = GmailService.class
				.getResourceAsStream(
						loadConfig.getCredentialsFolder()
						);
		logger.debug("Recuperation des clientSecrets");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets
        		.load(JSON_FACTORY,
        				new InputStreamReader(in)
        				);
        logger.debug("Recuperation du flow google");
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        		loadConfig.getHTTP_TRANSPORT(),
        		JSON_FACTORY, clientSecrets, scopes
        		)
                .setDataStoreFactory(new FileDataStoreFactory(
                		new java.io.File(
                				loadConfig.getTokensDirectoryPath()
                				)
                		))
                .setAccessType("offline")
                .build();
        return flow;
	}

	public static Logger getLogger() {
	    //TODO gut by Djer |Log4J| Attention toutes tes classe filles vont logger dans la catégorie "GoogleService" ce qui n'est pas top
		return logger;
	}
	
	
}
