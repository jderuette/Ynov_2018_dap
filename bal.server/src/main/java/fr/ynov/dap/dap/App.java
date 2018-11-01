package fr.ynov.dap.dap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;


/**
 * The Class App.
 */
//@SpringBootApplication
public class App
{
    
    /** The Constant APPLICATION_NAME. */
    public static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    
    /** The Constant JSON_FACTORY. */
    public static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    /** The Constant TOKENS_DIRECTORY_PATH. */
    public static final String TOKENS_DIRECTORY_PATH = "tokens";
   
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = new ArrayList<String>();
    
    /** The Constant CREDENTIALS_FILE_PATH. */
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
 
    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = CalendarQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
 
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }
   
   
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    public static void main( String[] args ) throws IOException, GeneralSecurityException
    {
        SCOPES.add(GmailScopes.GMAIL_READONLY);
        SCOPES.add(CalendarScopes.CALENDAR_EVENTS_READONLY);
        SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
      // SpringApplication.run(App.class, args);
        CalendarQuickstart.main(args);
        GmailQuickstart.main(args);
        PeopleActivity.main(args);
    }
    
    /**
     * Load config.
     *
     * @return the config
     */
    //@Bean
    public static Config loadConfig () 
    {
    	return new Config(null);
    }
    
    
}