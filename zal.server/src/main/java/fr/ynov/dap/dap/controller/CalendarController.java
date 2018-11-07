package fr.ynov.dap.dap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.dap.model.CalendarModel;
import fr.ynov.dap.dap.service.CalendarService;

/**
 * The Class CalendarController.
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController {

	/** The calendar service. */
	@Autowired
	CalendarService calendarService;

	/**
	 * Gets the mail inbox unread.
	 *
	 * @param userId the user id
	 * @return the mail inbox unread
	 * @throws Exception the exception
	 */
	@RequestMapping(value= "/next/{userId}")
	public CalendarModel getMailInboxUnread(@PathVariable final String userId) throws Exception {
		return calendarService.getCalendarEvents(userId);
	}
}
