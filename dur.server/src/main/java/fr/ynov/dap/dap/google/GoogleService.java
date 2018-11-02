package fr.ynov.dap.dap.google;

import fr.ynov.dap.dap.*;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.http.auth.Credentials;
import org.springframework.beans.factory.annotation.Autowired;

// TODO: Auto-generated Javadoc
/**
 * The Class GoogleService.
 */
public class GoogleService{
    
    /** The Constant JSON_FACTORY. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    /** The config. */
    @Autowired
    protected Config config;
    
    /**
     * Gets the credentials.
     *
     * @param userId the user id
     * @return the credential
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    //TODO dur by Djer Pas de majuscule au début des méthodes.
    protected Credential GetCredentials (String userId) throws IOException, GeneralSecurityException {
    	GoogleAuthorizationCodeFlow flow = getFlow();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userId);
    }
    
    /**
     * Gets the flow.
     *
     * @return the flow
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    public GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        // Load client secrets.
        InputStream in = GoogleService.class.getResourceAsStream(Config.CREDENTIALS_FILE_PATH);
        //TODO dur by Djer fichier Externe ua JAR ?
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        return new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, config.ALL_SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(Config.TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
    }
}
