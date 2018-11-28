package fr.ynov.dap.microsoft.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.microsoft.auth.TokenResponse;
import fr.ynov.dap.microsoft.data.Message;
import fr.ynov.dap.microsoft.data.PagedResult;
import fr.ynov.dap.microsoft.service.MicrosoftMailService;

/**
 * @author Mon_PC
 */
@Controller
public class MailController {

    /**.
     * microsoftService is managed by Spring on the loadConfig()
     */
    @Autowired
    private MicrosoftMailService microsoftMailService;

    /**
     * @param model param model
     * @param request servlet
     * @param redirectAttributes attributes of redirection
     * @return mail
     */
    @RequestMapping("/microsoft/mail")
    public String mail(final Model model, final HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {
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

        try {
            PagedResult<Message> messages = microsoftMailService.getContenuChaqueMailMicrosoft(tokens.getAccessToken(),
                    email);
            int unreadMessages = microsoftMailService.getNbMailMicrosoft(tokens.getAccessToken(), email);

            model.addAttribute("messages", messages.getValue());
            model.addAttribute("unreadMessages", unreadMessages);
            model.addAttribute("logout", "/logout");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }
        return "mail";
    }

    /**
     * @param userKey param
     * @param model param model
     * @param request servlet
     * @param redirectAttributes attributes of redirection
     * @return mail
     */
    @RequestMapping("/microsoft/mail/{userKey}")
    public String mail(@PathVariable final String userKey, final Model model, final HttpServletRequest request,
            final RedirectAttributes redirectAttributes) {

        try {
            int messagesUnreadAccountsMicrosoft = microsoftMailService.getNbMailAllAccountMicrosoft(userKey);
            List<PagedResult<Message>> messages = microsoftMailService.getContenusMailMicrosoft(userKey);

            model.addAttribute("logout", "/logout");
            model.addAttribute("messages", messages);
            model.addAttribute("messagesUnreadAccountsMicrosoft", messagesUnreadAccountsMicrosoft);

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }
        return "mailAllAccount";
    }
}
