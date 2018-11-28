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

import fr.ynov.dap.comparator.Sorter;
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
	
	/**
     * Instance of Calendar service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private CalendarService calendarService;

    /**
     * Instance of Outlook service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private OutlookService outlookService;

    @Override
    protected final String getClassName() {
        return CalendarController.class.getName();
    }
    /**
     * Get next event for a specific user from every linked account (e.g. microsoft, google, ...)
     * @param userId User id
     * @return NextEventOutDto instance
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws UserNotFoundException Thrown when user is not found
     * @throws NoGoogleAccountException Thrown when user haven't any google account
     * @throws NoMicrosoftAccountException Thrown when user haven't any microsoft account
     * @throws NoNextEventException Thrown when user haven't any next event
     */
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

            getLogger().error("No next event found for user with id : " + userId);

        }

        Collections.sort(events, new Sorter());

        return new NextEvent(events.get(0));

    }

    /**
     * Endpoint to get the user's next event.
     * @param userId User's Id
     * @return Next event for user linked by userId
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws UserNotFoundException Thrown when user is not found
     * @throws NoGoogleAccountException Thrown when user haven't any google account
     * @throws NoNextEventException Thrown when user haven't any next event
     */
    @RequestMapping("/google/nextEvent/{userId}")
    public final NextEvent getGoogleNextEvent(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException, UserException {

        AppUser user = getUserById(userId);

        GoogleCalendarEvent evnt = calendarService.getNextEvent(user);

        if (evnt == null) {

            getLogger().error("No Google next event found for user with id : " + userId);


        }

        return new NextEvent(evnt);

    }

    /**
     * Get next event for microsoft account of a user.
     * @param userId User id
     * @param request http request
     * @return NextEventOutDto instance
     * @throws UserNotFoundException User unknow
     * @throws NoMicrosoftAccountException No microsoft account for specified user
     * @throws NoNextEventException No next event found for current user
     * @throws IOException Exception
     */
    @RequestMapping("/microsoft/nextEvent/{userId}")
    public NextEvent getMicrosoftNextEvent(@PathVariable("userId") final String userId,
            final HttpServletRequest request)
            throws UserException, IOException {

        AppUser user = getUserById(userId);

        MicrosoftCalendarEvent evnt = outlookService.getNextEvent(user);

        if (evnt == null) {

            getLogger().error("No Microsoft next event found for user with id : " + userId);

        }

        return new NextEvent(evnt);

    }

}
