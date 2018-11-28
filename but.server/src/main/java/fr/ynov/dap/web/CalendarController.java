package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.google.CalendarService;
import fr.ynov.dap.microsoft.MicrosoftCalendarService;

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
     * Calendar Microsoft service.
     */
    @Autowired
    private MicrosoftCalendarService microsoftCalendarService;

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
    public EventResponse getNextEvent(@RequestParam("calendarId") final String calendarId,
            @RequestParam("userKey") final String userKey) throws IOException, GeneralSecurityException {

        AppUser user = repositoryUser.findByUserKey(userKey);

        if (user == null) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "User '" + userKey + "' not found.");
        }
        EventResponse lastEventGoogle = service.getNextEventOfAllAccounts(calendarId, user);
        EventResponse lastEventMicrosoft = microsoftCalendarService.getNextEvent(user);
        EventResponse result;

        if (lastEventMicrosoft == null && lastEventGoogle == null) {
            result = null;
        } else if (lastEventGoogle == null) {
            result = lastEventMicrosoft;
        } else if (lastEventMicrosoft == null) {
            result = lastEventGoogle;
        } else if (lastEventMicrosoft.getStart().before(lastEventGoogle.getStart())) {
            result = lastEventMicrosoft;
        } else {
            result = lastEventGoogle;
        }

        return result;
    }
}
