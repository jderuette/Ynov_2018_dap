package fr.ynov.dap.web.google.api;

import com.google.api.services.calendar.model.Event;
import fr.ynov.dap.services.google.CalendarService;
import fr.ynov.dap.services.google.responses.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

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
     * @param userKey This is the login of the user.
     * @return It returns the response of the request which contains the upcoming event.
     * @throws IOException The CalendarService can throw an IOException.
     * @throws GeneralSecurityException The CalendarService can throw an GeneralSecurityException.
     */
    @RequestMapping("/event/upcomingEvent")
    public final ServiceResponse<Event> getUpcomingEvent(@RequestParam("userKey") final String userKey) throws IOException, GeneralSecurityException {
        return calendarService.getUpcomingEvent(userKey);
    }
}
