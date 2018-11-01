package fr.ynov.dap.helpers;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.*;
import com.google.api.services.people.v1.*;
import fr.ynov.dap.*;
import org.apache.logging.log4j.LogManager;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.*;

public class GoogleHelper {

    //FIXME grj by Djer "New", du coups plus d'injection de possible, et plsu de configuration de possible non plus.
    // C'est domage d'avoir une classe "Configuration" qui ne se configure pas .....
    private Configuration    configuration = new Configuration();
    private JsonFactory      JSON_FACTORY;
    private List<String>     SCOPES;
    private NetHttpTransport HTTP_TRANSPORT;

    //TODO grj by Djer Le LogManager.getLogger() de Log4J peut être utilisé SANS paramètre (il prendra par defaut le nom, qualifié, de la classe)
    private org.apache.logging.log4j.Logger LOG = LogManager.getLogger("GoogleHelper");

    public GoogleHelper() {
        JSON_FACTORY = JacksonFactory.getDefaultInstance();
        SCOPES = Arrays.asList(GmailScopes.GMAIL_LABELS, CalendarScopes.CALENDAR_READONLY, PeopleServiceScopes.CONTACTS_READONLY);
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            //TODO grj by Djer Message + contexte
            LOG.error(e);
        }
    }

    /**
     * @return Google Authorization Code Flow
     * @throws IOException Deal with exception
     */
    protected final GoogleAuthorizationCodeFlow getFlow() throws IOException {

        InputStream         in            = Launcher.class.getResourceAsStream(configuration.getCredentialFolder());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        return new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(configuration.getClientSecretFile())))
                .setAccessType("offline")
                .build();
    }


    /**
     * Creates an authorized Credential object.
     *
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(String userKey) throws IOException {

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = getFlow();
        //TODO grj by Djer En mode "Web" il ne faut plus utiliser le "AuthorizationCodeInstalledApp", renvoie simplement flow.loadCredentials(userKey)
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userKey);
    }

    /**
     * Retrieve GMAIL service
     *
     * @param userKey userKey to log
     * @return GMAIL
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    public final Gmail getGmailService(String userKey) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(configuration.getApplicationName())
                .build();
    }

    /**
     * Retrieve People Service
     *
     * @param userKey userKey to log
     * @return People Service by Google
     * @throws IOException              Exception
     * @throws GeneralSecurityException Exception
     */
    public final PeopleService getPeopleService(String userKey) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(configuration.getApplicationName())
                .build();
    }

    /**
     * Retrieve CALENDAR service
     *
     * @param userKey userKey to log
     * @return CALENDAR
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    public final com.google.api.services.calendar.Calendar getCalendarService(String userKey) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new com.google.api.services.calendar.Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(configuration.getApplicationName())
                .build();
    }


}
