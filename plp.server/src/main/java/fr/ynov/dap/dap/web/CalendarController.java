package fr.ynov.dap.dap.web;

import fr.ynov.dap.dap.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * @author Pierre Plessy
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController {
    /**
     * Instantiate instance of CalendarService.
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * get the next event for a user when there is a request in /calendar/nextEvent.
     *
     * @param userId : userkey param
     * @return Map : contains accessRole, subject, start (date or/and time), finish (date or/and time)
     * @throws IOException              : throw exception
     * @throws GeneralSecurityException : throw exception
     */
    @RequestMapping("/nextEvent")
    public final Map<String, String> getNextEvent(@RequestParam("userKey") final String userId)
            throws IOException, GeneralSecurityException {
        return calendarService.getNextEvent(userId);
    }

}
