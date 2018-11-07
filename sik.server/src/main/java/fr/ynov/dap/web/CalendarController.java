package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.oauth2.model.Userinfoplus;

import fr.ynov.dap.comparator.SortByNearest;
import fr.ynov.dap.contract.AppUserRepository;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.dto.out.NextEventOutDto;
import fr.ynov.dap.exception.NoGoogleAccountException;
import fr.ynov.dap.exception.NoNextEventException;
import fr.ynov.dap.exception.UserNotFoundException;
import fr.ynov.dap.google.AccountService;
import fr.ynov.dap.google.CalendarService;
import fr.ynov.dap.model.CalendarEvent;

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
     * Instance of Account service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private AccountService accountService;

    /**
     * Instance of AppUserRepository.
     * Auto resolved by Autowire.
     */
    @Autowired
    private AppUserRepository appUserRepository;

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
    @RequestMapping("/nextEvent/{userId}")
    public final NextEventOutDto getNextEvent(@PathVariable("userId") final String userId)
            throws GeneralSecurityException, IOException, UserNotFoundException, NoGoogleAccountException,
            NoNextEventException {

        AppUser user = appUserRepository.findByUserKey(userId);

        if (user == null) {
            throw new UserNotFoundException();
        }

        if (user.getGoogleAccounts().size() == 0) {
            throw new NoGoogleAccountException();
        }

        ArrayList<CalendarEvent> events = new ArrayList<>();

        for (GoogleAccount gAcc : user.getGoogleAccounts()) {

            String accountName = gAcc.getAccountName();

            Userinfoplus userInfo = accountService.getUserInfo(accountName);

            CalendarEvent evnt = calendarService.getNextEvent(accountName, userInfo.getEmail());

            if (evnt != null) {
                events.add(evnt);
            }

        }

        if (events.size() == 0) {
            throw new NoNextEventException();
        }

        Collections.sort(events, new SortByNearest());

        CalendarEvent evnt = events.get(0);

        return new NextEventOutDto(evnt);

    }

    @Override
    protected final String getClassName() {
        return CalendarController.class.getName();
    }

}
