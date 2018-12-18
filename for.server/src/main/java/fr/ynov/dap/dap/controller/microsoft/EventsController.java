package fr.ynov.dap.dap.controller.microsoft;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.data.Token;
import fr.ynov.dap.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.dap.service.Event;
import fr.ynov.dap.dap.service.OutlookService;
import fr.ynov.dap.dap.service.OutlookServiceBuilder;
import fr.ynov.dap.dap.service.PagedResult;

@Controller
public class EventsController {

  @RequestMapping("/events")
  public String events(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    //TODO for by Djer |Rest API| Trop dépendant de la session, fonctionenra très mal sur l'API prévue
      HttpSession session = request.getSession();
    Token tokens = (Token)session.getAttribute("tokens");
    if (tokens == null) {
      // No tokens in session, user needs to sign in
      redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
      return "redirect:/index.html";
    }

    String tenantId = (String)session.getAttribute("userTenantId");

    tokens = AuthHelper.ensureTokens(tokens, tenantId);

    String email = (String)session.getAttribute("userEmail");

    OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

    // Sort by start time in descending order
    String sort = "start/dateTime DESC";
    // Only return the properties we care about
    String properties = "organizer,subject,start,end";
    // Return at most 10 events
    Integer maxResults = 10;

    try {
      PagedResult<Event> events = outlookService.getEvents(
          sort, properties, maxResults)
          .execute().body();
      model.addAttribute("events", events.getValue());
    } catch (IOException e) {
      //TODO for by Djer |Log4J| Une petite Log ?
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/index.html";
    }

    return "events";
  }
}