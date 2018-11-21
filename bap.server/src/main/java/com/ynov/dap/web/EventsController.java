package com.ynov.dap.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ynov.dap.microsoft.AuthHelper;
import com.ynov.dap.microsoft.Event;
import com.ynov.dap.microsoft.OutlookService;
import com.ynov.dap.microsoft.OutlookServiceBuilder;
import com.ynov.dap.microsoft.PagedResult;
import com.ynov.dap.microsoft.TokenResponse;


@Controller
public class EventsController {

  @RequestMapping("/events")
  public String events(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    HttpSession session = request.getSession();
    TokenResponse tokens = (TokenResponse)session.getAttribute("tokens");
    if (tokens == null) {
      // No tokens in session, user needs to sign in
      redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
      return "redirect:/index";
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
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/index";
    }

    return "events";
  }
}