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

import fr.ynov.dap.comparator.SortByNearest;
import fr.ynov.dap.contract.ApiEvent;
import fr.ynov.dap.dto.out.NextEventOutDto;
import fr.ynov.dap.exception.NoGoogleAccountException;
import fr.ynov.dap.exception.NoMicrosoftAccountException;
import fr.ynov.dap.exception.NoNextEventException;
import fr.ynov.dap.exception.UserNotFoundException;
import fr.ynov.dap.google.CalendarService;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.model.AppUser;
import fr.ynov.dap.model.google.GoogleCalendarEvent;
import fr.ynov.dap.model.microsoft.MicrosoftCalendarEvent;

/**
 * Controller to manage every call to Google Calendar API.
 * @author Kévin Sibué
 *
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController extends BaseController {

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
    public final NextEventOutDto getNextEvent(@PathVariable("userId") final String userId)
            throws UserNotFoundException, NoGoogleAccountException, NoNextEventException, GeneralSecurityException,
            IOException, NoMicrosoftAccountException {

        AppUser user = getUserById(userId);

        List<ApiEvent> events = new ArrayList<>();

        GoogleCalendarEvent gEvnt = calendarService.getNextEvent(user);
        if (gEvnt != null) {
            events.add(gEvnt);
        }

        MicrosoftCalendarEvent msEvnt = outlookService.getNextEvent(user);
        if (msEvnt != null) {
            events.add(msEvnt);
        }

        if (events.size() == 0) {

            getLogger().error("No next event found for user with id : " + userId);

            throw new NoNextEventException();

        }

        Collections.sort(events, new SortByNearest());

        return new NextEventOutDto(events.get(0));

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
    public final NextEventOutDto getGoogleNextEvent(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException, UserNotFoundException, NoGoogleAccountException,
            NoNextEventException {

        AppUser user = getUserById(userId);

        GoogleCalendarEvent evnt = calendarService.getNextEvent(user);

        if (evnt == null) {

            getLogger().error("No Google next event found for user with id : " + userId);

            throw new NoNextEventException();

        }

        return new NextEventOutDto(evnt);

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
    public NextEventOutDto getMicrosoftNextEvent(@PathVariable("userId") final String userId,
            final HttpServletRequest request)
            throws UserNotFoundException, NoMicrosoftAccountException, NoNextEventException, IOException {

        AppUser user = getUserById(userId);

        MicrosoftCalendarEvent evnt = outlookService.getNextEvent(user);

        if (evnt == null) {

            getLogger().error("No Microsoft next event found for user with id : " + userId);

            throw new NoNextEventException();

        }

        return new NextEventOutDto(evnt);

    }

    @Override
    protected final String getClassName() {
        return CalendarController.class.getName();
    }

}
