package fr.ynov.dap.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.AppUserRepostory;
import fr.ynov.dap.google.service.CalendarService;
import fr.ynov.dap.microsoft.models.Event;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.models.CalendarResponse;


/**
 * The Class EventsRestController.
 */
@RestController
public class EventsRestController {

	/** The calendar service. */
  //TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired 
	CalendarService calendarService;
	
	/** The app user repository. */
	//TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired
	AppUserRepostory appUserRepository;
	
	/** The outlook service. */
	//TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired
	OutlookService outlookService;
	
	/**
	 * Gets the google next event.
	 *
	 * @param userKey the user key
	 * @return the calendar response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws Exception the exception
	 */
	@RequestMapping("/google/calendar")
	public CalendarResponse GetGoogleNextEvent (@RequestParam final String userKey) throws IOException, Exception {
		
		AppUser appUser = appUserRepository.findByUserkey(userKey);
		CalendarResponse response = calendarService.getGoogleNextEventFromAccount(appUser);
		
		if(response == null) {
		    //TODO brc by Djer |Log4J| Une petite log ?
			response = new CalendarResponse();
			response.setError("No google account found for userKey : " + userKey);
		}
		return response;
		
	}
	
	/**
	 * Gets the next event.
	 *
	 * @param request the request
	 * @param redirectAttributes the redirect attributes
	 * @param userKey the user key
	 * @return the next event
	 * @throws Exception the exception
	 */
	//TODO brc by Djer |Rest API| Essaye d'être "cohérent" dans tes URLs entre tes différentes APIs
	@RequestMapping("/RestNextEvent")
	public CalendarResponse getNextEvent(HttpServletRequest request, RedirectAttributes redirectAttributes,
			@RequestParam final String userKey) throws Exception {

		AppUser currentUser = appUserRepository.findByUserkey(userKey);

		CalendarResponse googleNextEvent = calendarService.getGoogleNextEventFromAccount(currentUser);
		
		Event tempMsNextEvent = outlookService.getNextEventsForAccount(currentUser);
		CalendarResponse msNextEvent = new CalendarResponse();
		msNextEvent.setOrganizer(tempMsNextEvent.getOrganizer().getEmailAddress().getName());
		msNextEvent.setStart(tempMsNextEvent.getStart().getDateTime());
		msNextEvent.setEnd(tempMsNextEvent.getEnd().getDateTime());
		msNextEvent.setSubject(tempMsNextEvent.getSubject());

		CalendarResponse response = null;
		if (msNextEvent != null && googleNextEvent != null) {

			if (msNextEvent.getStart().before(googleNextEvent.getStart())) {
				response = msNextEvent;
			} else {
				response = googleNextEvent;
			}
		}
		//FIXME brc by Djer |POO| Else ? Si un des 2 est Null, alors le "non null" sera le bon évènnement
		
		return response;
	}
}
