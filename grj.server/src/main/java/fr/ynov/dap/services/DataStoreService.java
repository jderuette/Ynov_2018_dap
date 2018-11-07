package fr.ynov.dap.services;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import fr.ynov.dap.helpers.GoogleHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DataStoreService {

    private GoogleHelper googleHelper = new GoogleHelper();

    public DataStore<StoredCredential> getDataStore() throws IOException {
        return googleHelper.getFlow().getCredentialDataStore();
    }

}
