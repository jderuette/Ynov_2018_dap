package com.ynov.dap.service.google;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.ynov.dap.domain.AppUser;
import com.ynov.dap.domain.google.GoogleAccount;
import com.ynov.dap.model.CalendarModel;
import com.ynov.dap.repository.AppUserRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleCalendarService extends GoogleService {

    @Autowired
    private AppUserRepository appUserRepository;

    private Event getEvent(final String account) throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, getCredentials(account))
                .setApplicationName(getConfig().getApplicationName())
                .build();

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();

        if (items.isEmpty() || items.get(0) == null) {
            getLogger().info("No upcoming events found for google account : " + account);
            return null;
        } else {
        	getLogger().info("Next upcoming events for google account : " + account);

            Event nextEvent = items.get(0);

            return nextEvent;
        }
    }

    public CalendarModel getNextEvent(final String userKey) throws GeneralSecurityException, IOException {
        AppUser appUser = appUserRepository.findByName(userKey);
        
		if (appUser == null) {
			getLogger().error("userKey '" + userKey + "' not found");
			return new CalendarModel();
		}
        
        List<GoogleAccount> accounts = appUser.getGoogleAccounts();
        List<Event> events = new ArrayList<Event>();

        for (GoogleAccount account : accounts) {
        	events.add(getEvent(account.getName()));
        }

        Event finalEvent = null;

        if (events.isEmpty() || events.get(0) == null) {
            getLogger().info("No upcoming events found for userKey : " + userKey);
            return new CalendarModel();
        } else if (events.size() == 1) {
        	Event event = events.get(0);
        	
        	getLogger().info("A next upcoming events was found for userKey : " + userKey);
        	
    		return new CalendarModel(event.getSummary(), new Date(event.getStart().getDateTime().getValue()),
                    new Date(event.getEnd().getDateTime().getValue()), event.getStatus());
        } else {
    		for (int i = 0; i < events.size(); i++) {
    			Event event = events.get(i);
    			if (new Date(event.getStart().getDateTime().getValue()).before(new Date(finalEvent.getStart().getDateTime().getValue()))) {
    				finalEvent = event;
    			}
    		}
    		
    		if (finalEvent == null) {
                getLogger().info("No upcoming events found for userKey : " + userKey);
    			return new CalendarModel();
    		}
    		
        	getLogger().info("A next upcoming events was found for userKey : " + userKey);
    		
    		return new CalendarModel(finalEvent.getSummary(), new Date(finalEvent.getStart().getDateTime().getValue()),
                    new Date(finalEvent.getEnd().getDateTime().getValue()), finalEvent.getStatus());
        }
    }

    @Override
    public String getClassName() {
        return GoogleCalendarService.class.getName();
    }
}
