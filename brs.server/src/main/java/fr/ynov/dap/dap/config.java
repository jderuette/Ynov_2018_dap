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
 * The Class config.
 */
public class config {

	/** The application name. */
    //TODO brs by Djer en MAJUSCULE donc semble etre une constante (devrait être static final)
	private String APPLICATION_NAME;

	/** The credentials file path. */
	 //TODO brs by Djer en MAJUSCULE donc semble etre une constante (devrait être static final)
	private String CREDENTIALS_FILE_PATH;

	/** The tokens directory path. */
	 //TODO brs by Djer en MAJUSCULE donc semble etre une constante (devrait être static final)
	private String TOKENS_DIRECTORY_PATH;
	

	/**
	 * Sets the application name.
	 *
	 * @param aPPLICATION_NAME the new application name
	 */
	public void setAPPLICATION_NAME(String aPPLICATION_NAME) {
		APPLICATION_NAME = aPPLICATION_NAME;
	}

	/**
	 * Sets the credentials file path.
	 *
	 * @param cREDENTIALS_FILE_PATH the new credentials file path
	 */
	public void setCREDENTIALS_FILE_PATH(String cREDENTIALS_FILE_PATH) {
		CREDENTIALS_FILE_PATH = cREDENTIALS_FILE_PATH;
	}

	/**
	 * Sets the tokens directory path.
	 *
	 * @param tOKENS_DIRECTORY_PATH the new tokens directory path
	 */
	public void setTOKENS_DIRECTORY_PATH(String tOKENS_DIRECTORY_PATH) {
		TOKENS_DIRECTORY_PATH = tOKENS_DIRECTORY_PATH;
	}

	/** The json factory. */
	private JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** The scopes. */
	private final List<String> SCOPES = new ArrayList<String>();

	/**
	 * Gets the credentials file path.
	 *
	 * @return the credentials file path
	 */
	public String getCREDENTIALS_FILE_PATH() {
		return CREDENTIALS_FILE_PATH;
	}

	/** The Server call back. */
	//TODO brs by Djer Fait gaffe à l'ordre, ne devrait pas être au milieu de getter/setter
	private final String ServerCallBack = "/oAuth2Callback";

	/**
	 * Instantiates a new config.
	 *
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	//TODO brs by Djer le code de cette méthoe est vraiment succeptible de lever ces Exceptions ?
	public config() throws IOException, GeneralSecurityException {
		SCOPES.add(CalendarScopes.CALENDAR_READONLY);
		SCOPES.add(GmailScopes.GMAIL_LABELS);
		SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
	}

	/**
	 * Instantiates a new config.
	 *
	 * @param app_name    the app name
	 * @param token_path  the token path
	 * @param credentials the credentials
	 */
	public config(String app_name, String token_path, String credentials) {
		APPLICATION_NAME = app_name;
		TOKENS_DIRECTORY_PATH = token_path;
		CREDENTIALS_FILE_PATH = credentials;
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
	 * Gets the json factory.
	 *
	 * @return the json factory
	 */
	public JsonFactory getJSON_FACTORY() {
		return JSON_FACTORY;
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
	 * Gets the scopes.
	 *
	 * @return the scopes
	 */
	public List<String> getScopes() {
		return SCOPES;
	}

	/**
	 * Gets the o auth 2 callback url.
	 *
	 * @return the o auth 2 callback url
	 */
	public String getoAuth2CallbackUrl() {
	    //TODO brs by Djer Ce TODO ne semble plus d'actualité
		// TODO Auto-generated method stub
		return ServerCallBack;
	}
}
