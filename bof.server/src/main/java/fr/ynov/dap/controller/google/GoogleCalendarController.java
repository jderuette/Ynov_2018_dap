package fr.ynov.dap.controller.google;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.model.EmptyData;
import fr.ynov.dap.model.EventModel;
import fr.ynov.dap.model.MailModel;
import fr.ynov.dap.model.MasterModel;
import fr.ynov.dap.model.Google.GoogleAccountModel;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.repository.google.GoogleAccountRepository;
import fr.ynov.dap.service.Google.GoogleCalendarService;

/**
 * 
 * @author Florent
 * Handle all the request of the calendar service
 */
@RestController
@RequestMapping(value="/google/calendar")
public class GoogleCalendarController {

	/**
	 * Automatically injected attribute by the Autowired annotation
	 */
	@Autowired
	private GoogleCalendarService calendarService;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	//TODO bof by Djer |IDE| Ton IDE t'indique que ce n'est pas utilisé, Bug ? A supprimer ? 
	private GoogleAccountRepository googleAccountRepository;
	
	
	/**
	 * 
	 * @param userID Id of the user to access data
	 * @return The request response
	 * @throws Exception
	 * Map the path /upcoming to the associated service method
	 */
	@RequestMapping(value="/upcoming")
	public EventModel getNextEvent(@RequestParam final String userKey) throws Exception {
		
		List<EventModel> events = new ArrayList<>();
		
		if(null != appUserRepository.findByUserKey(userKey)) {
			for (GoogleAccountModel googleAccount : appUserRepository.findByUserKey(userKey).getGoogleAccounts()) {
				try {
				events.add((EventModel) calendarService.getUpcomingEvent(googleAccount.getAccountName()));
				}catch (ClassCastException clCastException) {
					//TODO bof by Djer |POO| Evite les catch vide. A minima met une Log, ou un commentaire indiquant que tu "étouffe" volontairement cette exception
				}
			}
		}
		EventModel nextEvent = null;
		if(events.size() > 0) {
			nextEvent = events.get(0);
			for (EventModel eventModel : events) {
			    //TODO bof by Djer |IDE| Formate ton code (ou fait-le faire par ton IDE via les "save actions")
				if(((EventModel) nextEvent).getStartDate().compareTo(eventModel.getStartDate()) >= 0 && ((EventModel) nextEvent).getStartDate().compareTo(new Date()) > 0){
					nextEvent = eventModel;
				}
			}			
		}
		
		return nextEvent;
	}
}
