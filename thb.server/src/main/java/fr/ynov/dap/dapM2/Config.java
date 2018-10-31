package fr.ynov.dap.dapM2;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

/**
 * The Class Config.
 */
//TODO thb by Djer Lit mon commentaire sur ton "Config" du Client ! 
public class Config {
	
	/** The application name. */
	private  String APPLICATION_NAME = "Google Calendar API Java Quickstart";
	
	/** The json factory. */
	private  JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	/** The scopes. */
	//TODO thb by Djer Fausse bonne idée, ces scopes ne sont pas très bien géré dans l'application,
	// les laisser au plus proche de leur utilisatio nserait mieux. Deplus ce paramètre ne doit pas
	// être modifié par un "admin système" mais par un developpeur, donc autant ne pas mélanger.
	private  final List<String> SCOPES = new ArrayList<String>();
	
	/** The tokens directory path. */
	private  String TOKENS_DIRECTORY_PATH = "tokens";
	
	/** The credentials file path. */
	private  String CREDENTIALS_FILE_PATH = "/credentials.json";
	
	/** The callback path. */
	private  String CALLBACK_PATH = "/oAuth2Callback";
		
	/** The instance. */
	private static Config INSTANCE = null;
	    

	/**
	 * Instantiates a new config.
	 */
	private Config() {
	   SCOPES.add(CalendarScopes.CALENDAR_READONLY);
	   SCOPES.add(GmailScopes.GMAIL_LABELS);
	   SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
	}   
   
	 /**
 	 * Initialise singleton.
 	 *
 	 * @return new Config
 	 */
   public static synchronized Config getInstance() {
	   if (INSTANCE == null) {
		   INSTANCE = new Config();
	   }
	   return INSTANCE;
   }
   
   /**
    * Change path for credential and store credential.
    *
    * @param path the new path
    */
   public void setPath(String path) {
	   String racine = System.getProperty("user.home");
	   
	   //FIXME thb by Djer Lire la JavaDoc ! "replace" renvoie la chaine modifiée, si tu n'utilise pas la valeur renvoyée,
	   // cette ligne ne sert à rien du tout !
	   path.replace("/", File.separator);
	   path.replace("\"", File.separator);
	   
	   TOKENS_DIRECTORY_PATH = racine + path;
	   CREDENTIALS_FILE_PATH = racine + path + File.separator + "tokens";
   }
   
   /**
    * Getter app name
    * return Application name.
    *
    * @return the application name
    */
   public String getAPPLICATION_NAME() {
	return APPLICATION_NAME;
   }

   
	/**
	 * Gets the json factory.
	 *
	 * @return the json factory
	 */
	public JsonFactory getJSON_FACTORY() {
		return JSON_FACTORY;
	}

	 /**
 	 * Getter tokens path.
 	 *
 	 * @return tokens auth
 	 */
	public String getTOKENS_DIRECTORY_PATH() {
		return TOKENS_DIRECTORY_PATH;
	}
	
	 /**
 	 * Getter scopes.
 	 *
 	 * @return scopes
 	 */
	public List<String> getScopes() {
		return SCOPES;
	}
	
	 /**
 	 * Getter credential.
 	 *
 	 * @return the view to display
 	 */
	public String getCredentials() {
		return CREDENTIALS_FILE_PATH;
	}
	
	
	 /**
 	 * Getter callback path.
 	 *
 	 * @return callback path
 	 */
	public String getCallBack() {
		return CALLBACK_PATH;
	}
}
