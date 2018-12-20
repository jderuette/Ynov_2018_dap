package fr.ynov.dap.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.web.ConnexionGoogle;

/**
 * @author alex
 *
 */
@Service
public class CredentialsService {
    /**
     * connexion google.
     */
    @Autowired
    private ConnexionGoogle cGoogle;
    /**
     * @return String template credentails
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    public final Map<String, StoredCredential> getCredentials() throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        DataStore<StoredCredential> credentials = cGoogle.getFlow(httpTransport).getCredentialDataStore();
        Map<String, StoredCredential> listCredentials = new HashMap<String, StoredCredential>();
        List<String> keys = new ArrayList<String>();
        keys.addAll(credentials.keySet());
        List<StoredCredential> listStoredCredentials = new ArrayList<StoredCredential>(credentials.values());
        for (Integer i = 0; i < credentials.size(); i++) {
            listCredentials.put(keys.get(i), listStoredCredentials.get(i));
        }
        
        //TODO roa by Djer |API Microsoft| Récupération des infos sur les comptes Microsoft ? 
        return listCredentials;
    }
}
