
package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.comparateur.Sorter;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.exception.UserException;
import fr.ynov.dap.google.CalendarService;
import fr.ynov.dap.microsoft.OutlookService;
import fr.ynov.dap.model.EventAllApi;
import fr.ynov.dap.model.GoogleCalendarEvent;
import fr.ynov.dap.model.MicrosoftCalendarEvent;
import fr.ynov.dap.model.outlook.NextEvent;

@RestController
@RequestMapping(value="/calendar")
public class CalendarController extends BaseController{
    @Autowired
    private CalendarService calendarService;
    @Autowired
    private OutlookService outlookService;
    @Override
    protected final String getClassName() {
        return CalendarController.class.getName();
    }
    @RequestMapping("/nextEvent/{userId}")
    public final NextEvent getNextEvent(@PathVariable("userId") final String userId)
            throws UserException, GeneralSecurityException,
            IOException {
        AppUser user = getUserById(userId);
        List<EventAllApi> events = new ArrayList<>();
        GoogleCalendarEvent googleEvent = calendarService.getNextEvent(user);
        if (googleEvent != null) {
            events.add(googleEvent);
        }
        MicrosoftCalendarEvent microsoftEvent = outlookService.getNextEvent(user);
        if (microsoftEvent != null) {
            events.add(microsoftEvent);
        }
        if (events.size() == 0) {
            //TODO bal by Djer |Log4J| Ce n'est pas une "erreur" de ne pas avoir d'évènnement à venire. C'est certes étrange. Un level Info serait suffisant (si j'utilise DaP uniquement pour mes mails)
            getLogger().error("No next event found for user with id : " + userId);
        }
        Collections.sort(events, new Sorter());
        return new NextEvent(events.get(0));
    }
    @RequestMapping("/google/nextEvent/{userId}")
	public final NextEvent getGoogleNextEvent(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException, UserException {
        AppUser user = getUserById(userId);
        GoogleCalendarEvent evnt = calendarService.getNextEvent(user);
        if (evnt == null) {
          //TODO bal by Djer |Log4J| Ce n'est pas une "erreur" de ne pas avoir d'évènnement à venire. C'est certes étrange. Un level Info serait suffisant (si je n'ai que des comtpes Microsoft)
            getLogger().error("No Google next event found for user with id : " + userId);
        }
        return new NextEvent(evnt);

    }
    @RequestMapping("/microsoft/nextEvent/{userId}")
    public NextEvent getMicrosoftNextEvent(@PathVariable("userId") final String userId,
            final HttpServletRequest request)
            throws UserException, IOException {
        AppUser user = getUserById(userId);
        MicrosoftCalendarEvent evnt = outlookService.getNextEvent(user);
        if (evnt == null) {
          //TODO bal by Djer |Log4J| Ce n'est pas une "erreur" de ne pas avoir d'évènnement à venire. C'est certes étrange. Un level Info serait suffisant (si je n'ai que des comptes Google)
            getLogger().error("No Microsoft next event found for user with id : " + userId);
        }
        return new NextEvent(evnt);
    }
}
