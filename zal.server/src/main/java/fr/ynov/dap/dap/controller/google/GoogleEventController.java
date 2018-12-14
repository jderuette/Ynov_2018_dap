package fr.ynov.dap.dap.controller.google;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.services.calendar.model.Event;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.microsoft.Recipient;
import fr.ynov.dap.dap.data.microsoft.model.EmailAddress;
import fr.ynov.dap.dap.model.EventModel;
import fr.ynov.dap.dap.service.google.GoogleCalendarService;

/**
 * The Class GoogleEventController.
 */
@RestController
@RequestMapping("/google/events")
public class GoogleEventController {

	/** The calendar service. */
	@Autowired
	private GoogleCalendarService calendarService;

	/** The app user repository. */
	@Autowired
	private AppUserRepository appUserRepository;

	/**
	 * Gets the next event from google.
	 *
	 * @param userKey
	 *            the user key
	 * @return the next event from google
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping("/nextEvent")
	public EventModel getNextEventFromGoogle(@RequestParam final String userKey) throws Exception {
		AppUser user = appUserRepository.findByUserkey(userKey);
		EventModel event = new EventModel("No Data");
		Event googleNextEvent = calendarService.getNextEvent(user);
		if (googleNextEvent != null) {
			Recipient recipient = new Recipient(new EmailAddress(googleNextEvent.getOrganizer().getDisplayName(),
					googleNextEvent.getOrganizer().getEmail()));
			event = new EventModel(googleNextEvent.getSummary(),
			        //TODO zal by Djer |API Google| "getDateTime()" est null pour les évènnements qui durent toute la journée, il faut utiliser getDate() pour ceux-là
					new Date(googleNextEvent.getStart().getDateTime().getValue()),
					new Date(googleNextEvent.getEnd().getDateTime().getValue()), recipient);
		}
		return event;
	}
}
