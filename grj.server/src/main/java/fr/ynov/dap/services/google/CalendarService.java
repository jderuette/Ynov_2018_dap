package fr.ynov.dap.services.google;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.*;
import fr.ynov.dap.helpers.GoogleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * CalendarService
 */
@Service
public class CalendarService {

    /**
     * Autowired GoogleHelper
     */
    @Autowired
    private GoogleHelper googleHelper;

    /**
     * Return HashMap with the next event
     *
     * @param userKey userKey to log
     * @return HashMap
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    public final Map<String, String> getNextEvent(String userKey) throws GeneralSecurityException, IOException {

        DateTime now = new DateTime(System.currentTimeMillis());

        Events nextEvents = googleHelper.getCalendarService(userKey).events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        Map<String, String> nextEventResponse = new HashMap<>();

        if (nextEvents.getItems().size() > 0) {
            Event nextEvent = nextEvents.getItems().get(0);
            nextEventResponse.put("name", nextEvent.getSummary());
            nextEventResponse.put("start_date", nextEvent.getStart().getDate().toString());
            nextEventResponse.put("end_date", nextEvent.getEnd().getDate().toString());
            nextEventResponse.put("status", nextEvent.getStatus());
        } else {
            nextEventResponse.put("message", "No upcoming events found.");
        }

        return nextEventResponse;
    }
}
