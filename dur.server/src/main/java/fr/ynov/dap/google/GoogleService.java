package fr.ynov.dap.google;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;

import fr.ynov.dap.Config;

// TODO: Auto-generated Javadoc
/**
 * The Class GoogleService.
 */
public abstract class GoogleService {

    /** The Constant JSON_FACTORY. */
    protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    /** The config. */
    @Autowired
    protected Config config;
    
    /**
     * get the current class name.
     *
     * @return ClassName
     */
    protected abstract String getClassName();

    /**
     * Log4j instance for all children google services.
     */
    protected static final Logger LOGGER = LogManager.getLogger();

    /**
     * Gets the credentials.
     *
     * @param userId the user id
     * @return the credential
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
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
        InputStream in = GoogleService.class.getResourceAsStream(config.getCredentialFolder());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        // Build flow and trigger user authorization request.
        return new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, config.getScopes())
                        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(config.getClientSecretFile())))
                .setAccessType("offline")
                .build();
    }

    public DataStore<StoredCredential> getCredentialDataStore() throws GeneralSecurityException, IOException {
        GoogleAuthorizationCodeFlow flow = getFlow();
        return flow.getCredentialDataStore();
    }

    public HashMap<String, StoredCredential> GetCredentialsAsMap() throws IOException, GeneralSecurityException {
        GoogleAuthorizationCodeFlow flow = this.getFlow();
        DataStore<StoredCredential> dataStore = flow.getCredentialDataStore();
        Set<String> keys = dataStore.keySet();
        HashMap<String, StoredCredential> mapDataStore = new HashMap<String, StoredCredential>();
        for (String key : keys) {
            mapDataStore.put(key, dataStore.get(key));
            System.out.println(key);
        }
        return mapDataStore;
    }
}
