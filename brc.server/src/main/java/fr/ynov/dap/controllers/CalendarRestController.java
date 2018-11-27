package fr.ynov.dap.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.AppUserRepostory;
import fr.ynov.dap.google.service.CalendarService;
import fr.ynov.dap.models.CalendarResponse;

/**
 * The Class CalendarController.
 */
@RestController
public class CalendarRestController {

	/** The calendar service. */
	@Autowired 
	CalendarService calendarService;
	
	@Autowired
	AppUserRepostory appUserRepository;
	
	/**
	 * Gets the last event.
	 *
	 * @param userId the user id
	 * @return the calendar response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/google/calendar")
	public CalendarResponse GetLastEvent (@RequestParam final String userKey) throws IOException, Exception {
		
		AppUser appUser = appUserRepository.findByUserkey(userKey);
		CalendarResponse response = calendarService.getGoogleNextEventFromAccount(appUser);
		
		if(response == null) {
			response = new CalendarResponse();
			response.setError("No google account found for userKey : " + userKey);
		}
		return response;
		
	}
}
