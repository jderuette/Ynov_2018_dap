package fr.ynov.dap.dap.services.google;

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
import fr.ynov.dap.dap.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

public class GoogleService{
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    @Autowired
    Config config;
    
    List<String> allScopes;

   /**
    * 
    * @return Client secrets from our credentials file
    * @throws IOException
    */
    protected GoogleClientSecrets LoadClientSecret() throws IOException {
        // Load client secrets.
    	System.out.println(config.getCredentialPath());
        InputStreamReader in = new InputStreamReader(new FileInputStream(config.getCredentialPath()),
                Charset.forName("UTF-8"));
        		//GoogleService.class.getResourceAsStream(config.getCredentialPath());
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, in);
        
		return clientSecrets;
    }
    
    /**
     * 
     * @param userId
     * @return Return a credentials that we need to authorize our app for all services
     * @throws IOException
     * @throws GeneralSecurityException
     */
    protected Credential GetCredentials (String userId) throws IOException, GeneralSecurityException {

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = getFlow();
        return flow.loadCredential(userId);
    }
    public GoogleAuthorizationCodeFlow getFlow() throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        GoogleClientSecrets clientSecrets = LoadClientSecret();

    	return new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, config.getAllScopes())
                .setDataStoreFactory(new FileDataStoreFactory(new File(config.getTokenPath())))
                .setAccessType("offline")
                .build();
    }
    
	public HashMap<String, StoredCredential> GetCredentialsAsMap() throws IOException, GeneralSecurityException {
		GoogleAuthorizationCodeFlow flow = this.getFlow();
		DataStore<StoredCredential> dataStore = flow.getCredentialDataStore();
		Set<String> keys = dataStore.keySet();
		HashMap<String, StoredCredential> mapDataStore = new HashMap<String, StoredCredential>();
		for(String key : keys) {
			mapDataStore.put(key, dataStore.get(key));
			System.out.println(key);
		}
		return mapDataStore;
	}
    
}