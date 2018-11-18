package com.ynov.dap.google;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.ynov.dap.data.AppUser;
import com.ynov.dap.data.GoogleAccount;
import com.ynov.dap.models.CalendarModel;
import com.ynov.dap.repository.AppUserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for calendar.
 * @author POL
 */
@Service
public class CalendarService extends GoogleService {

    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Instantiates a new calendar service.
     */
    public CalendarService() {
        super();
    }

    public Event resultCalendar(final String userId) throws Exception {
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
            getLogger().info("No upcoming events found for user : " + userId);
            return null;
        } else {
        	getLogger().info("Next upcoming events for user : " + userId);

            Event nextEvent = items.get(0);
                        
            return nextEvent;
        }
    }


    public CalendarModel finalReturn(final String userId) throws Exception {
        AppUser appUser = appUserRepository.findByName(userId);
        List<GoogleAccount> accounts = appUser.getGoogleAccounts();
        List<Event> events = new ArrayList<Event>();
        
        for (GoogleAccount account : accounts) {
        	events.add(resultCalendar(account.getName()));
        }
        
        System.out.println(events);
        
        Event finalEvent = new Event();
        
        if (events.size() == 1) {
        	
        }
        
		for(int i = 0; i < events.size(); i++) {
			Event event = events.get(i);
			if (new Date(event.getStart().getDateTime().getValue()).before(new Date(finalEvent.getStart().getDateTime().getValue()))) {
				finalEvent = event;
			}
		}

		return new CalendarModel(finalEvent.getSummary(), new Date(finalEvent.getStart().getDateTime().getValue()),
                new Date(finalEvent.getEnd().getDateTime().getValue()), finalEvent.getStatus());
    }

    @Override
    public String getClassName() {
        return CalendarService.class.getName();
    }
}
