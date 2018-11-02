package fr.ynov.dap.google;

import com.google.api.client.auth.oauth2.Credential;
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

import fr.ynov.dap.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Manage Google Service.
 * @author thibault
 *
 */
public abstract class GoogleService {
    /**
     * Global Config for Google Service.
     */
    @Autowired
    private Config config;

    /**
     * Json Factory for Google Service.
     */
    //TODO but by Djer Pourrai être final (pas la peine d'en avoir un par instance, même si on est dans un Singleton)
    private final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    /**
     * Logger for the class.
     */
    //TODO but by Djer Logger generalement en static (pas la peine d'en avoir un par instance)
    // final (pseudo-référence non modifiable)
    private Logger logger = LogManager.getLogger();

    /**
     * Creates an authorized Credential object.
     * @param userKey : user id associate token
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     * @throws GeneralSecurityException Security on Google API
     */
    protected Credential getCredentials(final String userKey) throws IOException, GeneralSecurityException {
        this.logger.info("Generate credentials Google API for user '" + userKey + "'");
        GoogleAuthorizationCodeFlow flow = this.getFlow();
        Credential cre = flow.loadCredential(userKey);
        if (cre == null) {
            //TODO but by Djer Est-ce que les credential t'auraient embeter ne mode Web par hasard ?
            // Je me permet quand même de te préciser que cette log n'est pas très contextualisée
            logger.error("ERRRREEEEEEEEEEEEUUUUUUUUUUUUUUURRRRRRRRR");
            throw new IOException("JACU");
        } else {
            logger.info(cre.getExpiresInSeconds());
            logger.info(cre.getAccessToken());
            logger.info(cre.getRefreshToken());
            //TODO but by Djer Attention refreshToken() demande un nouveau token ! (si le token actuel est expiré)
            // Enleve cette log une fois que tu as vue ce que tu voulais voir !
            logger.info(cre.refreshToken());
            logger.info(cre.getRefreshToken());
        }
        return cre;
    }

    /**
     * Creates an Google authorization flow.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     * @throws GeneralSecurityException Security on Google API
     */
    protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        this.logger.info("Generate new flow with scopes for Google API.");

        // Load client secrets.
        FileInputStream in = new FileInputStream(this.config.getCredentialsFilePath());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory,
                new InputStreamReader(in, Charset.forName("UTF-8")));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, jsonFactory,
                clientSecrets, getScopes())
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(this.config.getTokenDirPath())))
                        .setAccessType("offline").build();

        return flow;
    }

    /**
     * @return the config
     */
    protected Config getConfig() {
        return config;
    }

    /**
     * @return the jsonFactory
     */
    protected JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    /**
     * Generate array list who contains all scopes for this application.
     * @return array of scopes
     */
    private List<String> getScopes() {
        //TODO but by Djer Créer une nouvelle Liste à chaque "get" n'est pas top
        List<String> scopes = new ArrayList<String>();
        scopes.add(GmailScopes.GMAIL_LABELS);
        scopes.add(GmailScopes.GMAIL_READONLY);
        scopes.add(CalendarScopes.CALENDAR_READONLY);
        scopes.add(PeopleServiceScopes.CONTACTS_READONLY);
        scopes.add(PeopleServiceScopes.PLUS_LOGIN);
        return scopes;
    }
}
