package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.oauth2.model.Userinfoplus;

import fr.ynov.dap.comparator.SortByNearest;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.exception.NoGoogleAccountException;
import fr.ynov.dap.exception.NoNextEventException;
import fr.ynov.dap.model.GoogleCalendarEvent;

/**
 * Class to manage Calendar API.
 * @author Kévin Sibué
 *
 */
@Service
public class CalendarService extends GoogleAPIService<Calendar> {

    /**
     * Instance of Account service.
     * Auto resolved by Autowire.
     */
    @Autowired
    private AccountService accountService;

    @Override
    protected final Calendar getGoogleClient(final NetHttpTransport httpTransport, final Credential cdt,
            final String appName) {
        return new Calendar.Builder(httpTransport, getJsonFactory(), cdt).setApplicationName(appName).build();
    }

    /**
     * Get next user's event.
     * @param accountName Current user id
     * @param userEmail Current user mail
     * @return Next event of user linked by the params userId
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    private GoogleCalendarEvent getNextEvent(final String accountName, final String userEmail)
            throws GeneralSecurityException, IOException {

        Calendar calendarService = getService(accountName);

        DateTime now = new DateTime(System.currentTimeMillis());

        Events events = calendarService.events().list("primary").setMaxResults(1).setTimeMin(now)
                .setOrderBy("startTime").setSingleEvents(true).execute();

        List<Event> items = events.getItems();

        if (items.size() > 0) {

            Event gEvent = items.get(0);

            GoogleCalendarEvent evnt = new GoogleCalendarEvent(gEvent, userEmail);

            return evnt;

        }

        return null;

    }

    public GoogleCalendarEvent getNextEvent(final AppUser user)
            throws NoGoogleAccountException, NoNextEventException, GeneralSecurityException, IOException {

        if (user.getGoogleAccounts().size() == 0) {
            throw new NoGoogleAccountException();
        }

        ArrayList<GoogleCalendarEvent> events = new ArrayList<>();

        for (GoogleAccount gAcc : user.getGoogleAccounts()) {

            String accountName = gAcc.getAccountName();

            Userinfoplus userInfo = accountService.getUserInfo(accountName);

            GoogleCalendarEvent evnt = getNextEvent(accountName, userInfo.getEmail());

            if (evnt != null) {
                events.add(evnt);
            }

        }

        if (events.size() == 0) {
            throw new NoNextEventException();
        }

        Collections.sort(events, new SortByNearest());

        return events.get(0);

    }

    @Override
    protected final String getClassName() {
        return CalendarService.class.getName();
    }

}
