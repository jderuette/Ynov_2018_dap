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
public class MailService {

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
     * @return Nombre de mail non lu
     */
    //TODO brf by Djer |POO| Pourquoi du static ? Tu as annoté la classe avec @Service, donc déclare une méthode d'instance, puis utilise IOC pour l'appeler
    public static String NombreDeMail(final RedirectAttributes redirectAttributes, MicrosoftAccountData account,
            Model model, String userKey) {
        TokenResponse tokens = account.getTokenResponse();
        String tenantId = account.getTenantId();
        if (tokens == null) {
            // No tokens in session, user needs to sign in
            //TODO brf by Djer |Log4J| Une petite log ?
            redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
            return "redirect:/";
        }

        tokens = AuthHelper.ensureTokens(tokens, tenantId);

        //TODO brf by Djer |API Microsoft| Permet de filtrer sur les "unread" ?
        String filter = "inbox";

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

        // Retrieve messages from the inbox
        String folder = "inbox";
        // Sort by time received in descending order
        String sort = "receivedDateTime DESC";
        // Only return the properties we care about
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";

        try {
            PagedResult<Message> messages = outlookService.getMessages(
                    folder, sort, properties, MAXRESULTS)
                    .execute().body();
            PagedResult<Message> unreadMails = outlookService.getTotalUnreadMails(filter)
                    .execute().body();
            model.addAttribute("messages", messages.getValue());
          //TODO brf by Djer |Log4J| Contextualise tes messages
            LOG.debug("Message non lu : " + unreadMails.getTotalUnreadMails());
            return unreadMails.getTotalUnreadMails();
        } catch (IOException e) {
          //TODO brf by Djer |Log4J| Une petite log ?
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }
    }
}
