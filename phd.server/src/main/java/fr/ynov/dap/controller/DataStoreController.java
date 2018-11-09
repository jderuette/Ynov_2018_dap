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
    @RequestMapping("/datastore")
    public String dataStore(final ModelMap model) throws IOException, GeneralSecurityException {
        final GoogleAuthorizationCodeFlow flow = super.getFlow();
        DataStore<StoredCredential> datastore = flow.getCredentialDataStore();
        Map<String, StoredCredential> map = new HashMap<String, StoredCredential>();
        for (String stringValue : datastore.keySet()) {
            map.put(stringValue.toString(), datastore.get(stringValue));
        }

        model.addAttribute("map", map);
        return "dataStore";
    }
}
