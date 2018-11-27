package fr.ynov.dap.controllers;

import java.io.IOException;
import java.util.ArrayList;

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

@Controller
public class EventsController {

	@Autowired
	AppUserRepostory appUserRepository;

	@Autowired
	OutlookService outlookService;

	/** The calendar service. */
	@Autowired
	CalendarService calendarService;

	@RequestMapping("/microsoft/nextEvent")
	public String getMsNextEvent(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam final String userKey) throws IOException {

		AppUser currentUser = appUserRepository.findByUserkey(userKey);

		Event event = outlookService.getNextEventsForAccount(currentUser);

		model.addAttribute("userKey", userKey);
		model.addAttribute("events", event);

		return "msEvent";
	}

	@RequestMapping("/nextEvent")
	public String getNextEvent(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam final String userKey) throws Exception {

		AppUser currentUser = appUserRepository.findByUserkey(userKey);

		Event msNextEvent = outlookService.getNextEventsForAccount(currentUser);
		CalendarResponse googleNextEvent = calendarService.getGoogleNextEventFromAccount(currentUser);

		String redirect = "index";
		if (msNextEvent != null && googleNextEvent != null) {

			if (msNextEvent.getStart().getDateTime().before(googleNextEvent.getStart())) {
				model.addAttribute("events", msNextEvent);
				redirect = "msEvent";
			} else {
				model.addAttribute("events", googleNextEvent);
				redirect = "googleEvent";
			}
		}
		model.addAttribute("userKey", userKey);
		return redirect;
	}
}