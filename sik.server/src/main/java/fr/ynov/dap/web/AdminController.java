package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.exception.NoConfigurationException;
import fr.ynov.dap.google.GoogleAccountService;
import fr.ynov.dap.model.Credential;

/**
 * Controller to manage via interface users.
 * @author Kévin Sibué
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    /**
     * Auto inject on GoogleAccountService.
     */
    @Autowired
    private GoogleAccountService accountService;

    /**
     * Method return a view to show every user stored on datastore.
     * @param model Model
     * @return Html page
     * @throws GeneralSecurityException Exception
     * @throws IOException Exception
     * @throws NoConfigurationException Exception
     */
    @RequestMapping("/users")
    public String datastores(final ModelMap model)
            throws NoConfigurationException, IOException, GeneralSecurityException {

        DataStore<StoredCredential> flow = accountService.getFlows().getCredentialDataStore();
        ArrayList<Credential> credentials = new ArrayList<Credential>();

        flow.keySet().forEach(key -> {

            StoredCredential assocStoredCredential;

            try {

                assocStoredCredential = flow.get(key);

                Credential newCredential = new Credential();
                newCredential.setUserId(key);
                newCredential.setToken(assocStoredCredential.getAccessToken());
                newCredential.setRefreshToken(assocStoredCredential.getRefreshToken());
                newCredential.setExpirationTime(assocStoredCredential.getExpirationTimeMilliseconds());

                credentials.add(newCredential);

            } catch (IOException e) {

                e.printStackTrace();

            }

        });

        model.addAttribute("credentials", credentials);

        return "admin_datastore";

    }

}
