package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;

/**
 * The Class CalendarService.
 */
@Service
public class CalendarService extends GoogleService {

    @Override
    protected final String getClassName() {

        return CalendarService.class.getName();
    }

	/**
	 * Gets the service.
	 *
	 * @return the service
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    public Calendar getService(final String userKey) throws GeneralSecurityException, IOException {

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, this.GetCredentials(userKey))
                .setApplicationName(config.getApplicationName())
                .build();
        return service;
	}
	
	/**
     * Gets the next google event.
     *
     * @return the next event
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    public Event getNextEvent(final String userKey) throws IOException, GeneralSecurityException {

        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = getService(userKey).events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if(items.size() == 0) {
            LOGGER.info("No upcoming events found for user : " + userKey);
        	return null;
        } else {
            return items.get(0);
        }
	}

    public Event getNextEvent(final AppUser user) throws IOException, GeneralSecurityException {

        List<GoogleAccount> gAccounts = user.getGoogleAccounts();
        List<Event> events = new ArrayList<Event>();

        for (GoogleAccount gAccount : gAccounts) {

            String name = gAccount.getAccountName();
            Event event = getNextEvent(name);
            if (event != null) {
                events.add(event);
            }
        }

        if (events.isEmpty()) {
            LOGGER.info("No upcoming events found for this user");
            return null;
        }

        Event nextEvent = events.get(0);

        for (Event event : events) {
            if (nextEvent.getStart().getDateTime().getValue() > event.getStart().getDateTime().getValue()) {
                nextEvent = event;
            }
        }

        return nextEvent;
    }
}
