package fr.ynov.dap.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import fr.ynov.dap.config.Config;

/**.
 * This class return the credentials of GoogleService according a configuration of developpers
 * @author Dom
 *
 */
@Service
public class GoogleService {
    /**.
     * JSON_FACTORY is a variable containing default instance of JacksonFactory
     */
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**.
     * SCOPES is a variable containing the permissions of services google
     */

    private static final List<String> SCOPES = Stream.of(GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_READONLY,
            CalendarScopes.CALENDAR_EVENTS_READONLY, PeopleServiceScopes.CONTACTS_READONLY)
            .collect(Collectors.toList());
    /**.
     * Return the unique instance of Config with annotation Autowired
     */
    @Autowired
    private Config config;

    //TODO phd by Djer |POO| Les getter/setter vont en génral vers la fin de la classe
    /**
     * @return the config
     */
    public Config getConfig() {
        return config;
    }

    /**
     * @param conf the config to set
     */
    public void setConfig(final Config conf) {
        this.config = conf;
    }

    /**.
     * Return the credentials of the service google according a string userId param
     * @param userId .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    protected Credential getCredentials(final String userId) throws IOException, GeneralSecurityException {
        return getFlow().loadCredential(userId);
    }

    /**.
     * Return the google authorization code flow according the config of the developper
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
        GoogleClientSecrets clientSecrets = null;
        InputStreamReader file = new InputStreamReader(new FileInputStream(config.getCredentialsFilePath()),
                Charset.forName("UTF-8"));
        if (file.ready()) {
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, file);
        } else {
            InputStream inStream = GoogleService.class.getResourceAsStream(config.getCredentialsFilePath());
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(inStream));
        }

        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(config.getTokensDirectoryPath())))
                .setAccessType("offline").build();

    }
}
