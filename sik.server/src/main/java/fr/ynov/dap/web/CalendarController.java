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
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.dto.out.NextEventOutDto;
import fr.ynov.dap.exception.NoGoogleAccountException;
import fr.ynov.dap.exception.NoMicrosoftAccountException;
import fr.ynov.dap.exception.NoNextEventException;
import fr.ynov.dap.exception.UserNotFoundException;
import fr.ynov.dap.google.CalendarService;
import fr.ynov.dap.microsoft.OutlookService;
import fr.ynov.dap.model.GoogleCalendarEvent;
import fr.ynov.dap.model.MicrosoftCalendarEvent;

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

    @RequestMapping("/nextEvent/{userId}")
    public final NextEventOutDto getNextEvent(@PathVariable("userId") final String userId)
            throws UserNotFoundException, NoGoogleAccountException, NoNextEventException, GeneralSecurityException,
            IOException, NoMicrosoftAccountException {

        AppUser user = GetUserById(userId);

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

        AppUser user = GetUserById(userId);

        GoogleCalendarEvent evnt = calendarService.getNextEvent(user);

        return new NextEventOutDto(evnt);

    }

    @RequestMapping("/microsoft/nextEvent/{userId}")
    public NextEventOutDto getMicrosoftNextEvent(@PathVariable("userId") final String userId,
            final HttpServletRequest request)
            throws UserNotFoundException, NoMicrosoftAccountException, NoNextEventException, IOException {

        AppUser user = GetUserById(userId);

        MicrosoftCalendarEvent evnt = outlookService.getNextEvent(user);

        return new NextEventOutDto(evnt);

    }

    @Override
    protected final String getClassName() {
        return CalendarController.class.getName();
    }

}
