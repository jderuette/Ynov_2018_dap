package fr.ynov.dap.dap.service;

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

import fr.ynov.dap.dap.config.Config;

/**
 * @author Florian BRANCHEREAU
 *
 */
public abstract class GoogleService {

    /**.
     * LOG
     */
    protected static final Logger LOG = LogManager.getLogger();

    /**.
     * httpTransport
     */
    private NetHttpTransport httpTransport;
    /**.
     * jsonFactory
     */
    private JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    /**.
     * scope
     */
    private List<String> scope = new ArrayList<String>();

    /**.
     * declaration de configuration
     */
    @Autowired
    private Config configuration;

    /**.
     * Constructor class MainService
     * @throws Exception fonction
     * @throws IOException fonction
     */
    public GoogleService() throws Exception, IOException {
        httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        init();
    }

    /**
     * @return HTTP_TRANSPORT
     */
    public NetHttpTransport getHttpTransport() {
        return httpTransport;
    }

    /**
     * @param config recuperation de la vaeleur de config
     */
    public void setConfig(final Config config) {
        configuration = config;
    }

    /**
     * @return configuration
     */
    protected Config getConfiguration() {
        return configuration;
    }

    /**
     * @return scope
     */
    protected List<String> getScopes() {
        return scope;
    }

    /**
     * @return JSON_FACTORY
     */
    protected JsonFactory getJsonFactory() {
        return jsonFactory;
    }

    /**
     * @param theJsonFactory recuperation de la valeur de jsonFactory
     */
    protected void setJsonFactory(final JsonFactory theJsonFactory) {
        jsonFactory = theJsonFactory;
    }

    /**.
     * Initialisation de SCOPES
     * Appel le constructeur et la classe
     */
    private void init() {
        scope.add(GmailScopes.GMAIL_LABELS);
        scope.add(CalendarScopes.CALENDAR_READONLY);
        scope.add(PeopleServiceScopes.CONTACTS_READONLY);
    }

    /**
     * @param userKey nom du compte
     * @param HTTP_TRANSPORT
     * @return AuthorizationCodeInstalledApp
     * @throws IOException fonction
     * @throws GeneralSecurityException fonction
     */
    //TODO brf by Djer |POO| "userKey" est en faite un "accountName"
    protected Credential getCredentials(final String userKey) throws IOException, GeneralSecurityException {
        GoogleAuthorizationCodeFlow flow = getFlow();
        //TODO brf by Djer |API Google| Ceci risque de mal fonctionner en mode "serveur Web". Utilise flow.loadCredential(userKey)
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(userKey);
    }

    /**
     * @return flow
     * @throws IOException fonction
     * @throws GeneralSecurityException fonction
     */
    protected GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {

        LOG.debug("Chargement du fichier credential : " + configuration.getCredentialsFilePath());
        InputStreamReader file = new InputStreamReader(new FileInputStream(configuration.getCredentialsFilePath()),
                Charset.forName("UTF-8"));

        GoogleClientSecrets clientSecrets = null;

        if (file.ready()) {
            clientSecrets = GoogleClientSecrets.load(jsonFactory, file);
        } else {
            InputStream fileInterne = GoogleService.class.getResourceAsStream(configuration.getCredentialsFilePath());
            clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(fileInterne));
        }

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, scope)
                        .setDataStoreFactory(
                                new FileDataStoreFactory(new java.io.File(configuration.getTokensDirectoryPath())))
                        .setAccessType("offline")
                        .build();
        return flow;
    }

}
