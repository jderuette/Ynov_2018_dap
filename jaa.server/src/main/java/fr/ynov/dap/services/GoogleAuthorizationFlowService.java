package fr.ynov.dap.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.DataStore;

/**
 * @author adrij
 *
 */
@Service
public class GoogleAuthorizationFlowService extends GoogleService {

    /**
     * 
     */
    public GoogleAuthorizationFlowService() {
    }
    
    /**
     * get Google Authorization flow
     * @throws IOException 
     * @throws GeneralSecurityException 
     */
    public Map<String, StoredCredential> getStoreCredentialMap() throws GeneralSecurityException, IOException {
        GoogleAuthorizationCodeFlow flow = super.getFlow();
        DataStore<StoredCredential> credentials = flow.getCredentialDataStore();
        Map<String, StoredCredential> map = new HashMap<String, StoredCredential>();
        Set<String> keys = credentials.keySet();
        for (String key : keys) {
            map.put(key, credentials.get(key));
        }
        return map;
    }

}
