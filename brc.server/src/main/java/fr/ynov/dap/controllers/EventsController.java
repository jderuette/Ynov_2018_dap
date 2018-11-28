package fr.ynov.dap.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.AppUserRepostory;
import fr.ynov.dap.google.service.CalendarService;
import fr.ynov.dap.microsoft.models.*;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.models.CalendarResponse;

// TODO: Auto-generated Javadoc
/**
 * The Class EventsController.
 */
@Controller
public class EventsController {

	/** The app user repository. */
	@Autowired
	AppUserRepostory appUserRepository;

	/** The outlook service. */
	@Autowired
	OutlookService outlookService;

	/** The calendar service. */
	@Autowired
	CalendarService calendarService;

	/**
	 * Gets the ms next event.
	 *
	 * @param model the model
	 * @param request the request
	 * @param redirectAttributes the redirect attributes
	 * @param userKey the user key
	 * @return the ms next event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping("/microsoft/nextEvent")
	public String getMsNextEvent(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam final String userKey) throws IOException {

		AppUser currentUser = appUserRepository.findByUserkey(userKey);

		Event tempMsNextEvent = outlookService.getNextEventsForAccount(currentUser);
		CalendarResponse msNextEvent = new CalendarResponse();
		msNextEvent.setOrganizer(tempMsNextEvent.getOrganizer().getEmailAddress().getName());
		msNextEvent.setStart(tempMsNextEvent.getStart().getDateTime());
		msNextEvent.setEnd(tempMsNextEvent.getEnd().getDateTime());
		msNextEvent.setSubject(tempMsNextEvent.getSubject());

		model.addAttribute("userKey", userKey);
		model.addAttribute("events", msNextEvent);

		return "event";
	}

	
	/**
	 * Gets the next event.
	 *
	 * @param model the model
	 * @param request the request
	 * @param redirectAttributes the redirect attributes
	 * @param userKey the user key
	 * @return the next event
	 * @throws Exception the exception
	 */
	@RequestMapping("/nextEvent")
	public String getNextEvent(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam final String userKey) throws Exception {

		AppUser currentUser = appUserRepository.findByUserkey(userKey);

		CalendarResponse googleNextEvent = calendarService.getGoogleNextEventFromAccount(currentUser);
		
		Event tempMsNextEvent = outlookService.getNextEventsForAccount(currentUser);
		CalendarResponse msNextEvent = new CalendarResponse();
		msNextEvent.setOrganizer(tempMsNextEvent.getOrganizer().getEmailAddress().getName());
		msNextEvent.setStart(tempMsNextEvent.getStart().getDateTime());
		msNextEvent.setEnd(tempMsNextEvent.getEnd().getDateTime());
		msNextEvent.setSubject(tempMsNextEvent.getSubject());

		if (msNextEvent != null && googleNextEvent != null) {
			if (msNextEvent.getStart().before(googleNextEvent.getStart())) {
				model.addAttribute("events", msNextEvent);
			} else {
				model.addAttribute("events", googleNextEvent);
			}
		}
		model.addAttribute("userKey", userKey);
		return "event";
	}
}