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
import fr.ynov.dap.microsoft.data.Contact;
import fr.ynov.dap.microsoft.data.PagedResult;
import fr.ynov.dap.microsoft.service.MicrosoftContactService;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.microsoft.service.OutlookServiceBuilder;

/**
 *
 * @author Dom
 *
 */
@Controller
public class ContactsController {

    /**
    *
    */
    @Autowired
    private MicrosoftContactService microsoftContactService;

    /**
     *
     * @param model .
     * @param request .
     * @param redirectAttributes .
     * @return .
     * @throws IOException .
     * @param userId .
     */
    @RequestMapping("/microsoft/contacts")
    public String contacts(final Model model, final HttpServletRequest request,
            final RedirectAttributes redirectAttributes, @RequestParam("userId") final String userId)
            throws IOException {
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

        String sort = "GivenName ASC";
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";

        try {
            PagedResult<Contact> contacts = outlookService.getContacts(sort, properties).execute().body();
            int nbContact = contacts.getValue().length;
            model.addAttribute("contacts", contacts.getValue());
            model.addAttribute("nbContacts", nbContact);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/index.html";
        }

        int nbContactAllAccount = microsoftContactService.getNbContactAllAccount(userId);
        model.addAttribute("nbContactAllAccount", nbContactAllAccount);

        return "contacts";
    }

    /**
     *
     * @param model .
     * @param request .
     * @param redirectAttributes .
     * @return .
     * @throws IOException .
     */
    @RequestMapping("/microsoft/contactCurrent")
    public String contactCurrent(final Model model, final HttpServletRequest request,
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

        String sort = "GivenName ASC";
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";

        try {
            PagedResult<Contact> contacts = outlookService.getContacts(sort, properties).execute().body();
            int nbContact = contacts.getValue().length;
            model.addAttribute("contacts", contacts.getValue());
            model.addAttribute("nbContacts", nbContact);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/index.html";
        }
        return "contacts";
    }
}
