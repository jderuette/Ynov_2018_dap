package fr.ynov.dap.microsoft.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.microsoft.auth.TokenResponse;
import fr.ynov.dap.microsoft.data.Message;
import fr.ynov.dap.microsoft.data.PagedResult;
import fr.ynov.dap.microsoft.service.MicrosoftMailService;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.microsoft.service.OutlookServiceBuilder;

/**
 *
 * @author Dom
 *
 */
@Controller
public class MailController {

    /**
     *
     */
    private static final int MAX_RESULT = 10;

    /**.
     *
     */
    @Autowired
    private MicrosoftMailService microsoftMailService;

    /**
     *
     * @param model .
     * @param request .
     * @param redirectAttributes .
     * @param userId .
     * @return .
     * @throws IOException .
     */
    @RequestMapping("/microsoft/mail")
    public String mail(final Model model, final HttpServletRequest request, final RedirectAttributes redirectAttributes,
            @RequestParam("userId") final String userId) throws IOException {
        HttpSession session = request.getSession();
        TokenResponse tokens = (TokenResponse) session.getAttribute("tokens");
        if (tokens == null) {
            // No tokens in session, user needs to sign in
            redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
            return "redirect:/";
        }

        try {

            int nbMessagesUnreadAllAccount = microsoftMailService.getNbMailAllAccount(userId);
            model.addAttribute("nbMessagesUnreadAllAccount", nbMessagesUnreadAllAccount);
            model.addAttribute("logoutUrl", "/logout");
            model.addAttribute("messagess", microsoftMailService.getMessagesAllAccount(userId));

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            redirectAttributes.addFlashAttribute("logoutLabel", "Disconneting successfull");
            return "redirect:/";
        }

        return "mail";
    }

    /**
    *
    * @param model .
    * @param request .
    * @param redirectAttributes .
    * @return .
    * @throws IOException .
    */
    @RequestMapping("/microsoft/mailCurrent")
    public String mailCurrent(final Model model, final HttpServletRequest request,
            final RedirectAttributes redirectAttributes) throws IOException {
        HttpSession session = request.getSession();
        TokenResponse tokens = (TokenResponse) session.getAttribute("tokens");
        if (tokens == null) {
            // No tokens in session, user needs to sign in
            redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
            return "redirect:/";
        }

        String tenantId = (String) session.getAttribute("userTenantId");

        tokens = AuthHelper.ensureTokens(tokens, tenantId);

        String email = (String) session.getAttribute("userEmail");

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        // Retrieve messages from the inbox
        String folder = "inbox";
        // Sort by time received in descending order
        String sort = "receivedDateTime DESC";
        // Only return the properties we care about
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        // Return at most 10 messages
        Integer maxResults = MAX_RESULT;

        try {
            PagedResult<Message> messages = outlookService.getMessages(folder, sort, properties, maxResults).execute()
                    .body();
            model.addAttribute("messages", messages.getValue());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/index.html";
        }

        return "mailCurrent";
    }
}
