package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.model.CalendarEvent;

/**
 * Class to manage Calendar API.
 * @author Kévin Sibué
 *
 */
@Service
public class CalendarService extends GoogleAPIService<Calendar> {

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
    public CalendarEvent getNextEvent(final String accountName, final String userEmail)
            throws GeneralSecurityException, IOException {

        Calendar calendarService = getService(accountName);

        DateTime now = new DateTime(System.currentTimeMillis());

        Events events = calendarService.events().list("primary").setMaxResults(1).setTimeMin(now)
                .setOrderBy("startTime").setSingleEvents(true).execute();

        List<Event> items = events.getItems();

        if (items.size() > 0) {

            Event gEvent = items.get(0);

            CalendarEvent evnt = new CalendarEvent(gEvent, userEmail);

            return evnt;

        }

        return null;

    }

    @Override
    protected final String getClassName() {
        return CalendarService.class.getName();
    }

}
