package fr.ynov.dap.dap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

/**
 * The Class Config.
 *
 * @author clement.brossette
 */

//TODO brc by Djer Le principe "ZeroConf" ne remplace les bonnes pratiques Java sur le nomage des attributs/constantes ! 
public class Config {
	
	/** The application name. */
    //TODO brc by Djer : si en majuscule = CONSTANTE et devrait static final ! 
	private String APPLICATION_NAME = "Gmail API Java Quickstart";
	
	/** The json factory. */
	//TODO brc by Djer Pas top de mélanger de la conf "developpeur" et de la conf "admin systeme"
	private JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    /** The tokens directory path. */
    private String TOKENS_DIRECTORY_PATH = "tokens";
    
    /** The credentials file path. */
    private final String CREDENTIALS_FILE_PATH = "/credentials.json";
    
    /** The scopes. */
    //TODO brc by Djer Pas top de mélanger de la conf "developpeur" et de la conf "admin systeme"
    private final List<String> SCOPES = new ArrayList<String>();
    
    /** The credentials file path calendar. */
    //TODO brc by Djer ne semble plus utilisé, supprime le, c'est confusant
    private final String CREDENTIALS_FILE_PATH_CALENDAR = "/credentials_calandar.json";
    
    /**
     * Instantiates a new config.
     */
    public Config(){
    	SCOPES.add(CalendarScopes.CALENDAR_READONLY);
    	SCOPES.add(GmailScopes.GMAIL_LABELS);
    	SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
    }
    
    /**
     * Gets the application name.
     *
     * @return the application name
     */
    public final String getAPPLICATION_NAME() {
		return APPLICATION_NAME;
	}

	/**
	 * Gets the json factory.
	 *
	 * @return the json factory
	 */
	public final JsonFactory getJSON_FACTORY() {
		return JSON_FACTORY;
	}

	/**
	 * Gets the tokens directory path.
	 *
	 * @return the tokens directory path
	 */
	public final String getTOKENS_DIRECTORY_PATH() {
		return TOKENS_DIRECTORY_PATH;
	}

	/**
	 * Credentials file path.
	 *
	 * @return the string
	 */
	public final String CREDENTIALS_FILE_PATH() {
		return CREDENTIALS_FILE_PATH;
	}

	/**
	 * Gets the scopes.
	 *
	 * @return the scopes
	 */
	public final List<String> getSCOPES() {
		return SCOPES;
	}

	/**
	 * Gets the credentials file path calendar.
	 *
	 * @return the credentials file path calendar
	 */
	public final String getCREDENTIALS_FILE_PATH_CALENDAR() {
		return CREDENTIALS_FILE_PATH_CALENDAR;
	}

	/**
	 * Gets the o auth 2 callback url.
	 *
	 * @return the o auth 2 callback url
	 */
	public String getoAuth2CallbackUrl() {
		// TODO Auto-generated method stub
		return "/oAuth2Callback";
	}
}
