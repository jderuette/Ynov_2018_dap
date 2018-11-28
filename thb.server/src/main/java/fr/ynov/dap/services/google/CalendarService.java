package fr.ynov.dap.services.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.interfaces.AppUserRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class CalendarService.
 */
@Service
public class CalendarService extends GoogleService {

	/** The app user repo. */
	@Autowired
	AppUserRepository appUserRepo;

	/**
	 * Gets the event.
	 *
	 * @param user the user
	 * @return the event
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public Event getEvent(String user) throws IOException, GeneralSecurityException {
		NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar calendarService = new Calendar.Builder(HTTP_TRANSPORT, super.getJSON_FACTORY(),
				super.getCredentials(HTTP_TRANSPORT, user)).setApplicationName(getConfig().getApplicationName())
						.build();

		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = calendarService.events().list("primary").setMaxResults(1).setTimeMin(now)
				.setOrderBy("startTime").setSingleEvents(true).execute();

		Integer lastEventId = events.getItems().size() - 1;

		if (events.getItems().isEmpty()) {
			Log.info("Not events found");
			return null;
		}

		if (lastEventId < 0) {
			lastEventId = 0;
		}

		Event e = events.getItems().get(lastEventId);

		return e;
	}

	/**
	 * Gets the next event from all.
	 *
	 * @param user the user
	 * @return the next event from all
	 */
	public Event getNextEventFromAll(String user) {
		AppUser appU = appUserRepo.findByUserKey(user);

		if (appU == null) {
			return null;
		}

		Event lastEvent = null;
		Integer count = 0;
		for (GoogleAccount g : appU.getGoogleAccounts()) {
			try {
				if (count > 0) {
					Long lastDateTimestamp = lastEvent.getStart().getDateTime().getValue();
					Date lastDate = new Date(lastDateTimestamp);

					Long newDateTimestamp = getEvent(g.getName()).getStart().getDateTime().getValue();
					Date newDate = new Date(newDateTimestamp);

					if (lastDate.compareTo(newDate) > 0) {
						lastEvent = getEvent(g.getName());
					}
				} else {
					lastEvent = getEvent(g.getName());
				}

				count++;
			} catch (IOException | GeneralSecurityException e) {
				LOG.error("Error counting events for '" + g.getName() + "' account.", e);
			}
		}

		return lastEvent;
	}
}
