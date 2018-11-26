package fr.ynov.dap.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.Config;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.google.GoogleAccountRepository;
import fr.ynov.dap.data.google.JPADataStoreFactory;

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

/**
 * Manage Google Service.
 * @author thibault
 *
 */
public abstract class GoogleService {
    /**
     * Global Config for Google Service.
     */
    @Autowired
    private Config config;

    /**
     * Repository of GoogleAccount.
     */
    @Autowired
    private GoogleAccountRepository repository;

    /**
     * Json Factory for Google Service.
     */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    /**
     * Logger for the class.
     */
    private Logger logger = LogManager.getLogger();

    /**
     * List of scopes google.
     */
    private static List<String> scopes = Arrays.asList(
            GmailScopes.GMAIL_LABELS,
            GmailScopes.GMAIL_READONLY,
            CalendarScopes.CALENDAR_READONLY,
            PeopleServiceScopes.CONTACTS_READONLY,
            PeopleServiceScopes.PLUS_LOGIN
        );

    /**
     * Creates an authorized Credential object.
     * @param accountName : user id associate token
     * @param owner : owner user of credential
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     * @throws GeneralSecurityException Security on Google API
     */
    protected Credential getCredentials(final String accountName, final AppUser owner)
            throws IOException, GeneralSecurityException {
        this.logger.info("Generate credentials Google API for account '" + accountName + "'");
        GoogleAuthorizationCodeFlow flow = this.getFlow(owner);
        return flow.loadCredential(accountName);
    }

    /**
     * Creates an Google authorization flow.
     * @param owner : owner user of flow
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     * @throws GeneralSecurityException Security on Google API
     */
    protected GoogleAuthorizationCodeFlow getFlow(final AppUser owner)
            throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        this.logger.info("Generate new flow with scopes for Google API.");

        // Load client secrets.
        FileInputStream in = new FileInputStream(this.config.getCredentialsFilePath());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets
                .load(JSON_FACTORY, new InputStreamReader(in, Charset.forName("UTF-8")));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY,
                clientSecrets, getScopes())
                        .setDataStoreFactory(
                                new JPADataStoreFactory(repository, owner))
                        .setAccessType("offline").setApprovalPrompt("auto").build();

        return flow;
    }

    /**
     * @return the config
     */
    protected Config getConfig() {
        return config;
    }

    /**
     * @return the jsonFactory
     */
    protected JsonFactory getJsonFactory() {
        return JSON_FACTORY;
    }

    /**
     * Generate array list who contains all scopes for this application.
     * @return array of scopes
     */
    private List<String> getScopes() {

        return scopes;
    }
}
