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
    public static String nombreDeContact(RedirectAttributes redirectAttributes, MicrosoftAccountData account,
            Model model, String userKey) {
        TokenResponse tokens = account.getTokenResponse();
        String tenantId = account.getTenantId();
        if (tokens == null) {
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

            LOG.debug("Nombre de contact microsoft : " + nbContacts.getValue().length);
            return Integer.toString(nbContacts.getValue().length);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }
    }
}
