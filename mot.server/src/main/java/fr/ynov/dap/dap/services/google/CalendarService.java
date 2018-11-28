package fr.ynov.dap.dap.services.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.dap.CompareEvent;
import fr.ynov.dap.dap.data.google.AppUser;
import fr.ynov.dap.dap.data.google.GoogleAccount;
import fr.ynov.dap.dap.data.interfaces.AppUserRepository;

/**
 * The Class CalendarService.
 */
@Service
public class CalendarService extends GoogleService {

	@Autowired
	AppUserRepository appUserRepository;

	protected Logger LOG = LogManager.getLogger(CalendarService.class);

	/**
	 * Instantiates a new calendar service.
	 *
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public CalendarService() throws IOException, GeneralSecurityException {
		super();
	}

	/**
	 * Last event.
	 *
	 * @param user the user
	 * @return the evenement
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public Event getEvent(String user) throws IOException, GeneralSecurityException {
		// Event response = null ;
		NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, user))
				.setApplicationName(getCfg().getApplicationName()).build();

		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list("primary").setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
				.setSingleEvents(true).execute();

		Integer lastEventId = events.getItems().size() - 1;

		if (events.getItems().isEmpty()) {
			LOG.info("No events found");
			return null;
		}

		if (lastEventId < 0) {
			lastEventId = 0;
		}

		Event event = events.getItems().get(lastEventId);
		return event;
	}

	/**
	 * Gets the next event.
	 *
	 * @param user the user
	 * @return the next event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Event getNextEvent(String user) throws IOException {
		Event nextEvent = null;
		Event response = new Event();
		AppUser appUser = appUserRepository.findByUserKey(user);
		ArrayList<Event> events = new ArrayList<>();

		if (appUser == null) {
			LOG.error("No user found");
			throw new IOException();
		}

		for (GoogleAccount g : appUser.getAccounts()) {
			try {
				nextEvent = getEvent(g.getName());

				if (nextEvent != null) {
					events.add(nextEvent);
				}

			} catch (IOException | GeneralSecurityException e) {
				LOG.error("Error when getNextEvent() ", e);
			}
		}
		if (events.size() == 0) {
			LOG.error("No events founds");
			response = null;
		} else {
			Collections.sort(events, new CompareEvent());
			response = events.get(0);
		}

		return response;
	}
}
