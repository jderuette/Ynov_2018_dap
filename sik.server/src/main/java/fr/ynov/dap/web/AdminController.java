package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.contract.TokenRepository;
import fr.ynov.dap.exception.NoConfigurationException;
import fr.ynov.dap.google.GoogleAccountService;
import fr.ynov.dap.microsoft.service.MicrosoftAccountService;
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
    private GoogleAccountService googleAccountService;

    /**
     * Auto inject on MicrosoftAccountService.
     */
    @Autowired
    private MicrosoftAccountService msAccountService;

    /**
     * Instance of TokenRepository service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private TokenRepository tokenRepository;

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

        ArrayList<Credential> googleCredentials = googleAccountService.getStoredCredentials();

        ArrayList<Credential> msCredentials = msAccountService.getStoredCredentials(tokenRepository);

        ArrayList<Credential> credentials = new ArrayList<>();
        credentials.addAll(googleCredentials);
        credentials.addAll(msCredentials);

        model.addAttribute("credentials", credentials);

        return "admin_datastore";

    }

}
