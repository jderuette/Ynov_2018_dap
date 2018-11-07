package fr.ynov.dap.google;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.google.api.services.oauth2.Oauth2Scopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.Config;
import fr.ynov.dap.exception.NoConfigurationException;

/**
 * Default service to manage API.
 * @param <T> Type of service we use.
 * @author Kévin Sibué
 *
 */
public abstract class GoogleAPIService<T> {

    /**
     * Logger instance.
     */
    private Logger logger = LogManager.getLogger(getClassName());

    /**
     * Current configuration.
     */
    @Autowired
    private Config config;

    /**
     * Current json factory.
     */
    private JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    /**
     * Store all scopes needed.
     */
    private static final List<String> ALL_SCOPES;

    /**
     * Init service.
     * Add every scopes needed add to scopes list.
     * Add this time the following scopes are added :
     * - Gmail Label
     * - Calendar ReadOnly
     * - Contact ReadOnly
     * - UserInfo Email
     */
    static {
        ALL_SCOPES = new ArrayList<String>();
        ALL_SCOPES.add(GmailScopes.GMAIL_LABELS);
        ALL_SCOPES.add(CalendarScopes.CALENDAR_READONLY);
        ALL_SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
        ALL_SCOPES.add(Oauth2Scopes.USERINFO_EMAIL);
    }

    /**
     * Return current configuration.
     * @return Current configuration
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Return current instance of Logger.
     * @return Logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Update current configuration for current service.
     * @param cfg New configuration
     */
    public void setConfig(final Config cfg) {
        this.config = cfg;
    }

    /**
     * return Json Factory.
     * @return Json factory
     */
    public JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    /**
     * Retrieve credential for userId.
     * If an error occurred (e.g. Bad credential, IO error) return null.
     * @param gAccountName of user
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws NoConfigurationException Thrown when no configuration found
     * @return User's credential
     */
    protected Credential getCredential(final String gAccountName)
            throws IOException, GeneralSecurityException, NoConfigurationException {

        if (getConfig() == null) {

            throw new NoConfigurationException();

        }

        GoogleAuthorizationCodeFlow flow = getFlows();

        return flow.loadCredential(gAccountName);

    }

    /**
     * Create new auth flow.
     * @return Instance of GoogleAuthorizationCodeFlow class
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws NoConfigurationException Thrown when no configuration found
     */
    public GoogleAuthorizationCodeFlow getFlows()
            throws IOException, GeneralSecurityException, NoConfigurationException {

        if (getConfig() == null) {

            throw new NoConfigurationException();

        }

        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        final String clientSecretFolder = getConfig().getClientSecretFile();
        final String tokenFile = getConfig().getCredentialFolder();

        InputStream in = GoogleAPIService.class.getResourceAsStream(clientSecretFolder);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(getJsonFactory(), new InputStreamReader(in));

        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(
                new File(getConfig().getDatastoreDirectory() + File.separator + tokenFile));

        return new GoogleAuthorizationCodeFlow.Builder(httpTransport, getJsonFactory(), clientSecrets, ALL_SCOPES)
                .setDataStoreFactory(fileDataStoreFactory).setAccessType("offline").build();

    }

    /**
     * Return service.
     * @param userId User id
     * @return Service
     * @throws GeneralSecurityException Exception
     * @throws IOException Exception
     * @throws NoConfigurationException Exception
     */
    public T getService(final String userId) throws NoConfigurationException, IOException, GeneralSecurityException {

        Credential cdt = getCredential(userId);

        if (cdt != null) {

            final String appName = getConfig().getApplicationName();
            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            return getGoogleClient(httpTransport, cdt, appName);

        }

        return null;

    }

    /**
     * Return current class name.
     * @return Class name
     */
    protected abstract String getClassName();

    /**
     * Return GoogleClient instance.
     * @param httpTransport HttpTransport instance
     * @param cdt Credential instance
     * @param appName Application Name
     * @return Instance of GoogleClient
     */
    protected abstract T getGoogleClient(NetHttpTransport httpTransport, Credential cdt, String appName);

}
