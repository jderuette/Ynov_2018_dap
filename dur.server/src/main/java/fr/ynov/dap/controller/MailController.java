package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.google.GmailService;
import fr.ynov.dap.microsoft.MicrosoftService;
import fr.ynov.dap.microsoft.OutlookApiCalls;
import fr.ynov.dap.microsoft.OutlookService;
import fr.ynov.dap.microsoft.OutlookServiceBuilder;
import fr.ynov.dap.microsoft.entity.Message;
import fr.ynov.dap.microsoft.entity.PagedResult;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * Controller to manage every call to Google Gmail API.
 * @author Robin DUDEK
 *
 */
@Controller
@RequestMapping("/mail")
public class MailController extends BaseController {

    @Override
    protected final String getClassName() {
        return MailController.class.getName();
    }

    /**
     * the appUserRepository manages all database accesses for The AppUser.
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Instance of Gmail service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private GmailService gmailService;

    /**
     * Instance of Outlook service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private OutlookService microsoftMailService;
    /* @Autowired
    private MicrosoftMailService microsoftMailService;*/

    @RequestMapping("/nbUnread/{userKey}")
    @ResponseBody
    public final String getNumberOfUnreadMessage(
            @PathVariable("userKey") final String userKey)
            throws  IOException, GeneralSecurityException {

        AppUser user = appUserRepository.findByUserKey(userKey);

        Integer googleNbUnreadMail = gmailService.getNbUnreadEmails(user);

        Integer microsoftNbUnreadMail = microsoftMailService.getNbUnreadEmails(user);
        Integer total = googleNbUnreadMail + microsoftNbUnreadMail;

        return "Nombre de mails non lu sur tout les comptes Google et Microsoft de l'utilisateur " + userKey + " : "
                + total;

    }

    /**
     * Endpoint to get this number of unread message.
     * @param userId User's Id
     * @return Number of unread mail for user linked by userId. JSON Formatted.
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     */
    @RequestMapping("/google/nbUnread/{userKey}")
    public final String getGoogleNumberOfUnreadMessage(@PathVariable("userKey") final String userKey, final Model model)
            throws IOException, GeneralSecurityException {

        AppUser user = appUserRepository.findByUserKey(userKey);

        Integer googleNbUnreadMail = gmailService.getNbUnreadEmails(user);

        model.addAttribute("nbUnread", googleNbUnreadMail);
        model.addAttribute("username", user.getUserKey());
        model.addAttribute("nbAccount", user.getGoogleAccounts().size());
        return "mail";

    }


    @RequestMapping("/microsoft/nbUnread/{userId}")
    public String nbUnreadMicrosoftMails(ModelMap model, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        TokenResponse tokens = (TokenResponse) session.getAttribute("tokens");
        if (tokens == null) {
            // No tokens in session, user needs to sign in
            redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
            return "redirect:/index.html";
        }

        String tenantId = (String) session.getAttribute("userTenantId");

        tokens = MicrosoftService.ensureTokens(tokens, tenantId);

        String email = (String) session.getAttribute("userEmail");

        OutlookApiCalls outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

        // Retrieve messages from the inbox
        String folder = "inbox";
        // Sort by time received in descending order
        String sort = "receivedDateTime DESC";
        // Only return the properties we care about
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        // Return at most 10 messages
        Integer maxResults = 10;

        try {
            PagedResult<Message> messages = outlookService.getMessages(folder, sort, properties, maxResults).execute()
                    .body();
            model.addAttribute("messages", messages.getValue());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/index.html";
        }

        return "mail";
    }

    @RequestMapping("/microsoft/{userKey}")
    public String microsoftMails(@PathVariable("userKey") final String userKey, ModelMap model,
            HttpServletRequest request) {
        
        model.put("messages", microsoftMailService.getMailForAllAccounts(userKey));

        return "mail";

    }

}
