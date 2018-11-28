package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.dap.service.GoogleService;

/**
 * @author Florian
 */
@Controller
public class Welcome extends GoogleService {

    /**.
     * LOG
     */
    protected static final Logger LOG = LogManager.getLogger();

    /**
     * @throws Exception constructeur
     * @throws IOException constructeur
     */
    public Welcome() throws Exception, IOException {

    }

    /**
     * @param model renvois les valeurs a la JSP
     * @return le nom de la JSP
     * @throws IOException fonction
     * @throws GeneralSecurityException fonction
     */
    @RequestMapping("/admin")
    public String welcome(final ModelMap model) throws IOException, GeneralSecurityException {

        DataStore<StoredCredential> storedCredential = super.getFlow().getCredentialDataStore();
        LOG.debug("Welcome variable storedCredential : " + storedCredential);
        Map<String, StoredCredential> map = new HashMap<String, StoredCredential>();

        for (String stKey : storedCredential.keySet()) {
            map.put(stKey, storedCredential.get(stKey));
        }

        final Integer nbemailunreal = 12;
        model.addAttribute("nbEmails", nbemailunreal);

        model.addAttribute("map", map);

        return "welcome";
    }
}
