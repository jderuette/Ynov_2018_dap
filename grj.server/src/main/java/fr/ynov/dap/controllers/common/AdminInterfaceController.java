package fr.ynov.dap.controllers.common;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import fr.ynov.dap.models.*;
import fr.ynov.dap.repositories.MicrosoftAccountRepository;
import fr.ynov.dap.services.google.GoogleDataStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.*;

/**
 * AdminInterfaceController
 */
@Controller
public class AdminInterfaceController {

    /**
     * Autowired DataStoreService
     */
    @Autowired
    private GoogleDataStoreService googleDataStoreService;

    @Autowired
    private MicrosoftAccountRepository microsoftAccountRepository;

    /**
     * Return the data stored in StoredCredential
     *
     * @param model Model
     * @return Data store view
     * @throws IOException Exception
     */
    @RequestMapping("/admin-interface")
    public String dataStore(ModelMap model) throws IOException {

        Map<String, AccountCredential> credentials = new HashMap();

        DataStore<StoredCredential> dataStore = googleDataStoreService.getDataStore();
        for (String s : dataStore.keySet()) {
            AccountCredential accountCredential = new AccountCredential();
            accountCredential.setType("Google");
            accountCredential.setTenantId(null);
            accountCredential.setToken(dataStore.get(s).getAccessToken());
            credentials.put(s, accountCredential);
        }

        for (MicrosoftAccount currentMicrosoftAccount : microsoftAccountRepository.findAll()) {
            AccountCredential accountCredential = new AccountCredential();
            accountCredential.setType("Microsoft");
            accountCredential.setTenantId(currentMicrosoftAccount.getTenantId());
            accountCredential.setToken(currentMicrosoftAccount.getToken());
            credentials.put(currentMicrosoftAccount.getName(), accountCredential);
        }

        model.addAttribute("dataMap", credentials);

        return "credentials";
    }

}
