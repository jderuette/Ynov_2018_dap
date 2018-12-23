package fr.ynov.dap.dap.microsoft.service;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.dap.microsoft.auth.TokenResponse;
import fr.ynov.dap.dap.microsoft.data.MicrosoftAccountData;

/**
 * @author Florian
 */
@Service
public class ContactService {

    /**.
     * LOG
     */
    protected static final Logger LOG = LogManager.getLogger();
    /**.
     * Déclaration de MAXRESULTS
     */
    private static final int MAXRESULTS = 10;

    /**
     * @param redirectAttributes .
     * @param account .
     * @param model .
     * @param userKey .
     * @return page d'accueil
     */
    //TODO brf by Djer |POO| Pourquoi du static ? Tu as annoté la classe avec @Service, donc déclare une méthode d'instance, puis utilise IOC pour l'appeler
    //TODO brf by Djer |MVC| Ton service est "étrange" il renvoie soit un entier, soit une chaine de texte (redirection). Ce service fait trop de travail qui devrait être dans le controller
    public static String nombreDeContact(RedirectAttributes redirectAttributes, MicrosoftAccountData account,
            Model model, String userKey) {
        TokenResponse tokens = account.getTokenResponse();
        String tenantId = account.getTenantId();
        if (tokens == null) {
          //TODO brf by Djer |Log4J| Une petite log ?
            // No tokens in session, user needs to sign in
            redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
            return "redirect:/";
        }

        tokens = AuthHelper.ensureTokens(tokens, tenantId);

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

        // Sort by given name in ascending order (A-Z)
        String sort = "GivenName ASC";
        // Only return the properties we care about
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";
        // Return at most 10 contacts

        try {
            PagedResult<Contact> contacts = outlookService.getContacts(sort, properties, MAXRESULTS).execute().body();
            PagedResult<Contact> nbContacts = outlookService.getNbContacts(properties).execute().body();
            model.addAttribute("contacts", contacts.getValue());

          //TODO brf by Djer |Log4J| Contextualise tes messages
            LOG.debug("Nombre de contact microsoft : " + nbContacts.getValue().length);
            return Integer.toString(nbContacts.getValue().length);
        } catch (IOException e) {
          //TODO brf by Djer |Log4J| Une petite log ?
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }
    }
}
