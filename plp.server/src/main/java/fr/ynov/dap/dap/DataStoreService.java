package fr.ynov.dap.dap;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class DataStoreService {
    private GoogleService googleService = new GoogleService();

    public DataStore<StoredCredential> getDataStore() throws IOException, GeneralSecurityException {
        return googleService.getFlow().getCredentialDataStore();
    }
}
