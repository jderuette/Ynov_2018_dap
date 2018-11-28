package fr.ynov.dap.dap.google.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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
public abstract class GoogleBaseService {
    /**
     * link config.
     */
    @Autowired
    private Config config;

    /**
     * Initialize the logger.
     */
    private Logger logger = LogManager.getLogger(getClassName());

    /**
     * return the default instance a jackson factory.
     */
    protected static final JacksonFactory JACKSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * list of all the scope required for the appli.
     */
    private static final List<String> SCOPES = Arrays.asList(GmailScopes.GMAIL_LABELS, CalendarScopes.CALENDAR_READONLY,
            PeopleServiceScopes.CONTACTS_READONLY, Oauth2Scopes.USERINFO_EMAIL);

    /**
     * stock the defaultUser.
     */
    private static String defaultUser = "victorTEST@gmail.com";

    /**
     * store and obtain a credential for accessing protected resources.
     *
     * @return the
     * @throws IOException              throw if the the file token file not exist
     * @throws GeneralSecurityException throw by GoogleNetHttpTransport
     */
    protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {

        final String clientSecretFileName = config.getGoogleClientSecretFile();
        final String tokenFile = config.getCredentialsFolder();
        final String clientSecretAbsolutPath = config.getDataStoreDirectory() + File.separator + clientSecretFileName;
        File clientSecretFile = new File(clientSecretAbsolutPath);

        FileInputStream istreamClientSecretFile = new FileInputStream(clientSecretFile);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JACKSON_FACTORY,
                new InputStreamReader(istreamClientSecretFile, Charset.forName("UTF-8")));

        String pathname = config.getDataStoreDirectory() + File.separator + tokenFile;

        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(new File(pathname));

        return new GoogleAuthorizationCodeFlow.Builder(GoogleNetHttpTransport.newTrustedTransport(), JACKSON_FACTORY,
                clientSecrets, SCOPES).setDataStoreFactory(fileDataStoreFactory).setAccessType("offline").build();
    }

    /**
     * get the credential from the userId.
     *
     * @param accountName alias for googleAccount
     * @return the credential of the userId
     * @throws IOException              throw by getFLow
     * @throws GeneralSecurityException throw by getFLow
     */
    protected Credential getCredential(final String accountName) throws IOException, GeneralSecurityException {
        return getFlow().loadCredential(accountName);
    }

    /**
     * @return the logger
     */
    protected Logger getLogger() {
        return logger;
    }

    /**
     * get the current class name.
     *
     * @return ClassName
     */
    protected abstract String getClassName();

    /**
     * @return the defaultUser
     */
    protected static String getDefaultUser() {
        return defaultUser;
    }

    /**
     * @return the config
     */
    protected Config getConfig() {
        return config;
    }
}
