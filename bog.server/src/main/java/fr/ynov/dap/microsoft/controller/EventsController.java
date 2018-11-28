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
import fr.ynov.dap.microsoft.data.Event;
import fr.ynov.dap.microsoft.data.PagedResult;
import fr.ynov.dap.microsoft.service.MicrosoftEventService;

/**
 * @author Mon_PC
 */
@Controller
public class EventsController {

    /**.
     * microsoftService is managed by Spring on the loadConfig()
     */
    @Autowired
    private MicrosoftEventService microsoftEventService;

    /**
     * @param model model
     * @param request servlet
     * @param redirectAttributes attributes
     * @return events
     */
    @RequestMapping("/microsoft/events")
    public String events(final Model model, final HttpServletRequest request,
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
            PagedResult<Event> events = microsoftEventService.getEvents(tokens.getAccessToken(), email);
            model.addAttribute("events", events.getValue());
            model.addAttribute("logout", "/logout");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }

        return "event";
    }

    /**
     * @param userKey user passé en param
     * @param model model
     * @param request servlet
     * @param redirectAttributes attributes
     * @return events
     */
    @RequestMapping("/microsoft/events/{userKey}")
    public String events(@PathVariable("userKey") final String userKey, final Model model,
            final HttpServletRequest request, final RedirectAttributes redirectAttributes) {

        try {
            Event event = microsoftEventService.getNextEventForAllAccountMicrosoft(userKey);
            model.addAttribute("event", event);
            model.addAttribute("logout", "/logout");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/";
        }

        return "eventAllAccount";
    }
}
