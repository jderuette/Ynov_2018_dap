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

    //TODO jog by Djer |IDE| Ton IDE t'indique que ce n'est pas utilisé. Cela doit être du "vieux" code de l'époque ou vous aviez codé le Singleton (maintennt Spring le fait tout seul grace au @Service)
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

		//TODO jog by Djer |POO| Pourquoi ne pas utiliser ta méthode "getService" qui fait exactement le code ci-dessous ?
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

		    //TODO job by Djer |API Google| Tu itère sur les 10 events, et tu écrase "prochainEvent", tu en garde donc que je dernier de la liste (ce n'est donc pas le prochain, mais le "10 ème à partir de "now""). Tu peux faire un "maxResult" de "1" lors de l'appel et te faciliter la vie et avoir un résultat juste.
			for (Event event : items) {
				DateTime start = event.getStart().getDateTime();
				if (start == null) {
					start = event.getStart().getDate();
				}
				prochainEvent = (event.getSummary() + start);

			}
		}
		//TODO jog by Djer |API Google| Afficher "mon status" sur l'évènnement ? 
		
		return prochainEvent;
	}
}
