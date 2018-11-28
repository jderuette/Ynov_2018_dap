package fr.ynov.dap.dap.controller;

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
import fr.ynov.dap.dap.data.microsoft.model.OutlookEvent;
import fr.ynov.dap.dap.model.EventModel;
import fr.ynov.dap.dap.service.google.GoogleCalendarService;
import fr.ynov.dap.dap.service.microsoft.OutlookService;

/**
 * The Class EventController.
 */
@RestController
@RequestMapping("/events")
public class EventController {

	/** The calendar service. */
	@Autowired
	private GoogleCalendarService calendarService;

	/** The outlook service. */
	@Autowired
	private OutlookService outlookService;

	/** The app user repository. */
	@Autowired
	private AppUserRepository appUserRepository;

	/**
	 * Gets the next event from all account.
	 *
	 * @param userKey
	 *            the user key
	 * @return the next event from all account
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping("/nextEvent")
	public EventModel getNextEventFromAllAccount(@RequestParam final String userKey) throws Exception {
		AppUser user = appUserRepository.findByUserkey(userKey);

		EventModel event = new EventModel("No Data");
		Event googleNextEvent = calendarService.getNextEvent(user);
		OutlookEvent outlookNextEvent = outlookService.getNextEvent(user);
		Date dateStartOfGoogleEvent = null;
		if (outlookNextEvent != null) {
			event = new EventModel(outlookNextEvent.getSubject(), outlookNextEvent.getStart().getDateTime(),
					outlookNextEvent.getEnd().getDateTime(), outlookNextEvent.getOrganizer());
		}
		if (googleNextEvent != null) {
			dateStartOfGoogleEvent = new Date(googleNextEvent.getStart().getDateTime().getValue());
			if (outlookNextEvent == null || dateStartOfGoogleEvent.before(event.getDateOfStart())) {
				Recipient recipient = new Recipient(new EmailAddress(googleNextEvent.getOrganizer().getDisplayName(),
						googleNextEvent.getOrganizer().getEmail()));
				event = new EventModel(googleNextEvent.getSummary(), dateStartOfGoogleEvent,
						new Date(googleNextEvent.getEnd().getDateTime().getValue()), recipient);
			}
		}
		return event;
	}
}
