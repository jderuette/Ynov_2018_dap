package fr.ynov.dap.controllers.common;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;
import fr.ynov.dap.models.common.AccountCredential;
import fr.ynov.dap.models.microsoft.MicrosoftAccount;
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
//TODO grj by Djer |POO| Evite d'employer le mot "Interface" qui à déja un sens en Java. Tu peux utiliser GUI (ou IHM) si vraiment tu as besoin de le préciser dans ta classe.
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

        //TODO grj by Djer |POO| Depuis Java 1.7 tu peux ne pas re-préciser le type des paramètre lors de l'instanciation, mais tu DOIS laisser les <> (le compilateur completera automatiquement à partir du type déclaré)
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

        //TODO grj by Djer |POO| N'hésite pas être claire dans le nom des variable que tu transfert a Thymleaf via le modele (accountsMap, ou accountsData serait plus claire)
        model.addAttribute("dataMap", credentials);

        return "credentials";
    }

}
