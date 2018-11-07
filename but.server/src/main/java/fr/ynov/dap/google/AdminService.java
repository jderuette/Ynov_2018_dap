package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

/**
 * Class to manage Google Service.
 * @author thibault
 *
 */
@Service
public class AdminService extends GoogleService {

    /**
     * Get list of credentials.
     * @return Map of list userkey and associate storedCredential.
     * @throws IOException If the credentials.json file cannot be found.
     * @throws GeneralSecurityException Security on Google API
     */
    public Map<String, StoredCredential> getCredentialDataStore() throws IOException, GeneralSecurityException {
        DataStore<StoredCredential> dataStoreGoogle = this.getFlow().getCredentialDataStore();
        Map<String, StoredCredential> dataStore = new HashMap<String, StoredCredential>();
        for (String userKey : dataStoreGoogle.keySet()) {
            dataStore.put(userKey, dataStoreGoogle.get(userKey));
        }

        return dataStore;
    }
}
