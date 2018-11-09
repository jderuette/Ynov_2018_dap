package fr.ynov.dap.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
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

import fr.ynov.dap.configuration.Config;

/**
 * @author Gaël BOSSER
 * Abstract class GoogleService
 */
public abstract class GoogleService {
    /**.
     * HTTP_TRANSPORT constante
     */
    private static NetHttpTransport httpTransports;
    /**.
     * JSON_FACTORY constante
     */
    private static JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    /**.
     * SCOPES List<String>
     */
    private static List<String> scopes = new ArrayList<String>();
    /**.
     * Log variable
     */
    protected static final Logger LOG = LogManager.getLogger();

    /**.
     * configuration is managed by Spring
     */
    @Autowired
    private Config configuration;

    /**.
     * Constructor class MainService
     * @throws Exception un problème est survenu lors de la création de l'instance GoogleService par une classe fille
     * @throws IOException un problème est survenu lors de la création de l'instance GoogleService par une classe fille
     */
    public GoogleService() throws Exception, IOException {
        setHttpTransports(GoogleNetHttpTransport.newTrustedTransport());
        init();
    }

    /**
     * @return current configuration
     */
    public Config getConfig() {
        return configuration;
    }

    /**
     * @return HttpTransport
     */
    public NetHttpTransport getHttpTransport() {
        return httpTransports;
    }

    /**
     * @param config
     * Param config to set new configuration
     */
    public void setConfig(final Config config) {
        configuration = config;
    }

    /**
     * @return List of scopes
     */
    protected List<String> getScopes() {
        return scopes;
    }

    /**
     * @return JsonFactory
     */
    protected JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    /**
     * @param newJsonFactory
     * Default : JacksonFactory.getDefaultInstance()
     */
    protected void setJsonFactory(final JsonFactory newJsonFactory) {
        jsonFactory = newJsonFactory;
    }

    /**.
     * Initialize SCOPES
     * Call in the constructor of the current class
     * Gmail, calendar, contact
     */
    private void init() {
        scopes.add(GmailScopes.GMAIL_LABELS);
        scopes.add(CalendarScopes.CALENDAR_READONLY);
        scopes.add(PeopleServiceScopes.CONTACTS_READONLY);
    }

    /**
     * @param accountName correspondant à l'utilisateur dont il faut récupérer les credentials
     * @return Credential
     * @throws IOException si un problème est survenu lors de l'appel à cette fonction
     * @throws GeneralSecurityException si un problème est survenu lors de l'appel à cette fonction
     */
    protected Credential getCredentials(final String accountName) throws IOException, GeneralSecurityException {
        GoogleAuthorizationCodeFlow flow = getFlow();
        return flow.loadCredential(accountName);
    }

    /**
     * @return GoogleAuthorizationCodeFlow
     * @throws IOException si un problème est survenu lors de l'appel à cette fonction
     * @throws GeneralSecurityException si un problème est survenu lors de l'appel à cette fonction
     */
    protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
        GoogleClientSecrets clientSecrets = null;
        InputStreamReader file = new InputStreamReader(new FileInputStream(configuration.getCredentialsFilePath()),
                Charset.forName("UTF-8"));
        if (file.ready()) {
            clientSecrets = GoogleClientSecrets.load(jsonFactory, file);
            LOG.info("FILE_PATH : " + configuration.getCredentialsFilePath());
        } else {
            InputStream inStream = GoogleService.class.getResourceAsStream(configuration.getCredentialsFilePath());
            clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(inStream));
        }

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(httpTransports, jsonFactory,
                clientSecrets, scopes)
                        .setDataStoreFactory(
                                new FileDataStoreFactory(new java.io.File(configuration.getTokensDirectoryPath())))
                        .setAccessType("offline").build();
        return flow;
    }

    /**
     * @return current NetHttpTransport
     */
    public static NetHttpTransport getHttpTransports() {
        return httpTransports;
    }

    /**
     * @param newHttpTransports new Http transports
     */
    public static void setHttpTransports(final NetHttpTransport newHttpTransports) {
        httpTransports = newHttpTransports;
    }
}
