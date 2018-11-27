package fr.ynov.dap.services.google;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import fr.ynov.dap.helpers.GoogleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * DataStoreService
 */
@Service
public class GoogleDataStoreService {

    /**
     * Autowired GoogleHelper
     */
    @Autowired
    GoogleHelper googleHelper;

    /**
     * Get data store credential
     *
     * @return DataStore of StoredCredential
     * @throws IOException Exception
     */
    public DataStore<StoredCredential> getDataStore() throws IOException {
        return googleHelper.getFlow().getCredentialDataStore();
    }

}
