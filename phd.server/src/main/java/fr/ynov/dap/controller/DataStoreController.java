package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.service.GoogleService;

/**
 * .
 * @author Dom
 *
 */
@Controller
public class DataStoreController extends GoogleService {
    /**.
     * @param model .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    @RequestMapping("/google/datastore")
    public String dataStore(final ModelMap model) throws IOException, GeneralSecurityException {
        final GoogleAuthorizationCodeFlow flow = super.getFlow();
        DataStore<StoredCredential> datastore = flow.getCredentialDataStore();
        Map<String, StoredCredential> map = new HashMap<String, StoredCredential>();
        //TODO phd by Djer |POO| "stringValue" dit ce que c'est mais pas ce que sa represente, ici il s'agit du "userId" (du point de vue Google) et de l'accountName (du point de vue dap)
        for (String stringValue : datastore.keySet()) {
            //TODO phd by Djer |Audit Code| PMD te dit que ca n'est pas utile d'appelr "toString" sur un String ....
            map.put(stringValue.toString(), datastore.get(stringValue));
        }

        //TODO phd by Djer |POO| "map" n'est pas très paralant, credentialList ou "credentials" ou "googleCredential" serait plus parlant
        model.addAttribute("map", map);
        return "dataStore";
    }
}
