package fr.ynov.dap;

import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;

@RestController
public abstract class GoogleService {
    @Autowired
    protected Config customConfig;
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    //TODO kea by Djer Pourquoi static ?
    protected static List<String> scopes = null;
    //TODO kea by Djer Pourquoi static ?
    protected static String user = "me";
    //TODO kea by Djer Pourquoi une majuscule à la varaible ?
    // Les Logger devraient etre static FINAL et donc écris en majuscule
    //TODO kea by Djer ton Logger devrait être privée. Chaque classe filel devrait avoir le sien
    protected static Logger logger;
    //TODO kea by Djer Pourquoi static ?
    protected static GoogleAuthorizationCodeFlow flow = null;
    protected GoogleClientSecrets clientSecrets;

    /**
     * instantiate all the scopes needed in the whole application.
     * @throws InstantiationException nothing special
     * @throws IllegalAccessException nothing special
     */
    public static void init() throws InstantiationException, IllegalAccessException {
        ArrayList<String> myscopes = new ArrayList<String>(
                Arrays.asList(GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_READONLY, CalendarScopes.CALENDAR_READONLY));
        scopes = myscopes;
        logger = LogManager.getLogger(GoogleService.class);
    }

    /**
     * get the configuration object set in Launcher class.
     * @return the configuration
     */
    public Config getCustomConfig() {
        return customConfig;
    }

    /**
     * set the configuration set in Launcher class.
     * @param newConfig autowired attribute
     */
    public void setCustomConfig(final Config newConfig) {
        this.customConfig = newConfig;
    }

    /**
     * Checks if the user that is trying to
     * connect has already been stored in credentials.
     * @param userKey the new userId
     * @return the token for the userId that corresponds to userKey
     * @throws IOException nothing special
     */
    protected Credential getCredentials(final String userKey) throws IOException {
        flow = getFlow();
        //TODO kea by Djer en mode "web" il ne faut plus utiliser le localReviever
        //return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userKey);
        return flow.loadCredential(userKey);
    }

    /**
     *
     * @return the current user that is using the application
     */
    public static String getUser() {
        return user;
    }

    /**
     * sets the user with you want to authenticate.
     * @param user the userId
     */
    public static void setUser(final String user) {
        GoogleService.user = user;
    }

    /**
     *  the application has access to users' data
     *  based on an access token and a refresh token
     *  to refresh that access token when it expires.
     * @return the token
     * @throws IOException nothing special
     */
    public GoogleAuthorizationCodeFlow getFlow() throws IOException {
        InputStream in = GmailService.class.getResourceAsStream(getCustomConfig().getCredentialsFolder());
        this.clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        return new GoogleAuthorizationCodeFlow.Builder(getCustomConfig().getHttpTransport(), JSON_FACTORY,
                clientSecrets, scopes)
                        .setDataStoreFactory(
                                new FileDataStoreFactory(new java.io.File(getCustomConfig().getTokensDirectoryPath())))
                        .setAccessType("offline").build();
    }
}
