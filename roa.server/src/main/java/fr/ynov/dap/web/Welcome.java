package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.services.GoogleGmail;

/**
 * @author alex
 */
@Controller
public class Welcome {
    /**
     * service Gmail.
     */
    @Autowired
    private GoogleGmail mail;
    /**
     * @param model modele
     * @param user utilisateur
     * @return String welcome
     * @throws GeneralSecurityException secutity exception
     * @throws IOException exception
     */
    @RequestMapping("/")
    public final String welcome(final ModelMap model, final @RequestParam("userKey") String user)
            throws IOException, GeneralSecurityException {
        model.addAttribute("nbEmails", mail.getNbMailNonLu(user));
        return "welcome";
    }
}
