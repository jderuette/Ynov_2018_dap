package fr.ynov.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.gmail.Gmail;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;


@Service

public class CalendarService extends GoogleService {

	private static CalendarService gCalendarService;

	private CalendarService() throws GeneralSecurityException, IOException {
		super();

	}

	public Gmail getService(String userId) throws IOException, GeneralSecurityException {
		Gmail service = new Gmail.Builder(http_transport, JSON_FACTORY, super.getCredentials(http_transport, userId))
				.setApplicationName(configuration.getApplicationName()).build();
		return service;
	}

	/**
	 * Fonction qui prend l'utilisateur et renvoi les prochains évenements
	 * 
	 * @param user
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */

	public String getNextEvents(String userKey) throws IOException, GeneralSecurityException {

		String prochainEvent = null;

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, userKey))
				.setApplicationName(configuration.getApplicationName()).build();

		// List the next 10 events from the primary calendar.
		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list("primary").setMaxResults(10).setTimeMin(now).setOrderBy("startTime")
				.setSingleEvents(true).execute();
		List<Event> items = events.getItems();
		if (items.isEmpty()) {
			prochainEvent = null;

		} else {

			for (Event event : items) {
				DateTime start = event.getStart().getDateTime();
				if (start == null) {
					start = event.getStart().getDate();
				}
				prochainEvent = (event.getSummary() + start);

			}
		}
		return prochainEvent;
	}
}
