package fr.ynov.dap.google;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.HashSet;

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
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.Config;

/**
 * Abstract class for services classes.
 * @author MBILLEMAZ
 *
 */

@org.springframework.stereotype.Service
public abstract class Service {

    /**
     * json factory.
     */
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * rights asked to API.
     */
    protected static final HashSet<String> SCOPES = new HashSet<String>();

    /**
     * http transport.
     */
    private static NetHttpTransport httpTransport = null;

    /**
     * config file.
     */
    @Autowired
    private Config config;

    /**
     * Generate http transport attribute.
     */
    public Service() {
        try {
            Service.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            Service.SCOPES.add(CalendarScopes.CALENDAR_READONLY);
            Service.SCOPES.add(GmailScopes.GMAIL_LABELS);
            //TODO bim by Djer "ALL" c'est un peu large non pour juste compter les contacts ?
            Service.SCOPES.addAll(PeopleServiceScopes.all());
        } catch (GeneralSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Returns credentials.
     * @param userKey Applicative user
     * @return credential file
     * @throws Exception if user not found
     */
    protected final Credential getCredentials(final String userKey) throws Exception {
        Logger logger = LogManager.getLogger();
        logger.info("Récupération des droits des API google...");
        GoogleAuthorizationCodeFlow flow = getFlow();
        if (flow.loadCredential(userKey) == null) {
            throw new Exception("Utilisateur inexistant");
        }
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userKey);
    }

    /**
     * @return the httpTransport
     */
    public static NetHttpTransport getHttpTransport() {
        return httpTransport;
    }

    /**
     * Get google flow.
     * @return google glow
     */
    public final GoogleAuthorizationCodeFlow getFlow() {

        try {
            File file = new File(this.config.getCredentialsFolder() + "credentials.json");
            InputStreamReader in = new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8"));
            GoogleClientSecrets clientSecrets;
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, in);
            return new GoogleAuthorizationCodeFlow.Builder(Service.httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new File(this.config.getClientSecretDir())))
                    .setAccessType("offline").build();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Logger logger = LogManager.getLogger();
            logger.error("impossible de récupérer le fichier credentials.json dans le dossier "
                    + this.config.getCredentialsFolder());
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @return the config
     */
    public final Config getConfig() {
        return config;
    }

}
