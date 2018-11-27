package fr.ynov.dap.services.google;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import org.springframework.stereotype.Service;

@Service
public class GDataStoreService extends GoogleService {


    public DataStore<StoredCredential> getDataStoreCredential() {
        DataStore<StoredCredential> storedCredentials = getFlow().getCredentialDataStore();
        return storedCredentials;
    }
}
