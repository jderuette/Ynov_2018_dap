package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;

import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.google.GoogleAccountService;
import fr.ynov.dap.repository.MicrosoftAccountRepository;

/**
 * Controller to manage via interface users.
 * @author Robin DUDEK
 *
 */
@Controller
public class AdminController extends BaseController {

    @Override
    protected final String getClassName() {
        return AccountController.class.getName();
    }

    /**
     * Auto inject on GoogleAccountService.
     */
    @Autowired
    private GoogleAccountService accountService;

    /**
     * Instance of AppUserRepository.
     * Auto resolved by Autowire.
     */
    @Autowired
    private MicrosoftAccountRepository microsoftAccountRepository;
    /**
     * Method return a view to show every user stored on datastore.
     * @param model Model
     * @return Html page
     * @throws GeneralSecurityException Exception
     * @throws IOException Exception
     */
    @RequestMapping("/admin")
    public String GetAllCredentials(final ModelMap model) throws IOException, GeneralSecurityException {
        HashMap<String, StoredCredential> mapStoredCred = accountService.GetCredentialsAsMap();

        List<MicrosoftAccount> otAccounts = (List<MicrosoftAccount>) microsoftAccountRepository.findAll();

        model.addAttribute("credentials", mapStoredCred);
        model.addAttribute("otAccounts", otAccounts);
        return "admin_datastore";
    }
}
