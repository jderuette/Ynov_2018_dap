package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
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
public class CalendarService extends GoogleAPIService {

    /**
     * Create new Calendar service for user.
     * @param userId Current user
     * @return Calendar services
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    public Calendar getService(final String userId) throws GeneralSecurityException, IOException {

        Credential cdt = getCredential(userId);

        if (cdt != null) {

            final String appName = getConfig().getApplicationName();
            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            return new Calendar.Builder(httpTransport, getJsonFactory(), cdt).setApplicationName(appName).build();

        }

        return null;

    }

    /**
     * Get next user's event.
     * @param user Current user id
     * @return Next event of user linked by the params userId
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    public CalendarEvent getNextEvent(final String user) throws GeneralSecurityException, IOException {

        Calendar calendarService = getService(user);

        DateTime now = new DateTime(System.currentTimeMillis());

        Events events = calendarService.events().list("primary").setMaxResults(1).setTimeMin(now)
                .setOrderBy("startTime").setSingleEvents(true).execute();

        List<Event> items = events.getItems();

        if (items.size() > 0) {

            Event gEvent = items.get(0);

            CalendarEvent evnt = new CalendarEvent(gEvent);

            return evnt;

        }

        return null;

    }

    @Override
    protected final String getClassName() {
        return CalendarService.class.getName();
    }

}
