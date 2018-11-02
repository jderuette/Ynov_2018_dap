package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;


/**
 * The Class CalendarService.
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
	 * Gets the service.
	 *
	 * @param userId the user id
	 * @return the service
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Calendar getService(String userId) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, configuration.getJSON_FACTORY(),
				getCredentials(HTTP_TRANSPORT, userId)).setApplicationName(configuration.getAPPLICATION_NAME()).build();
		return service;
	}
	
	/**
	 * Gets the next event.
	 *
	 * @param userId the user id
	 * @return the next event
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public CalendarResponse getNextEvent(String userId)  throws IOException, GeneralSecurityException {
		Calendar service = getService(userId);
		DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        CalendarResponse calendarRes = new CalendarResponse(items.get(0).getStart().toString(),
        		items.get(0).getEnd().toString(), items.get(0).getStatus(), items.get(0).getSummary());
        return calendarRes;
	}
}
