package fr.ynov.dap.generalaccount.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.microsoft.data.MicrosoftAccountData;
import fr.ynov.dap.microsoft.data.MicrosoftRepository;
import fr.ynov.dap.service.GoogleService;

/**
 *
 * @author Dom
 *
 */
@Service
public class GeneralAdminService extends GoogleService {

    /**
     *
     */
    @Autowired
    private MicrosoftRepository microsoftRepository;

    /**
     * @return .
     * @throws GeneralSecurityException .
     * @throws IOException .
     */
    public Map<String, StoredCredential> getCredentialsAdminDetails() throws IOException, GeneralSecurityException {
        final GoogleAuthorizationCodeFlow flow = super.getFlow();
        DataStore<StoredCredential> datastore = flow.getCredentialDataStore();
        Map<String, StoredCredential> map = new HashMap<String, StoredCredential>();
        for (String stringValue : datastore.keySet()) {
            map.put(stringValue.toString(), datastore.get(stringValue));
        }
        return map;
    }

    /**
     *
     * @return .
     */
    //TODO phd by Djer |POO| Devrait être privé
    public Iterable<MicrosoftAccountData> getDataStoreMicrosoft() {

        return microsoftRepository.findAll();
    }

}
