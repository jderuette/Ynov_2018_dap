package fr.ynov.dap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;


public abstract class GoogleService {

	@Autowired private Config loadConfig;
	
	protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static List<String> scopes = null;
	private static String user ="me";
	//TODO gut by Djer Ton IDE te dit que ca n'est plus utilisé. A supprimer ? bug ? Doublon ?
	private Config configuration;
	
	/**
	 * récupère la config
	 * @return Config
	 */
	//TODO gut by Djer Pourquoi en public ?
	public Config getConfiguration() {
		return loadConfig;
	}

	/**
	 * Met a jour la configuration
	 * @param configuration
	 */
	public void setConfiguration(Config configuration) {
		this.configuration = configuration;
	}

	/**
	 * Initialisation des scopes pour les APIs Google
	 */
	public void init() {
		ArrayList<String> myscopes = new ArrayList<String>(Arrays.asList(GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_READONLY, CalendarScopes.CALENDAR_READONLY, PeopleServiceScopes.CONTACTS_READONLY));
	    scopes = myscopes;
	    setConfiguration(loadConfig);
	}
	
	/**
	 * TODO gut by Djer ce commentaire est faux, ce code charge l'autorisation accordé par un des tes utilisateurs
	 * Authoriser l'accès aux API google 
	 * @param userKey
	 * @return Credential
	 * @throws IOException
	 */
	protected Credential getCredentials(String userKey) throws IOException {
        //TODO gut by Djer Ce commentaire est devenu faux !
	    // Load client secrets.
		GoogleAuthorizationCodeFlow flow = getFlow();
		//TODO gut by Djer AuthorizationCodeInstalledApp ne fonctionne pas en mode"web", utilise flow.loadCredential(userKey)
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userKey);
    }

	/**
	 * Récupération de l'utilisateur
	 * @return String
	 */
	//TODO gut by Djer Pourquoi en public ?
	public static String getUser() {
		return user;
	}

	//TODO gut by Djer Atention, en multi-utlisateur tu risques d'écraser la valeur !
	public static void setUser(String user) {
		GoogleService.user = user;
	}
	
	/**
	 * Flow de connection utilisé pour sauvegarde l'utilsiateur dans GoogleAccount
	 * @return GoogleAuthorizationCodeFlow
	 * @throws IOException
	 */
	public GoogleAuthorizationCodeFlow getFlow() throws IOException {
		InputStream in = GmailService.class.getResourceAsStream(loadConfig.getCredentialsFolder());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        		loadConfig.getHTTP_TRANSPORT(), JSON_FACTORY, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(loadConfig.getTokensDirectoryPath())))
                .setAccessType("offline")
                .build();
        return flow;
	}
}
