package fr.ynov.dap.microsoft.controller;

import java.io.IOException;

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
import fr.ynov.dap.microsoft.service.MicrosoftContactService;

/**
 * @author Mon_PC
 */
@Controller
public class ContactsController {

    /**.
     * microsoftService is managed by Spring on the loadConfig()
     */
    @Autowired
    private MicrosoftContactService microsoftContactService;

    /**
     * @param model model
     * @param request servletRequest
     * @param redirectAttributes attributes redirect
     * @return contacts
     */
    @RequestMapping("/microsoft/contacts")
    public String contacts(final Model model, final HttpServletRequest request,
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
            Integer nbContact = microsoftContactService.getContacts(tokens.getAccessToken(), email).getValue().length;
            model.addAttribute("nbContact", nbContact);
            model.addAttribute("contacts",
                    microsoftContactService.getContacts(tokens.getAccessToken(), email).getValue());
            model.addAttribute("logout", "/logout");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }

        return "contacts";
    }

    /**
     * @param userKey user param
     * @param model model
     * @param request servletRequest
     * @param redirectAttributes attributes redirect
     * @return contacts
     */
    @RequestMapping("/microsoft/contacts/{userKey}")
    public String contacts(@PathVariable("userKey") final String userKey, final Model model,
            final HttpServletRequest request, final RedirectAttributes redirectAttributes) {

        try {
            int nbContactAllAccountMicrosoft = microsoftContactService.getNbContactAllAccountMicrosoft(userKey);
            model.addAttribute("logout", "/logout");
            model.addAttribute("nbContactAllAccountMicrosoft", nbContactAllAccountMicrosoft);
            model.addAttribute("logout", "/logout");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }

        return "contactsAllAccount";
    }
}
