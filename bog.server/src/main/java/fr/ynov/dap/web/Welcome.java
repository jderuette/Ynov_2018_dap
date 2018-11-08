package fr.ynov.dap.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.service.GoogleService;

/**
 * @author Mon_PC
 */
@Controller
public class Welcome extends GoogleService {
    /**
     * @throws Exception si un problème est survenu lors de la création de l'instance Welcome
     * @throws IOException si un problème est survenu lors de la création de l'instance Welcome
     */
    public Welcome() throws Exception, IOException {
    }

    /**
     * @param model type Modelmap
     * @return welcome view
     * @throws Exception si un problème est survenu lors de l'appel à cette fonction
     */
    @RequestMapping("/welcome")
    public String welcome(final ModelMap model) throws Exception {
        DataStore<StoredCredential> storedCredential = super.getFlow().getCredentialDataStore();
        Map<String, StoredCredential> mapStoreCredential = new HashMap<String, StoredCredential>();

        for (String key : storedCredential.keySet()) {
            mapStoreCredential.put(key, storedCredential.get(key));
        }
        final Integer unreadMsg = 11;
        model.addAttribute("nbEmails", unreadMsg);
        model.addAttribute("mapStoreCredential", mapStoreCredential);

        return "welcome";
    }
}
