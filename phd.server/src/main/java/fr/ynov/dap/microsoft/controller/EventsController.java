package fr.ynov.dap.microsoft.controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;

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
import fr.ynov.dap.microsoft.data.Event;
import fr.ynov.dap.microsoft.data.PagedResult;
import fr.ynov.dap.microsoft.service.MicrosoftEventService;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.microsoft.service.OutlookServiceBuilder;

/**
 *
 * @author Dom .
 *
 */
@Controller
public class EventsController {

    /**
     *
     */
    @Autowired
    private MicrosoftEventService microsoftEventService;

    /**
     *  .
     */
    private static final int MAX_RESULT = 1;

    /**
     *
     * @param model .
     * @param request .
     * @param redirectAttributes .
     * @param userId .
     * @return .
     * @throws IOException .
     * @throws ParseException .
     */
    @RequestMapping("/microsoft/events")
    public String events(final Model model, final HttpServletRequest request,
            final RedirectAttributes redirectAttributes, @RequestParam("userId") final String userId)
            throws ParseException, IOException {
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

        // Sort by start time in descending order
        String sort = "start/dateTime ASC";
        // Only return the properties we care about
        String properties = "organizer,subject,start,end";

        String filterDate = "start/dateTime ge ";
        StringBuilder stringBuilderFilter = new StringBuilder();
        stringBuilderFilter.append(filterDate);
        String dateNow = Instant.now().toString();
        stringBuilderFilter.append("'");
        stringBuilderFilter.append(dateNow);
        stringBuilderFilter.append("'");

        Integer maxResults = MAX_RESULT;

        try {
            PagedResult<Event> events = outlookService
                    .getNextEvents(sort, properties, maxResults, stringBuilderFilter.toString()).execute().body();
            model.addAttribute("events", events.getValue());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/index.html";
        }

        Event nextEventForAllAccount = microsoftEventService.getNextEventForAllAccount(userId);
        model.addAttribute("nextEventForAllAccount", nextEventForAllAccount);

        return "events";
    }

    /**
    *
    * @param model .
    * @param request .
    * @param redirectAttributes .
    * @return .
    * @throws IOException .
    * @throws ParseException .
    */
    @RequestMapping("/microsoft/eventCurrent")
    public String eventCurrent(final Model model, final HttpServletRequest request,
            final RedirectAttributes redirectAttributes) throws ParseException, IOException {
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

        // Sort by start time in descending order
        String sort = "start/dateTime ASC";
        // Only return the properties we care about
        String properties = "organizer,subject,start,end";

        String filterDate = "start/dateTime ge ";
        StringBuilder stringBuilderFilter = new StringBuilder();
        stringBuilderFilter.append(filterDate);
        String dateNow = Instant.now().toString();
        stringBuilderFilter.append("'");
        stringBuilderFilter.append(dateNow);
        stringBuilderFilter.append("'");

        Integer maxResults = MAX_RESULT;

        try {
            PagedResult<Event> events = outlookService
                    .getNextEvents(sort, properties, maxResults, stringBuilderFilter.toString()).execute().body();
            model.addAttribute("events", events.getValue());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/index.html";
        }

        return "eventCurrent";
    }
}
