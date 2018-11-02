package fr.ynov.dap.dap;

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
//TODO bit by Djer Principe "ZeroConf" a Revoir, ici tu as du "NoConf".
public class Config {
	
	/** The credentials folder. */
    //TODO bot by Djer Si Maujuscule statici final ! 
	private String CREDENTIALS_FOLDER = "google/credential";
	
	/** The client secret dir. */
	private String CLIENT_SECRET_DIR = "google/client";
	
	/** The application name. */
	private String APPLICATION_NAME = "Hoc Dap";
	
	/** The tokens directory path. */
	private String TOKENS_DIRECTORY_PATH = "tokens";
	
	/** The credentials file path. */
	private final String CREDENTIALS_FILE_PATH = "/credentials.json";
	
	/** The json factory. */
	private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	/** The scopes. */
	private final List<String> SCOPES = new ArrayList<String>();
	
	/**
	 * Instantiates a new config.
	 */
	public Config() {
		this.SCOPES.add(CalendarScopes.CALENDAR_READONLY);
		this.SCOPES.add(GmailScopes.GMAIL_LABELS);
		this.SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
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
	 * Gets the scopes.
	 *
	 * @return the scopes
	 */
	public List<String> getSCOPES() {
		return SCOPES;
	}

	/**
	 * Gets the credentials folder.
	 *
	 * @return the credentials folder
	 */
	public String getCREDENTIALS_FOLDER() {
		return CREDENTIALS_FOLDER;
	}

	/**
	 * Gets the client secret dir.
	 *
	 * @return the client secret dir
	 */
	public String getCLIENT_SECRET_DIR() {
		return CLIENT_SECRET_DIR;
	}

	/**
	 * Gets the application name.
	 *
	 * @return the application name
	 */
	public String getAPPLICATION_NAME() {
		return APPLICATION_NAME;
	}
	
	/**
	 * Gets the tokens directory path.
	 *
	 * @return the tokens directory path
	 */
	public String getTOKENS_DIRECTORY_PATH() {
		return TOKENS_DIRECTORY_PATH;
	}

	/**
	 * Gets the credentials file path.
	 *
	 * @return the credentials file path
	 */
	public String getCredentialsFilePath() {
		return CREDENTIALS_FILE_PATH;
	}
	
	/**
	 * Gets the o auth 2 callback url.
	 *
	 * @return the o auth 2 callback url
	 */
	public String getoAuth2CallbackUrl() {
		return "/oAuth2Callback";
	}
	
}
