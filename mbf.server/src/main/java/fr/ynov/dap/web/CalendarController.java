package fr.ynov.dap.web;

import fr.ynov.dap.services.Google.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * The CalendarController handles all the client(s) requests related to calendar events.
 */
@RestController
public class CalendarController {

    /**
     * The calendar service attribute automatically wired by Spring.
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * This method handles the client's request on the endpoint /event/upcomingEvent.
     * @param userKey This is the userKey of the currently authenticated user.
     * @return It returns the response of the request which contains the upcoming event.
     * @throws IOException The CalendarService can throw an IOException.
     * @throws GeneralSecurityException The CalendarService can throw an GeneralSecurityException.
     */
    @RequestMapping("/event/upcomingEvent")
    public final Map<String, Object> getUpcomingEvent(@RequestParam("userKey") final String userKey) throws IOException, GeneralSecurityException {
        return calendarService.getUpcomingEvent(userKey);
    }
}
