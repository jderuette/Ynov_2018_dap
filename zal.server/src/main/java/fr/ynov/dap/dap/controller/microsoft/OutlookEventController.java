package fr.ynov.dap.dap.controller.microsoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.microsoft.model.OutlookEvent;
import fr.ynov.dap.dap.model.EventModel;
import fr.ynov.dap.dap.service.microsoft.OutlookService;

/**
 * The Class OutlookEventController.
 */
@RestController
@RequestMapping("/outlook/events")
public class OutlookEventController {

	/** The outlook service. */
	@Autowired
	private OutlookService outlookService;

	/** The app user repository. */
	@Autowired
	private AppUserRepository appUserRepository;

	/**
	 * Gets the next event from outlook.
	 *
	 * @param userKey
	 *            the user key
	 * @return the next event from outlook
	 * @throws Exception
	 *             the exception
	 */
	@RequestMapping("/nextEvent")
	public EventModel getNextEventFromOutlook(@RequestParam final String userKey) throws Exception {
		AppUser user = appUserRepository.findByUserkey(userKey);
		EventModel event = new EventModel("No Data");
		OutlookEvent outlookNextEvent = outlookService.getNextEvent(user);

		if (outlookNextEvent != null) {
			event = new EventModel(outlookNextEvent.getSubject(), outlookNextEvent.getStart().getDateTime(),
					outlookNextEvent.getEnd().getDateTime(), outlookNextEvent.getOrganizer());
		}
		return event;
	}
}
