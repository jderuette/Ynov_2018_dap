package fr.ynov.dap.dap.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.CalendarService;
import fr.ynov.dap.dap.models.CalendarResponse;

/**
 * The Class CalendarController.
 */
@RestController
public class CalendarController {

	/** The calendar service. */
	@Autowired 
	CalendarService calendarService;
	
	/**
	 * Gets the last event.
	 *
	 * @param userId the user id
	 * @return the calendar response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/calendar")
	public CalendarResponse GetLastEvent (@RequestParam final String userId) throws IOException, Exception {
		
		return calendarService.resultCalendar(userId);
	}
}
