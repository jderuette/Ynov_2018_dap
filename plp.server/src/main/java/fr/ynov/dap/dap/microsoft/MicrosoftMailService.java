package fr.ynov.dap.dap.microsoft;

import fr.ynov.dap.dap.GMailService;
import fr.ynov.dap.dap.data.microsoft.Token;
import fr.ynov.dap.dap.microsoft.models.*;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MicrosoftMailService {
    /**
     * Instantiate logger.
     */
    private static final Logger LOG = LogManager.getLogger(GMailService.class);

    /**
     * instantiate userRepository
     */
    @Autowired
    AppUserRepository userRepository;

    //    public Map<String, Integer> mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    public String mail(final String userKey, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();
        Token tokens = (Token) session.getAttribute("tokens");
        Map<String, Integer> response = new HashMap<>();
        if (tokens == null) {
            // No tokens in session, user needs to sign in
            redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
            response.put("connect user", 0);
        }

        String tenantId = (String) session.getAttribute("userTenantId");

        tokens = AuthHelper.ensureTokens(tokens, tenantId);

//        String email = (String) session.getAttribute("userEmail");

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

        // Retrieve messages from the inbox
        String folder = "inbox";
        // Sort by time received in descending order
        String sort = "receivedDateTime DESC";
        // Only return the properties we care about
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        // Return at most 10 messages
        Integer maxResults = 10;

        try {
            PagedResult<Message> messages = outlookService.getMessages(
                    folder, sort, properties, maxResults)
                    .execute().body();
//            model.addAttribute("messages", messages.getValue());
//            model.addAttribute("nbUnread", messages.getValue().length);
            response.put("messages", messages.getValue().length);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            LOG.error("Can't get messages", e);
            response.put("error", 1);
        }

//        return response;
        return "mail_micro";
    }
}
