package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.data.google.GoogleAccountRepository;
import fr.ynov.dap.data.microsoft.MicrosoftAccountRepository;

/**
 * Controller to manage google service via Admin page.
 * @author thibault
 *
 */
@Controller
public class AdminController extends HandlerErrorController {

    /**
     * Repository of GoogleAccount.
     */
    @Autowired
    private GoogleAccountRepository repositoryGoogleAccount;

    /**
     * Repository of MicrosoftAccount.
     */
    @Autowired
    private MicrosoftAccountRepository repositoryMicrosoftAccount;

    /**
     * Route admin page.
     * @param model Model data for View
     * @return template name
     * @throws GeneralSecurityException if error with credentials.json (google)
     * @throws IOException if error http (with api google or microsoft)
     */
    @RequestMapping("/admin")
    public String welcome(final ModelMap model) throws IOException, GeneralSecurityException {
        model.addAttribute("credentialsGoogle", repositoryGoogleAccount.findAll());
        model.addAttribute("credentialsMicrosoft", repositoryMicrosoftAccount.findAll());
        model.addAttribute("fragment", "admin");
        return "base";
    }
}
