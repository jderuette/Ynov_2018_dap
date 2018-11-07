package com.ynov.dap.google;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.ynov.dap.models.CalendarModel;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Service for calendar.
 * @author POL
 */
@Service
public class CalendarService extends GoogleService {

    /**
     * Instantiates a new calendar service.
     */
    public CalendarService() {
        super();
    }

    /**
     * Result calendar.
     *
     * @param userId the user id
     * @return the calendar model
     * @throws Exception the exception
     */
    public CalendarModel resultCalendar(final String userId) throws Exception {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(userId))
                .setApplicationName(env.getProperty("application_name"))
                .build();

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        if (items.isEmpty() || items.get(0) == null) {
            getLogger().error("No upcoming events found for user : " + userId);
            return null;
        } else {
        	System.out.println(items.isEmpty());
        	System.out.println(items.get(0));
        	getLogger().error("Next upcoming events for user : " + userId);

            Event nextEvent = items.get(0);
            CalendarModel calendar = new CalendarModel(
                    nextEvent.getSummary(), nextEvent.getStart().getDateTime().toString(),
                    nextEvent.getEnd().getDateTime().toString(), nextEvent.getStatus());

            return calendar;
        }
    }

    @Override
    public String getClassName() {
        return CalendarService.class.getName();
    }
}
