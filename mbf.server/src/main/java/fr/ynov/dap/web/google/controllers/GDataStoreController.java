package fr.ynov.dap.web.google.controllers;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import fr.ynov.dap.services.google.GDataStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class GDataStoreController {

    @Autowired
    private GDataStoreService service;

    @RequestMapping("/datastore")
    public final String datastore(ModelMap model) throws IOException {
        //TODO mbf by Djer |POO| utilise la version paramètré, ici ta clef est un String, et ta valeur est un StoredCredential
        HashMap mappedCredentials = new HashMap();
        DataStore<StoredCredential> credentials = service.getDataStoreCredential();

        // for-each loop
        for (String s : credentials.keySet()) {
            mappedCredentials.put(s, credentials.get(s));
        }

        model.addAttribute("credentials", mappedCredentials);
        return "datastore";
    }
}
