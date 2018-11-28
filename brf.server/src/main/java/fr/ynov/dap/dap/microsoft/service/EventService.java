package fr.ynov.dap.dap.microsoft.service;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.dap.microsoft.auth.TokenResponse;
import fr.ynov.dap.dap.microsoft.data.MicrosoftAccountData;

/**
 * @author Florian
 */
public class EventService {

    /**.
     * LOG
     */
    protected static final Logger LOG = LogManager.getLogger();
    /**.
     * Déclaration de MAXRESULTS
     */
    private static final int MAXRESULTS = 1;

    /**
     * @param redirectAttributes .
     * @param account .
     * @param model .
     * @param userKey .
     * @return Nombre de mail non lu
     */
    public static Event[] firstEvent(RedirectAttributes redirectAttributes, MicrosoftAccountData account, Model model,
            String userKey, Event[] evenementEnCours) {
        TokenResponse tokens = account.getTokenResponse();
        String tenantId = account.getTenantId();
        if (tokens == null) {
            // No tokens in session, user needs to sign in
            redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
        }

        tokens = AuthHelper.ensureTokens(tokens, tenantId);

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

        // Sort by start time in descending order
        String sort = "start/dateTime DESC";
        // Only return the properties we care about
        String properties = "organizer,subject,start,end";

        try {
            PagedResult<Event> events = outlookService.getEvents(sort, properties, MAXRESULTS).execute().body();
            LOG.debug("Nombre d'Event microsoft : " + events.getValue().length);
            if (events.getValue().length == 0) {
                return evenementEnCours;
            } else {
                return events.getValue();
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return null;
        }
    }
}
