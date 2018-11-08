package fr.ynov.dap.dap;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

/**
 * @author Pierre Plessy
 */
class GoogleService {
    /**
     * Instantiate Logger.
     */
    private static final Logger log = LogManager.getLogger(GoogleService.class);
    /**
     * Instance of json factory.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    /**
     * List of authorization who the app needed.
     */
    private static final List<String> SCOPES = Arrays.asList(CalendarScopes.CALENDAR_READONLY,
            GmailScopes.GMAIL_LABELS, PeopleServiceScopes.CONTACTS_READONLY);

    /**
     * Get the config in Launcher.
     */
    @Autowired
    private Config config = new Config();

    /**
     * Get flow in which an end-user authorize the application to access data.
     *
     * @return GoogleAuthorizationCodeFlow
     * @throws IOException              : throw exception
     * @throws GeneralSecurityException : throw exception
     */
    public GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport;
        GoogleClientSecrets clientSecrets;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            InputStream in = Launcher.class.getResourceAsStream(config.getCredentialFolder());
//            InputStream in = new FileInputStream(config.getCredentialFolder());
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in, Charset.forName("UTF-8")));
        } catch (IOException | GeneralSecurityException e) {
            log.error("Error when trying to get Flow", e);
            throw e;
        }

        return new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(config.getClientSecretFile())))
                .setAccessType("offline").build();
    }

    /**
     * Get Credential for authorized user.
     *
     * @param userId : userKey
     * @return Credential
     * @throws IOException              : throw exception
     * @throws GeneralSecurityException : throw exception
     */
    protected Credential getCredentials(final String userId) throws IOException, GeneralSecurityException {
        GoogleAuthorizationCodeFlow flow = getFlow();
        return flow.loadCredential(userId);
    }

    /**
     * Get the app Config.
     *
     * @return Config
     */
    protected Config getConfig() {
        return config;
    }

    /**
     * Get the default instance JSON.
     *
     * @return JsonFactory
     */
    protected JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }
}
