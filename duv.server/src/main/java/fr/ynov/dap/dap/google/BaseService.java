package fr.ynov.dap.dap.google;

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
     * Initialize the logger.
     */
    private Logger logger = LogManager.getLogger(getClassName());

    /**
     * link config.
     */
    @Autowired
    private Config config;

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

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(clientSecretFile);
        // TODO duv by Djer Chargement d'un fichier Externe au Jar ?
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JACKSON_FACTORY, new InputStreamReader(is));

        String pathname = config.getDataStoreDirectory() + File.separator + tokenFile;

        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(pathname));

        return new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JACKSON_FACTORY,
                clientSecrets, scopes()).setDataStoreFactory(fileDataStoreFactory).setAccessType("offline").build();
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
        // TODO duv by Djer AuthorizationCodeInstalledApp Ne fonctionne pas bien en mode
        // "web". utiliser getFLow().loadCredential(userId)
        return new AuthorizationCodeInstalledApp(getFlow(), new LocalServerReceiver()).authorize(userId);

    }

    /**
     * @return the logger
     */
    protected Logger getLogger() {
        return logger;
    }

    /**
     * return the default instance a jackson factory.
     */
    protected static final JacksonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * get the current class name.
     *
     * @return ClassName
     */
    protected abstract String getClassName();

    /**
     * stock the defaultUser.
     */
    // TODO duv by DJer Evite de laisser trainer ton adresse email !
    private static String defaultUser = "Vic_aaaaaa_@_aaa_com";

    /**
     * @return the defaultUser
     */
    protected static String getDefaultUser() {
        return defaultUser;
    }

    /**
     * list Scopes required for all the service.
     *
     * @return list a scope
     */
    private List<String> scopes() {
        // TODO duv by Djer Reconstruire une Liste à chaque appel n'est pas très
        // efficace.
        List<String> scopes = new ArrayList<>();
        scopes.add(GmailScopes.GMAIL_LABELS);
        scopes.add(CalendarScopes.CALENDAR_READONLY);
        scopes.add(PeopleServiceScopes.CONTACTS_READONLY);
        scopes.add(Oauth2Scopes.USERINFO_EMAIL);
        return scopes;
    }

}
