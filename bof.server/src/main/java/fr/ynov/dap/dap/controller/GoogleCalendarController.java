package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.model.EventModel;
import fr.ynov.dap.dap.model.MasterModel;
import fr.ynov.dap.dap.service.CalendarService;

/**
 * 
 * @author Florent
 * Handle all the request of the calendar service
 */
@RestController
@RequestMapping(value="/calendar")
public class GoogleCalendarController {

	/**
	 * Automatically injected attribute by the Autowired annotation
	 */
	@Autowired
	private CalendarService calendarService;
	
	/**
	 * 
	 * @param userID Id of the user to access data
	 * @return The request response
	 * @throws Exception
	 * Map the path /upcoming to the associated service method
	 */
	@RequestMapping(value="/upcoming")
	public MasterModel getNextEvent(@RequestParam final String userID) throws Exception {
		return calendarService.getUpcomingEvent(userID);
	}
}
