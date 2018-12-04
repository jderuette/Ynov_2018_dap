package fr.ynov.dap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

/**
 *  Configuration class init all the constant to configure the app.
 * @author Florent
 */


@EnableJpaRepositories
public class Config {
    //TODO bof by Djer |Design Patern| Nomage de tes attributs !!! Il pourrais y avoir une constante pour la valeur par defaut et il DOIT y avoir un attribut pour laisser configurable.
    private String APPLICATION_NAME = "Gmail API Java Quickstart";
    private JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private String TOKENS_DIRECTORY_PATH = "tokens";
    private final String CREDENTIALS_FILE_PATH = "/credentials_oauth.json";
    private final List<String> SCOPES = new ArrayList<String>();
    private final String CREDENTIALS_FILE_PATH_CALENDAR = "/credentials-calendar.json";
    private String auth2CallbackUrl = "/oAuth2Callback";
    
    /**
     * Constructor of the config init the SCOPES of the google services.
     */
    public Config(){
    	SCOPES.add(CalendarScopes.CALENDAR_READONLY);
    	SCOPES.add(GmailScopes.GMAIL_LABELS);
    	SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
    }
    
    /**
     * 
     * @return The value of auth2CallbackUrl
     */
	public String getAuth2CallbackUrl() {
		return auth2CallbackUrl;
	}

	
    /**
     * 
     * @return value of applicationName
     */
    public String getAPPLICATION_NAME() {
		return APPLICATION_NAME;
	}

    /**
     * 
     * @return value of JsonFactory
     */
	public JsonFactory getJSON_FACTORY() {
		return JSON_FACTORY;
	}

	/**
	 * 
	 * @return value of tokens_directory_path
	 */
	public String getTOKENS_DIRECTORY_PATH() {
		return TOKENS_DIRECTORY_PATH;
	}

	/**
	 * 
	 * @return value of credentials_file_path
	 */
	public String getCREDENTIALS_FILE_PATH() {
		return CREDENTIALS_FILE_PATH;
	}

	/**
	 * 
	 * @return value of scopes
	 */
	public List<String> getSCOPES() {
		return SCOPES;
	}

}
