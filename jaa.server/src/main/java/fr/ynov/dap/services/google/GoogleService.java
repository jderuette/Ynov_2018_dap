package fr.ynov.dap.services.google;

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
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.Config;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.exceptions.AuthorizationException;

/**
 * @author adrij
 *
 */
public abstract class GoogleService {
    /**
     * Logger used for logs.
     */
    private static Logger logger = LogManager.getLogger();

    /**
     * AppUserRepository instantiate thanks to the injection of dependency.
     */
    @Autowired
    private AppUserRepository repository;
    /**
     * AppUser getter.
     * @return used by the child googleServices.
     */
    protected AppUserRepository getRepository() {
        return repository;
    }

    /**
     * config used by GoogleService.
     */
    @Autowired
    private Config config;
    /**
     * @return config used for GoogleService.
     */
    protected Config getConfig() {
        return config;
    }

    /**
     * JsonFactory instance.
     */
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    /**
     * Scopes. Used to ask right Google Api Permissions.
     */
    private List<String> scopes = new ArrayList<String>();


    /**
     * get credentials for children.
     * @param userKey user key for authentication
     * @return credentials
     * @throws AuthorizationException exception
     */

    protected Credential getCredentials(final String userKey) throws AuthorizationException {
        GoogleAuthorizationCodeFlow flow;
        Credential credential;
        try {
            flow = getFlow();
            credential = flow.loadCredential(userKey);
            if (credential == null) {
                throw new AuthorizationException("Bad userKey");
            }

            return credential;
        } catch (GeneralSecurityException gse) {
            logger.error(gse);
            throw new AuthorizationException("can't get credentials. Error: " + gse.getMessage(), gse);
        } catch (IOException ioe) {
            logger.error(ioe);
            throw new AuthorizationException("can't get credentials. Error: " + ioe.getMessage(), ioe);
        }
    }

    /**
     * get the Google authorization code flow for authentication.
     * @return Google authorization code flow
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     */
    protected GoogleAuthorizationCodeFlow getFlow() throws GeneralSecurityException, IOException {
        scopes.add(GmailScopes.GMAIL_LABELS);
        scopes.add(GmailScopes.GMAIL_READONLY);
        scopes.add(CalendarScopes.CALENDAR_READONLY);
        scopes.add(PeopleServiceScopes.CONTACTS_READONLY);
        scopes.add(PeopleServiceScopes.PLUS_LOGIN);
        logger.info("get flow - Scopes :" + scopes.toString());

        //TODO Get credential outside of the jar
        InputStream in = GoogleService.class.getResourceAsStream(config.getCredentialsFilePath());
        GoogleClientSecrets clientSecrets = null;
        try {
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        } catch (IOException ioe) {
            logger.error("failed to get client secrets", ioe);
        }
        //TODO jaa by Djer si pas de conf "interne" charger la conf "externe" au jar.

        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        GoogleAuthorizationCodeFlow flow = null;
        try {
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, clientSecrets, scopes)
                    //TODO jaa by Djer si dossier "token" à l'extérieur du Jar ?
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(config.getCredentialFolder())))
                    .setAccessType("offline")
                    .build();
        } catch (IOException ioe) {
            logger.error("failed to getFlow", ioe);
        }
        return flow;
    }
}
