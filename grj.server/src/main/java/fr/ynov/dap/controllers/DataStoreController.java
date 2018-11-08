package fr.ynov.dap.controllers;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import fr.ynov.dap.services.DataStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.*;

/**
 * DataStoreController
 */
@Controller
public class DataStoreController {

    @Autowired
    private DataStoreService dataStoreService;

    @RequestMapping("/data-store")
    public String dataStore(ModelMap model) throws IOException {

        DataStore<StoredCredential>   dataStore    = dataStoreService.getDataStore();
        Map<String, StoredCredential> dataStoreMap = new HashMap<>();

        for (String s : dataStore.keySet()) {
            dataStoreMap.put(s, dataStore.get(s));
        }

        model.addAttribute("dataMap", dataStoreMap);

        return "datastore";
    }

}
