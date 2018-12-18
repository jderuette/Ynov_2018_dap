package fr.ynov.dap.web.microsoft.controllers;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import fr.ynov.dap.data.microsoft.Event;
import fr.ynov.dap.data.microsoft.MicrosoftHelper;
import fr.ynov.dap.data.microsoft.PagedResult;
import fr.ynov.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.services.microsoft.OutlookService;
import fr.ynov.dap.services.microsoft.OutlookServiceBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
//TODO mbf by Djer |MVC| Séparation service/Controller ?
public class EventsController {

    @RequestMapping("/events")
    public final String events(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        TokenResponse tokens = (TokenResponse)session.getAttribute("tokens");
        if (tokens == null) {
            // No tokens in session, user needs to sign in
            redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
            return "redirect:/home";
        }

        String tenantId = (String)session.getAttribute("userTenantId");

        tokens = MicrosoftHelper.ensureTokens(tokens, tenantId);

        String email = (String)session.getAttribute("userEmail");

        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        // Sort by start time in descending order
        String sort = "start/dateTime DESC";
        // Only return the properties we care about
        String properties = "organizer,subject,start,end";

        try {
            PagedResult<Event> events = outlookService.getEvents(
                    sort, properties, 10)
                    .execute().body();
            model.addAttribute("events", events.getValue());
        } catch (IOException e) {
          //TODO mbf by Djer |Log4J| Une petite log ?
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/home";
        }

        return "event";
    }
}