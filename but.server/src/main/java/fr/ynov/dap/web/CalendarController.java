package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.google.CalendarService;

/**
 * @author thibault
 *
 */
@RestController
public class CalendarController extends HandlerErrorController {
    /**
     * Calendar Google service.
     */
    @Autowired
    private CalendarService service;

    /**
     * Repository of AppUser.
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**
     * Route to get the next event in calendar of client.
     * @param calendarId calendarID
     * @param userKey ID of user
     * @return Event
     * @throws IOException Exception produced by failed interrupted I/O operations
     * @throws GeneralSecurityException Google security exception
     */
    @RequestMapping("/events/next")
    public Event getNextEvent(@RequestParam("calendarId") final String calendarId,
            @RequestParam("userKey") final String userKey) throws IOException, GeneralSecurityException {

        AppUser user = repositoryUser.findByUserKey(userKey);

        if (user == null) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "User '" + userKey + "' not found.");
        }

        return service.getNextEventOfAllAccounts(calendarId, user);
    }
}
