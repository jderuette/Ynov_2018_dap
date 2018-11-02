
package fr.ynov.dap.dap;

import java.util.ArrayList;

import org.springframework.context.annotation.Configuration;

import com.google.api.services.calendar.CalendarScopes;
//TODO dur by Djer Configure les "save action" de ton IDE. Cf mémo Eclipse.
import com.google.api.services.calendar.Calendar.Acl.List;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;


//TODO dur by Djer Ce todo est toujours d'actualité ?
// TODO: Auto-generated Javadoc
/**
 * The Class Config.
 */
//TODO dur by Djer Pourquoi cette annotation ?
@Configuration
//TODO dur by Djer Principe "ZeronCof" à revoir, ici tu a fait du "No Conf"
public class Config{

    /** The Constant CREDENTIALS_FILE_PATH. */
    public static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    
    /** The Constant TOKENS_DIRECTORY_PATH. */
    public static final String TOKENS_DIRECTORY_PATH = "tokens";
    
    /** The Constant APPLICATION_NAME. */
    public static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    
    /** The all scopes. */
    //TODO dur by Djer Tu ne devrais pas mélanger de la conf "Dev" et de la conf "admin system"
    public final ArrayList<String> ALL_SCOPES = new ArrayList<String>();
    
    /**
     * Instantiates a new config.
     */
    public Config() {
    	ALL_SCOPES.add(GmailScopes.GMAIL_LABELS);
    	ALL_SCOPES.add(CalendarScopes.CALENDAR_READONLY);
    	ALL_SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
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