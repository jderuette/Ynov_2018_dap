package com.ynov.dap.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ynov.dap.google.business.CalendarService;
import com.ynov.dap.google.models.CalendarModel;
import com.ynov.dap.microsoft.business.AuthHelper;
import com.ynov.dap.microsoft.business.OutlookService;
import com.ynov.dap.microsoft.business.OutlookServiceBuilder;
import com.ynov.dap.microsoft.models.Event;
import com.ynov.dap.microsoft.models.PagedResult;
import com.ynov.dap.microsoft.models.TokenResponse;

@RestController
@RequestMapping("calendar")
public class CalendarController {
	
    /** The calendar service. */
    @Autowired
    private CalendarService calendarService;

    /**
     * Gets the calendar.
     *
     * @param gUser the g user
     * @return the calendar
     */
    @GetMapping(value = "/google/{appUser}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CalendarModel> getCalendar(@PathVariable final String appUser) {
        if (appUser == null || appUser.length() <= 0) {
            return new ResponseEntity<CalendarModel>(new CalendarModel("", null, null, ""), HttpStatus.BAD_REQUEST);
        }

        try {
        	System.out.println(calendarService.finalReturn(appUser));
        	
            return new ResponseEntity<CalendarModel>(calendarService.finalReturn(appUser), HttpStatus.ACCEPTED);
        } catch (Exception e) {
            //getLogger().error(e.getMessage());
        }
        
        return new ResponseEntity<CalendarModel>(new CalendarModel("", null, null, ""), HttpStatus.BAD_REQUEST);
    }
    
    @GetMapping(value = "/microsoft/{appUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String events(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        
    	HttpSession session = request.getSession();
        TokenResponse tokens = (TokenResponse) session.getAttribute("tokens");
        if (tokens == null) {
          // No tokens in session, user needs to sign in
          redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
          return "redirect:/index";
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

    /*
    @Override
    public String getClassName() {
        return CalendarController.class.getName();
    }
	*/
}
