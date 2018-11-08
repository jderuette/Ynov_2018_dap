package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.services.CredentialsService;

/**
 * @author alex
 */
@Controller
public class Credentials {
    /**
     * service de gestion des credentials.
     */
    @Autowired
    private CredentialsService credentials;
    /**
     * @param model modèle
     * @return String template credentails
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @RequestMapping("/credentails")
    public final String getCredentials(final ModelMap model)
      throws IOException, GeneralSecurityException {
        model.addAttribute("credentials", credentials.getCredentials());
        return "Credentials";
    }
}
