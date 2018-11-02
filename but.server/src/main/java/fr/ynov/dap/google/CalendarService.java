package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

/**
 * Manage Google Calendar Service.
 * @author thibault
 *
 */
@Service
public class CalendarService extends GoogleService {
    /**
     * Logger for the class.
     */
    //TODO but by Djer Logger generalement en static (pas la peine d'en avoir un par instance)
    // final (pseudo-référence non modifiable)
    private Logger logger = LogManager.getLogger();

    /**
     * Connect to Google Calendar Service.
     * @param userId ID of user (associate token)
     * @return Calendar google calendar service
     * @throws IOException Exception produced by failed interrupted I/O operations
     * @throws GeneralSecurityException Google security exception
     */
    public Calendar getService(final String userId) throws IOException, GeneralSecurityException {
        this.logger.info("Generate service Calendar for user '" + userId + "'");
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(httpTransport, this.getJsonFactory(), getCredentials(userId))
                .setApplicationName(this.getConfig().getApplicationName()).build();

        return service;
    }
    /**
     * Get the next event in calendar of client.
     * @param calendarId calendarID
     * @param userId ID of user (associate token)
     * @return Event
     * @throws IOException Exception produced by failed interrupted I/O operations
     * @throws GeneralSecurityException Google security exception
     */
    public Event getNextEvent(final String calendarId, final String userId)
            throws IOException, GeneralSecurityException {
        Event event = null;
        Calendar service = this.getService(userId);
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list(calendarId).setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
                .setSingleEvents(true).execute();
        List<Event> items = events.getItems();
        if (!items.isEmpty()) {
            event = events.getItems().get(0);
        }

        return event;
    }
}
