package fr.ynov.dap.web.microsoft;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.services.microsoft.AuthHelper;
import fr.ynov.dap.services.microsoft.Event;
import fr.ynov.dap.services.microsoft.OutlookService;
import fr.ynov.dap.services.microsoft.OutlookServiceBuilder;
import fr.ynov.dap.services.microsoft.PagedResult;
import fr.ynov.dap.services.microsoft.TokenResponse;

/**
 * controlleur des évènements du calendrier microsoft.
 * @author alexa
 *
 */
@Controller
public class EventsController {
  /**
   * nombre maximal de résultat renvoyé.
   */
  static final Integer MAXRESULTS = 10;
  /**
   * récupère les évènements du compte microsoft.
   * @param model model
   * @param request request
   * @param redirectAttributes attributes
   * @return template
   */
  @RequestMapping("/events")
  public String events(final Model model, final HttpServletRequest request,
    final RedirectAttributes redirectAttributes) {
    HttpSession session = request.getSession();
    TokenResponse tokens = (TokenResponse) session.getAttribute("tokens");
    if (tokens == null) {
      // No tokens in session, user needs to sign in
      redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
      return "redirect:/index.html";
    }

    String tenantId = (String) session.getAttribute("userTenantId");

    tokens = AuthHelper.ensureTokens(tokens, tenantId);

    String email = (String) session.getAttribute("userEmail");

    OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

    // Sort by start time in descending order
    String sort = "start/dateTime DESC";
    // Only return the properties we care about
    String properties = "organizer,subject,start,end";
    // Return at most 10 events

    try {
      PagedResult<Event> events = outlookService.getEvents(
          sort, properties, MAXRESULTS)
          .execute().body();
      model.addAttribute("events", events.getValue());
    } catch (IOException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/index.html";
    }

    return "events";
  }
}
