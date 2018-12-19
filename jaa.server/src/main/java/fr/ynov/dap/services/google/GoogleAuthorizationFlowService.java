package fr.ynov.dap.services.google;

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
 * Google Authorization Flow service.
 */
@Service
//TODO jaa by DJer |POO| Le nom de cette classe "ment" un peu. Elle ne gère pas le "flow" (c'est le parent qui le fait), en revanche elle manipule des credentials. "GoogleCredentialService" serait plus juste
public class GoogleAuthorizationFlowService extends GoogleService {

    /**
     * default constructor.
     */
    public GoogleAuthorizationFlowService() {
    }

    /**
     * get Google Authorization flow.
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     * @return StoreCredential dictionary
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
