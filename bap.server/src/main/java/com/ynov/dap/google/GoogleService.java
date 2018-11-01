package com.ynov.dap.google;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


/**
 * The Class GoogleService.
 */
@Service
//TODO bap by Djer Bien vue !! :-) Il aurait été parfait que tu m'explique dans la doc "technique" comment l'utiliser !
// Plus d'info sur spring Properties ici : https://www.baeldung.com/properties-with-spring (tu as du "No conf" pour le moment)
@PropertySource("classpath:config.properties")
public class GoogleService {

    /** The env. */
    @Autowired
    private Environment env;

    /** The Constant JSON_FACTORY. */
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /** The Constant ALL_SCOPES. */
    private static final List<String> ALL_SCOPES = new ArrayList<String>();

    /**
     * Instantiates a new google service.
     */
    public GoogleService() {
        ALL_SCOPES.add(GmailScopes.GMAIL_LABELS);
        ALL_SCOPES.add(CalendarScopes.CALENDAR_READONLY);
        ALL_SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
    }

    /**
     * Gets the credentials.
     *
     * @param userId the user id
     * @return the credentials
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    protected Credential getCredentials(final String userId) throws IOException, GeneralSecurityException {
        GoogleAuthorizationCodeFlow flow = getFlow();
        //TODO bap by Djer en mode "web" le AuthorizationCodeInstalledApp fonctionen mal. Utilise directement flow.loadCredential(userId)
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userId);
    }

    /**
     * Gets the flow.
     *
     * @return the flow
     * @throws GeneralSecurityException the general security exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public GoogleAuthorizationCodeFlow getFlow() throws GeneralSecurityException, IOException {
        InputStream in = GoogleService.class.getResourceAsStream(
              env.getProperty("credentials_folder") + "/" + env.getProperty("credentials_file"));

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        return new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, ALL_SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(env.getProperty("credentials_folder"))))
                .setAccessType("offline")
                .build();
    }
}
