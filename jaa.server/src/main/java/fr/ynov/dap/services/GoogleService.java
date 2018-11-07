package fr.ynov.dap.services;

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
     * @throws Exception exception
     */
  //TODO jaa by Djer ne jamais lever une "Exception" toujours utiliser une sous classe (AuthorizationException ici serait parfait)
    protected Credential getCredentials(final String userKey) throws Exception {
        GoogleAuthorizationCodeFlow flow = getFlow();
        Credential credential = flow.loadCredential(userKey);
        if (credential == null) {
            throw new AuthorizationException("Bad userKey");
        }

        return credential;
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

        InputStream in = GoogleService.class.getResourceAsStream(config.getCredentialsFilePath());
        GoogleClientSecrets clientSecrets = null;
        try {
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        } catch (IOException e1) {
        	//TODO jaa by Djer devrait etre un ERROR ? tu p devrait passer en deuxième parametre l'exception.
            logger.info("failed to get client secrets");
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
        } catch (IOException e) {
        	//TODO jaa by Djer devrait etre un ERROR ? tu p devrait passer en deuxième parametre l'exception.
            logger.info("failed to getFlow");
        }
        return flow;
    }
}
