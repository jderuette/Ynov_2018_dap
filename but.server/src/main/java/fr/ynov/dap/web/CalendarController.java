package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;


import fr.ynov.dap.google.CalendarService;

/**
 * @author thibault
 *
 */
@RestController
public class CalendarController extends GoogleController {
    /**
     * Calendar Google service.
     */
    @Autowired
    private CalendarService service;

    /**
     * Route to get the next event in calendar of client.
     * @param calendarId calendarID
     * @param userId ID of user (associate token)
     * @return Event
     * @throws IOException Exception produced by failed interrupted I/O operations
     * @throws GeneralSecurityException Google security exception
     */
    @RequestMapping("/events/next")
    public Event getNextEvent(@RequestParam("calendarId") final String calendarId,
            @RequestParam("userId") final String userId) throws IOException, GeneralSecurityException {
        return service.getNextEvent(calendarId, userId);
    }
}
