package fr.ynov.dap.dap.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.Calendar.Events;

import fr.ynov.dap.dap.CalendarService;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepostory;
import fr.ynov.dap.dap.models.CalendarResponse;
import fr.ynov.dap.dap.models.GmailResponse;

/**
 * The Class CalendarController.
 */
@RestController
public class CalendarController {

	/** The calendar service. */
	@Autowired 
	CalendarService calendarService;
	
	@Autowired
	AppUserRepostory appUserRepository;
	
	/** The logger. */
	private final static Logger logger = LogManager.getLogger(CalendarController.class);	
	
	/**
	 * Gets the last event.
	 *
	 * @param userId the user id
	 * @return the calendar response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/calendar")
	public CalendarResponse GetLastEvent (@RequestParam final String userKey) throws IOException, Exception {
		
		
		AppUser appUser = appUserRepository.findByUserkey(userKey);
		List<CalendarResponse> events = new ArrayList<CalendarResponse>();
		logger.info("appuser : " + appUser.getUserKey());
		
		if(appUser != null) {
			for(int i=0; i < appUser.getGoogleAccounts().size(); i++) {
				String accountName = appUser.getGoogleAccounts().get(i).getName();
				logger.info("accountName : " + accountName);

				if(accountName != null) {
					CalendarResponse response = calendarService.resultCalendar(accountName);
					events.add(response);
				}
			}
		}
		
		CalendarResponse output = null;
		if(events.get(0) != null) {
			output = events.get(0);
			for(int i=0; i<events.size(); i++) {
				if(events.size()>1) {	
					if(events.get(i).getStart().before(output.getStart())) {
						output = events.get(i);
					}
				}	
			}
		}
		return output;
		
	}
}
