package fr.ynov.dap.services.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;
import fr.ynov.dap.Launcher;
import fr.ynov.dap.configurations.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * This is the main class inherited by all the services we consume from google.
 */
@Service
public class GoogleService {
    /**
     * Available OAuth 2.0 scopes for use with the google APIs.
     */
    private static final List<String> SCOPES = Arrays.asList(GmailScopes.GMAIL_LABELS, CalendarScopes.CALENDAR_READONLY, PeopleServiceScopes.CONTACTS_READONLY);
    /**
     * Low-level JSON library implementation based on Jackson 2.
     */
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * The logger of the GoogleService class.
     */
  //TODO mbf by Djer |Log4J| Devrait être static final
    private Logger logger = Logger.getLogger(GoogleService.class.getName());

    /**
     * The current configuration with which the app has been launched.
     */
    @Autowired
    private Config configuration;

    /**
     * This method returns a Credential which is a Thread-safe OAuth 2.0 helper for accessing protected resources using an access token, as well as
     * optionally refreshing the access token when it expires using a refresh token.
     * @param userKey This is the login of the user.
     * @return Credential
     * @throws IOException The method can throw an IOException.
     */
    protected final Credential getCredentials(final String userKey) throws IOException {
        // Load client secrets.
        //TODO mbf by Djer |API Google| Attention, le "new LocalServerReceiver" créer un serveur Web, à l'intérieru de ton serveru Web. Ca n'est pas utile (et dangereux). Tu peux simplement faire un getFlow().loadCredential(accountname)
        return new AuthorizationCodeInstalledApp(getFlow(), new LocalServerReceiver()).authorize(userKey);
    }

    /**
     * This method returns Thread-safe google OAuth 2.0 authorization code flow that manages and persists end-user
     *  * credentials.
     * @return GoogleAuthorizationCodeFlow
     */
    protected final GoogleAuthorizationCodeFlow getFlow() {

        NetHttpTransport httpTransport = null;
        GoogleAuthorizationCodeFlow flow = null;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException e) {
          //TODO mbf by Djer |Log4J| Evite d'ajouter le message de l'exeption à ton propre message
            logger.severe("GeneralSecurityException: Failed to get trusted Http transport connection with the following message:  " + e.getMessage());
        } catch (IOException e) {
          //TODO mbf by Djer |Log4J| Evite d'ajouter le message de l'exeption à ton propre message
            logger.severe("Failed to get trusted Http transport connection with the following message: " + e.getMessage());
        }
        InputStream in = Launcher.class.getResourceAsStream(configuration.getCredentialsFilePath());
        GoogleClientSecrets clientSecrets = null;
        try {
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        } catch (IOException e) {
          //TODO mbf by Djer |Log4J| Evite d'ajouter le message de l'exeption à ton propre message
            logger.severe("Failed to load the client secrets with the following message: " + e.getMessage());
        }

        try {
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(configuration.getTokensDirectoryPath())))
                .setAccessType("offline")
                .build();
        } catch (IOException e) {
          //TODO mbf by Djer |Log4J| Evite d'ajouter le message de l'exeption à ton propre message
            logger.severe("Failed to get the GoogleAuthorizationCodeFlow with the following message: " + e.getMessage());
        }
        return flow;
    }

    /**
     * The setter of the current configuration.
     * @param conf The configuration parameter.
     */
    public final void setConfiguration(Config conf) {
        this.configuration = conf;
    }

    /**
     * It returns the current configuration with which the app has been launched.
     * @return Config
     */
    protected final Config getConfiguration() {
        return configuration;
    }
}
