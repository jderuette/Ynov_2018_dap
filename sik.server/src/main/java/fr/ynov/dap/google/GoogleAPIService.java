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
import com.google.api.services.oauth2.Oauth2Scopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.Config;
import fr.ynov.dap.exception.NoConfigurationException;

/**
 * Default service to manage API.
 * @author Kévin Sibué
 *
 */
public abstract class GoogleAPIService {

    /**
     * Logger instance.
     */
    //TODO sik by Djer il serait bien en "static final", même s'il dépend du "className" des enfants.
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
    //TODO sik by Djer Il pourrai rester static final, mais il faudra utiliser un "static initializer".
    // les "static initializer" sont a éviter, mais pas banni pour autant.
    //Tu peux jetter un oeil ici : https://stackoverflow.com/questions/335311/static-initializer-in-java
    private List<String> allScopes;

    /**
     * Default constructor.
     */
    public GoogleAPIService() {
        this.init();
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
     * Init service.
     * Add every scopes needed add to scopes list.
     * Add this time the following scopes are added :
     * - Gmail Label
     * - Calendar ReadOnly
     * - Contact ReadOnly
     * - UserInfo Email
     */
    public void init() {
        allScopes = new ArrayList<String>();
        allScopes.add(GmailScopes.GMAIL_LABELS);
        allScopes.add(CalendarScopes.CALENDAR_READONLY);
        allScopes.add(PeopleServiceScopes.CONTACTS_READONLY);
        allScopes.add(Oauth2Scopes.USERINFO_EMAIL);
    }

    /**
     * Retrieve credential for userId.
     * If an error occurred (e.g. Bad credential, IO error) return null.
     * @param userId of user
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws NoConfigurationException Thrown when no configuration found
     * @return User's credential
     */
    protected Credential getCredential(final String userId)
            throws IOException, GeneralSecurityException, NoConfigurationException {

        if (getConfig() == null) {

            throw new NoConfigurationException();

        }

        GoogleAuthorizationCodeFlow flow = getFlows();

        //TODO sik by Djer Inutile en mode "Web", un simple flow.loadCrednetial(userId) est mieux.
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userId);

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

        return new GoogleAuthorizationCodeFlow.Builder(httpTransport, getJsonFactory(), clientSecrets, allScopes)
                .setDataStoreFactory(fileDataStoreFactory).setAccessType("offline").build();

    }

    /**
     * Return current class name.
     * @return Class name
     */
    protected abstract String getClassName();

}
