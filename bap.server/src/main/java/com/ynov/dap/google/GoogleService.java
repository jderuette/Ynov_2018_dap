package com.ynov.dap.google;

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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * The Class GoogleService.
 */
@PropertySource("classpath:config.properties")
public abstract class GoogleService {

    protected abstract String getClassName();

    private Logger logger = LogManager.getLogger(getClassName());

   /*
   private Config config;

   public Config getConfig() {
	   return config;
   }
   */
    protected Logger getLogger() {
        return logger;
    }

    /** The env. */
    @Autowired
    protected Environment env;

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
        return flow.loadCredential(userId);
    }

    /**
     * Gets the flow.
     *
     * @return the flow
     * @throws GeneralSecurityException the general security exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public GoogleAuthorizationCodeFlow getFlow() throws GeneralSecurityException, IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
        		new InputStreamReader(new FileInputStream(
        				env.getProperty("credentials_folder")
        				+ "/" + env.getProperty("credentials_file")), Charset.forName("UTF-8")));

        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        return new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, JSON_FACTORY, clientSecrets, ALL_SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(env.getProperty("credentials_tokens"))))
                .setAccessType("offline")
                .build();
    }

}
