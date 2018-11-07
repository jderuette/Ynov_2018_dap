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
//TODO zal by Djer Prinicpe "Zero Conf" à revoir, ici c'est du "no Conf" (à minima il faudrait les setter)
public class Config {
	
	private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
  
    /** The application name. */
    private String applicationName = APPLICATION_NAME ;
   
    /** The json factory. */
    private JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
   
    /** The token directory path. */
    private String tokenDirectoryPath = "tokens";
    
    /** The credential file path. */
    private final String credentialFilePath = "/credentials.json";
    
    /** The scopes. */
    private final List<String> scopes = new ArrayList<String>();

    
    /**
     * Instantiates a new config.
     */
    public Config() {
        scopes.add(CalendarScopes.CALENDAR_READONLY);
        scopes.add(PeopleServiceScopes.CONTACTS_READONLY);
        scopes.add(GmailScopes.GMAIL_LABELS);
    }
    
    /**
     * Gets the application name.
     *
     * @return the application name
     */
    public final String getApplicationName() {
        return applicationName;
    }
    
    /**
     * Gets the json factory.
     *
     * @return the json factory
     */
    public final JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    
    /**
     * Gets the token directory path.
     *
     * @return the token directory path
     */
    public final String getTokenDirectoryPath() {
        return tokenDirectoryPath;
    }
   
    /**
     * Gets the credentials file path.
     *
     * @return the credentials file path
     */
    public final String getCredentialsFilePath() {
       return credentialFilePath;
    }
 
    /**
     * Gets the scopes.
     *
     * @return the scopes
     */
    public final List<String> getScopes() {
        return scopes;
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
