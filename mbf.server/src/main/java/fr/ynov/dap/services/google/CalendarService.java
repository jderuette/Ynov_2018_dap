package fr.ynov.dap.services.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import fr.ynov.dap.services.google.responses.ServiceResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * The CalendarService is the service that communicates with the google Calendar API.
 */
@Service
public class CalendarService extends GoogleService {

    /**
     * This constant holds the max results number to be used in the query.
     */
    private static final Integer MAX_RESULTS = 10;

    /**
     * This returns the Calendar object that manipulates events and other calendar data.
     * @param userKey This is the login of the user.
     * @return The Calendar object that manipulates events and other calendar data.
     * @throws GeneralSecurityException This method can throw an GeneralSecurityException.
     * @throws IOException This method can throw an IOException.
     */
    private Calendar getService(final String userKey) throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new Calendar.Builder(httpTransport, JSON_FACTORY, this.getCredentials(userKey))
                .setApplicationName(getConfiguration().getApplicationName())
                .build();
    }

    /**
     * This returns the response of the /event/upcomingEvent client's request.
     * @param userKey This is the login of the user.
     * @return It returns the response of the request.
     * @throws IOException This method can throw an IOException.
     * @throws GeneralSecurityException This method can throw an GeneralSecurityException.
     */
    public final ServiceResponse<Event> getUpcomingEvent(final String userKey) throws IOException, GeneralSecurityException {
        ServiceResponse<Event> response = new ServiceResponse<>();

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = getService(userKey).events().list("primary")
                .setMaxResults(MAX_RESULTS)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        if (events.getItems().isEmpty()) {
            response.setMessage("No upcoming events.");
        } else {
            response.setData(events.getItems().get(0));
        }
        return response;
    }

}
