package fr.ynov.dap.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import fr.ynov.dap.Launcher;
import fr.ynov.dap.config.Config;
/**
 * This class return the credentials of GoogleService according a configuration of developpers
 * @author Dom
 *
 */
@Service
public class GoogleService {
/**
 * JSON_FACTORY is a variable containing default instance of JacksonFactory
 */
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    /**
     * SCOPES is a variable containing the permissions of services google
     */

    private static final List<String> SCOPES = Stream.of(GmailScopes.GMAIL_LABELS,GmailScopes.GMAIL_READONLY,CalendarScopes.CALENDAR_EVENTS_READONLY,PeopleServiceScopes.CONTACTS_READONLY).collect(Collectors.toList());
/**
 * Return the unique instance of Config with annotation Autowired
 */
    @Autowired
    protected Config config;
    
    /**
     * Return the credentials of the service google according a string userId param
     * @param userId
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    protected Credential getCredentials(final String userId) throws IOException, GeneralSecurityException {
        return new AuthorizationCodeInstalledApp(getFlow(), new LocalServerReceiver()).authorize(userId);
    }
    
    /**
     * Return the google authorization code flow according the config of the developper
     * @return
     * @throws IOException
     * @throws GeneralSecurityException
     */
    protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
    	InputStream in = Launcher.class.getResourceAsStream(config.getCredentialsFilePath());
    	GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		return new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(config.getTokensDirectoryPath())))
                .setAccessType("offline")
                .build();
    	
    }
}
