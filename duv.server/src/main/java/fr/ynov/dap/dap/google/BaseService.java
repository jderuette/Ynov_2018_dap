package fr.ynov.dap.dap.google;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.oauth2.Oauth2Scopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.dap.Config;

/**
 *
 * @author David_tepoche
 *
 */
public abstract class BaseService {

    /**
     * return the default instance a jackson factory.
     */
    protected static final JacksonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * list of the scope needed.
     */
    private static List<String> scopes = Arrays.asList(GmailScopes.GMAIL_LABELS, CalendarScopes.CALENDAR_READONLY,
            PeopleServiceScopes.CONTACTS_READONLY, Oauth2Scopes.USERINFO_EMAIL);

    /**
     * stock the defaultUser.
     */
    private static String defaultUser = "VicTEST@gmail.com";

    /**
     * @return the defaultUser
     */
    protected static String getDefaultUser() {
        return defaultUser;
    }

    /**
     * Initialize the logger.
     */
    private final Logger logger = LogManager.getLogger(getClassName());

    /**
     * link config.
     */
    @Autowired
    private Config config;

    /**
     * get the current class name.
     *
     * @return ClassName
     */
    protected abstract String getClassName();

    /**
     * @return the config
     */
    protected Config getConfig() {
        return config;
    }

    /**
     * get the credential from the userId.
     *
     * @param userId user Key
     * @return the credential of the userId
     * @throws IOException              throw by getFLow
     * @throws GeneralSecurityException throw by getFLow
     */
    protected Credential getCredential(final String userId) throws IOException, GeneralSecurityException {
        return getFlow().loadCredential(userId);
    }

    /**
     * store and obtain a credential for accessing protected resources.
     *
     * @return the
     * @throws IOException              throw if the the file token file not exist
     * @throws GeneralSecurityException throw by GoogleNetHttpTransport
     */
    protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {

        final String clientSecretFile = config.getClientSecretFile();
        final String tokenFile = config.getCredentialsFolder();

        final ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        final InputStream is = classloader.getResourceAsStream(clientSecretFile);

        final GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JACKSON_FACTORY, new InputStreamReader(is));

        final String pathname = config.getDataStoreDirectory() + File.separator + tokenFile;

        final FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(pathname));

        return new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JACKSON_FACTORY,
                clientSecrets, scopes).setDataStoreFactory(fileDataStoreFactory).setAccessType("offline").build();
    }

    /**
     * @return the logger
     */
    protected Logger getLogger() {
        return logger;
    }

}
