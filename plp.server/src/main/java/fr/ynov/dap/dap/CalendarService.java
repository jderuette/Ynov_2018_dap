package fr.ynov.dap.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pierre
 */
@RestController
public class CalendarService extends GoogleService {
    /**
     * Instantiate Logger.
     */
    private static final Logger log = LogManager.getLogger(CalendarService.class);

    /**
     * Get flow in which an end-user authorize the application to access data.
     *
     * @param userId : userKey
     * @return Calendar
     * @throws IOException              : throw exception
     * @throws GeneralSecurityException : throw exception
     */
    private Calendar getService(final String userId) throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (IOException | GeneralSecurityException e) {
            log.error("Error when trying to get Service for user : " + userId ,e);
            throw e;
        }

        return new Calendar.Builder(httpTransport, getJsonFactory(), getCredentials(userId))
                .setApplicationName(getConfig().getApplicationName()).build();
    }

    /**
     * Get the next event in calendar.
     *
     * @param userId : userKey
     * @return Map : accessRole, subject, start, finish
     * @throws IOException              : throw exception
     * @throws GeneralSecurityException : throw exception
     */
    public final Map<String, String> getNextEvent(final String userId) throws IOException, GeneralSecurityException {
        DateTime now = new DateTime(System.currentTimeMillis());
        Events firstEvent = getService(userId).events().list("primary")
                .setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
                .setSingleEvents(true).execute();

        Event itemFirst = firstEvent.getItems().get(0);
        Map<String, String> response = new HashMap<>();
        if (itemFirst.isEmpty()) {
            response.put("subject", "No incomming event");
        } else {
            response.put("accessRole", itemFirst.getStatus());
            response.put("subject", itemFirst.getSummary());
            if (itemFirst.getStart().getDate() == null) {
                response.put("start", itemFirst.getStart().getDateTime().toString());
                response.put("finish", itemFirst.getEnd().getDateTime().toString());
            }
            else {
                response.put("start", itemFirst.getStart().getDate().toString());
                response.put("finish", itemFirst.getEnd().getDate().toString());
            }
        }

        return response;
    }
}
