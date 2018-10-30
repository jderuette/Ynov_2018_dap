package fr.ynov.dap.google;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;

import fr.ynov.dap.*;


/**
 * The Class GoogleService.
 */
public class GoogleService {
	//TODO cha by Djer Eviter les classe avec "que" du static.
	//Utiliser un VRAI service en utilisant Singleton. Dans le cas de DAP 
	//une classe parent aurait aussi fait l'affaire.
	
	/** The Constant JSON_FACTORY. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	/** The Constant TOKENS_DIRECTORY_PATH. */
	public static final String TOKENS_DIRECTORY_PATH = "tokens";
    
    /** The configuration. */
	//TODO cha by Djer Eclipse indique "non utilisé", pourquoi ne pas traiter ?
    @Autowired
    private Config configuration;
    
    /** The scopes. */
  //TODO cha by Djer Eclipse indique "non utilisé", pourquoi ne pas traiter ?
    private static List<String> scopes = null;
    
    /**
     * Instantiates a new google service.
     */
    //FIXME cha by Djer Ta principal méthodes est static, pourquoi avoir un constructeur ?
    public GoogleService() {
    }

	/**
	 * Gets the json factory.
	 *
	 * @return the json factory
	 */
	public static JsonFactory getJsonFactory() {
		return JSON_FACTORY;
	}

	/**
	 * Gets the tokens directory path.
	 *
	 * @return the tokens directory path
	 */
	public static String getTokensDirectoryPath() {
		return TOKENS_DIRECTORY_PATH;
	}

	/**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
	protected static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleService.class.getResourceAsStream(Config.getCredentialsFilePath());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
 
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, Config.getSCOPES())
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        //FIXME cha by Djer et la version multi-compte ?
        //FIXME cha by Djer Ne fonctionne pas correctement en mode "web"
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
}
