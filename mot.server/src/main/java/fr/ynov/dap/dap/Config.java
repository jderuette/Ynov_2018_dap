package fr.ynov.dap.dap;

import java.io.IOException;

import java.security.GeneralSecurityException;
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
//TODO mot by Djer le princiep "ZeroCOnf" ne remplace les règles de nomage de JAVA ! 
public class Config {
	
	/** The application name. */
    //TODO mot by Djer si Majuscule devrait être static final ! 
	private  String APPLICATION_NAME = "Google Calendar API Java Quickstart";
	
	/** The json factory. */
	private  JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	/** The tokens directory path. */
	private  String TOKENS_DIRECTORY_PATH = "tokens";
	
	/** The separator. */
	private String separator = System.getProperty("file.separator");
	
	/** The home path. */
	private String homePath = System.getProperty("user.home");

	/** The scopes. */
	private  final List<String> SCOPES = new ArrayList<String>();
	
	/** The credentials file path. */
	private  String CREDENTIALS_FILE_PATH = "/credentials.json";
	
	/** The Auth 2 callback url. */
	private  final String Auth2CallbackUrl = "/oAuth2Callback";
    
	/**
	 * Constructor Config.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
   public Config() throws IOException, GeneralSecurityException {
	   SCOPES.add(CalendarScopes.CALENDAR_READONLY);
	   SCOPES.add(GmailScopes.GMAIL_LABELS);
	   SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
   } 
   
   /**
    * SETTER TOKENS DIRECTORY.
    *
    * @param TOKENS_DIRECTORY_PATH the new tokens directory
    */
   public void setTokensDirectory(String TOKENS_DIRECTORY_PATH) {
	   this.TOKENS_DIRECTORY_PATH = homePath + separator + TOKENS_DIRECTORY_PATH;
   }
   
   /**
    * Get Application_name.
    *
    * @return APPLICATION_NAME
    */
   public String getAPPLICATION_NAME() {
	return APPLICATION_NAME;
   }

   /**
    * GET JSON_FACTORY.
    *
    * @return JSON_FACTORY
    */
	public JsonFactory getJSON_FACTORY() {
		return JSON_FACTORY;
	}
	
	/**
	 * GET TOKENS_DIRECTORY_PATH.
	 *
	 * @return TOKENS_DIRECTORY_PATH
	 */
	public String getTOKENS_DIRECTORY_PATH() {
		return TOKENS_DIRECTORY_PATH;
	}
	
	/**
	 * GET SCOPES.
	 *
	 * @return SCOPES
	 */
	public List<String> getScopes() {
		return SCOPES;
	}
	
	/**
	 * GET CREDENTIALS_FILE_PATH.
	 *
	 * @return CREDENTIALS_FILE_PATH
	 */
	public String getCredentialFilePath() {
		return CREDENTIALS_FILE_PATH;
	}
	
	/**
	 * SETTER CREDENTIALS_FILE_PATH.
	 *
	 * @param CREDENTIALS_FILE_PATH the new credential file path
	 */
	public void setCredentialFilePath(String CREDENTIALS_FILE_PATH) {
		   this.CREDENTIALS_FILE_PATH = homePath + separator + CREDENTIALS_FILE_PATH;
	   }

	/**
     * GET Auth2CallbackUrl.
     * @return Auth2CallbackUrl
     */
	public String getoAuth2CallbackUrl() {
		// TODO Auto-generated method stub
		return Auth2CallbackUrl;
	}
}
