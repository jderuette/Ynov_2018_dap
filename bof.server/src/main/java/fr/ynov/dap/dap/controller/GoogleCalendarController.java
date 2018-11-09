package fr.ynov.dap.dap.controller;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.model.EmptyData;
import fr.ynov.dap.dap.model.EventModel;
import fr.ynov.dap.dap.model.GoogleAccountModel;
import fr.ynov.dap.dap.model.MailModel;
import fr.ynov.dap.dap.model.MasterModel;
import fr.ynov.dap.dap.repository.AppUserRepository;
import fr.ynov.dap.dap.repository.GoogleAccountRepository;
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
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private GoogleAccountRepository googleAccountRepository;
	
	
	/**
	 * 
	 * @param userID Id of the user to access data
	 * @return The request response
	 * @throws Exception
	 * Map the path /upcoming to the associated service method
	 */
	@RequestMapping(value="/upcoming")
	public MasterModel getNextEvent(@RequestParam final String userKey) throws Exception {
		
		List<EventModel> events = new ArrayList<>();
		
		if(null != appUserRepository.findByUserKey(userKey)) {
			for (GoogleAccountModel googleAccount : appUserRepository.findByUserKey(userKey).getGoogleAccounts()) {
				try {
				events.add((EventModel) calendarService.getUpcomingEvent(googleAccount.getAccountName()));
				}catch (ClassCastException clCastException) {
					
				}
			}
		}
		MasterModel nextEvent = new EmptyData("No Event found");
		if(events.size() > 0) {
			nextEvent = events.get(0);
			for (EventModel eventModel : events) {
				if(((EventModel) nextEvent).getStartDate().compareTo(eventModel.getStartDate()) >= 0){
					nextEvent = eventModel;
				}
			}			
		}
		
		return nextEvent;
	}
}
