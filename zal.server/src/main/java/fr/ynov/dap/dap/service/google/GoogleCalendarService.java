package fr.ynov.dap.dap.service.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.google.GoogleAccount;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Service Calendar.
 * 
 * @author loic
 */
@Service
public class GoogleCalendarService extends GoogleService {
	/**
	 * resultCalendar function.
	 * 
	 * @param accountName
	 *            *id of user*
	 * @return CalendarModel
	 * @throws Exception
	 *             *Exception*
	 */
	private Event getCalendarEvent(final String accountName) throws Exception {
		// Build a new authorized API client service.
		Calendar service = getGoogleCalendarClient(accountName);
		// List the next 10 events from the primary calendar.
		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list("primary").setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
				.setSingleEvents(true).execute();
		List<Event> items = events.getItems();
		Event event;
		if (items.isEmpty()) {
			return null;
		} else {
		    //TODO zal by Djer |Rest API| Pas de SysOut sur un serveur ! (une Log si tu as besoin de cette information)
			System.out.println("Upcoming events");
			event = items.get(0);
		}
		return event;
	}

	/**
	 * Gets the next event.
	 *
	 * @param user
	 *            the user
	 * @return the next event
	 */
	public Event getNextEvent(AppUser user) {
		if (user.getGoogleAccounts().size() == 0) {
			return null;
		}

		Event nextEvent = null;
		for (GoogleAccount account : user.getGoogleAccounts()) {
			try {
				Event event = getCalendarEvent(account.getName());
				if (nextEvent != null) {
				    //TODO zal by Djer |API Google| Attention "getDate()" est valorisé pour les évennements qui durent toute la journée. Attention tu compares 2 choses, potentiellement, différentes "getDate()" et "getDateTime()" 
					Date dateOfEvent = new Date(event.getStart().getDate().getValue());
					Date dateOfNextEvent = new Date(nextEvent.getStart().getDateTime().getValue());
					if (dateOfEvent.before(dateOfNextEvent)) {
						nextEvent = event;
					}
				} else {
					nextEvent = event;
				}
				//TODO zal by Djer |API Google| Gestion de "MON" status ?

			} catch (Exception e) {
			    //TODO zal by Djer |Log4J| "e.printStackTrace()" affiche directement dans la console. Utilise une Log
				e.printStackTrace();
			}
		}
		return nextEvent;
	}

	/**
	 * Gets the google calendar client.
	 *
	 * @param accountName
	 *            the account name
	 * @return the google calendar client
	 * @throws GeneralSecurityException
	 *             the general security exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	//TODO zal by Djer |POO| "final" sur un **méthode** empèche les "classes filles" de surcharger la méthode (hors c'est déja impossible, cette méthode est privée). Je pense que tu voulais mettre le final sur le **paramètre** pour indiquer que la méthode ne pourra PAS changer la (pseudo)référence de ce paramètre.
	private final Calendar getGoogleCalendarClient(final String accountName)
			throws GeneralSecurityException, IOException {
		final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		return new Calendar.Builder(httpTransport, this.jsonFactory, getCredentials(httpTransport, accountName))
				.setApplicationName(getCfg().getApplicationName()).build();
	}
}