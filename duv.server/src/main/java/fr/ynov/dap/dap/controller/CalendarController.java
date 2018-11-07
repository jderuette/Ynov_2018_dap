package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.dap.google.CalendarService;
import fr.ynov.dap.dap.google.UserInfoService;
import fr.ynov.dap.dap.model.CalendarEvent;

/**
 *
 * @author David_tepoche
 *
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController {

    /**
     * linkg config.
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * link the UserinfoService.
     */
    @Autowired
    private UserInfoService userInfoService;

    /**
     * dunno.
     *
     * @param nbrEvent .
     * @param userId   of the local token
     * @return list of CalendarEvent
     * @throws GeneralSecurityException throws by calendarService when it try to
     *                                  getCredential
     * @throws IOException              throws by userInfoSErvice or calendarService
     * @throws NumberFormatException    if the nbrEvent cannot be cast as an integer
     */
    @GetMapping("/getNextEvent/{nbrEnvent}/{userId}")
    public @ResponseBody List<CalendarEvent> getNextEvent(@PathVariable(value = "nbrEnvent") final String nbrEvent,
            @PathVariable("userId") final String userId)
            throws NumberFormatException, IOException, GeneralSecurityException {

        final List<Event> lastEvents = calendarService.getLastEvent(Integer.valueOf(nbrEvent), userId);

        final String userEmail = userInfoService.getEmail(userId);

        final List<CalendarEvent> calendarEvents = new ArrayList<>();
        for (final Event event : lastEvents) {
            calendarEvents.add(new CalendarEvent(event, userEmail));
        }
        return calendarEvents;
    }
}
