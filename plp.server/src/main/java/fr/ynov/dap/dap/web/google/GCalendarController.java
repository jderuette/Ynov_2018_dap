package fr.ynov.dap.dap.web.google;

import com.google.api.services.calendar.model.Event;
import fr.ynov.dap.dap.CalendarService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pierre Plessy
 */
@RestController
@RequestMapping("/google/calendar")
public class GCalendarController {
    /**
     * Instantiate instance of CalendarService.
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * Instantiate Logger.
     */
    private static final Logger log = LogManager.getLogger(GCalendarController.class);


    /**
     * get the next event for a user when there is a request in /calendar/nextEvent.
     *
     * @param userKey : userkey param
     * @return Map : contains accessRole, subject, start (date or/and time), finish (date or/and time)
     * @throws IOException              : throw exception
     * @throws GeneralSecurityException : throw exception
     */
    @RequestMapping("/nextEvent")
    public final Map<String, String> getNextEvent(@RequestParam("userKey") final String userKey)
            throws IOException, GeneralSecurityException {

        Map<String, String> response = new HashMap<>();
        Event nextEvent = calendarService.getNextEvent(userKey);
        if (nextEvent.isEmpty()) {
            response.put("subject", "No incomming event");
        } else {
            //TODO plp by Djer |API Google| accesRole = Status de l'évènnement ?
            response.put("accessRole", nextEvent.getStatus());
            response.put("subject", nextEvent.getSummary());
            if (nextEvent.getStart().getDate() == null) {
                response.put("start", nextEvent.getStart().getDateTime().toString());
                response.put("finish", nextEvent.getEnd().getDateTime().toString());
            } else {
                response.put("start", nextEvent.getStart().getDate().toString());
                response.put("finish", nextEvent.getEnd().getDate().toString());
            }
        }
        //TODO plp by Djer |API Google| Gestion de "MON" status ?
        return response;
    }
}
