package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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

import fr.ynov.dap.Constants;
import fr.ynov.dap.comparator.SortByNearest;
import fr.ynov.dap.model.AppUser;
import fr.ynov.dap.model.google.GoogleAccount;
import fr.ynov.dap.model.google.GoogleCalendarEvent;

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
    private OAuthService accountService;

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

        OffsetDateTime utc = OffsetDateTime.now(ZoneOffset.UTC);
        long epochMillis = utc.toEpochSecond() * Constants.SECOND_TO_MILLISECOND;

        Events events = calendarService.events().list("primary").setMaxResults(1).setTimeMin(new DateTime(epochMillis))
                .setOrderBy("startTime").setSingleEvents(true).execute();

        List<Event> items = events.getItems();

        if (items.size() > 0) {

            Event gEvent = items.get(0);

            GoogleCalendarEvent evnt = new GoogleCalendarEvent(gEvent, userEmail);

            return evnt;

        }

        return null;

    }

    /**
     * Get next user's event.
     * @param user DaP User
     * @return User's next event
     * @throws GeneralSecurityException Security exception
     * @throws IOException Exception
     */
    public GoogleCalendarEvent getNextEvent(final AppUser user) throws GeneralSecurityException, IOException {

        if (user.getGoogleAccounts().size() == 0) {
            return null;
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
            return null;
        }

        Collections.sort(events, new SortByNearest());

        return events.get(0);

    }

    @Override
    protected final String getClassName() {
        return CalendarService.class.getName();
    }

}
