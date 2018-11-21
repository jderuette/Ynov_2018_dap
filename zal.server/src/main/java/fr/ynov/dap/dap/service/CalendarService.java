package fr.ynov.dap.dap.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.dap.Config;
import fr.ynov.dap.dap.model.CalendarModel;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
//TODO zal by Djer Configuer les "save actions" de ton IDE. Cf mémo Eclipse
import org.springframework.web.bind.annotation.RestController;

/**
 * Service Calendar.
 * @author loic
 */
@Service
public class CalendarService extends GoogleService {
	/**
	 * resultCalendar function.
	 * @param accountName *id of user*
	 * @return CalendarModel
	 * @throws Exception *Exception*
	 */
    public CalendarModel getCalendarEvents(final String accountName) throws Exception {
        // Build a new authorized API client service.
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(
        		httpTransport, getCfg().getJsonFactory(),
        		getCredentials(httpTransport, accountName))
                .setApplicationName(getCfg().getApplicationName())
                .build();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        CalendarModel calendar;
        if (items.isEmpty()) {
            calendar = new CalendarModel("no Data");
        } else {
            System.out.println("Upcoming events");
            Event event = items.get(0);
            calendar = new CalendarModel(
            		event.getSummary(),
            		new Date(event.getStart().getDateTime().getValue()),
            		new Date(event.getEnd().getDateTime().getValue()),
            		event.getStatus());
        }
        return calendar;
    }
}