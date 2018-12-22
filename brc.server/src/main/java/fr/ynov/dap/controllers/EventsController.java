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
  //TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired
	AppUserRepostory appUserRepository;

	/** The outlook service. */
	//TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
	@Autowired
	OutlookService outlookService;

	/** The calendar service. */
	//TODO brc by Djer |POO| Il faut préciser le modifier (public/protected/private) sur tes attributs, sinon par defaut c'est celui de la classe (donc public ici)
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
		//TODO brc by Djer |POO| TU pourias avoir une méthode "from(Event)" pour l'initialsiation (ou un constructeur spécifique) pour éviter de "copier/coller" le code d'intialisation
		msNextEvent.setOrganizer(tempMsNextEvent.getOrganizer().getEmailAddress().getName());
		msNextEvent.setStart(tempMsNextEvent.getStart().getDateTime());
		msNextEvent.setEnd(tempMsNextEvent.getEnd().getDateTime());
		msNextEvent.setSubject(tempMsNextEvent.getSubject());
		
		//TODO brc by Djer |API Google| Gestion de "MON" status sur l'event ?

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

		//TODO brc by Djer |POO| C'est étrange d'avoir un Service qui transforme en "CalendarResponse" et l'autre qui ne le fait pas
		CalendarResponse googleNextEvent = calendarService.getGoogleNextEventFromAccount(currentUser);
		
		Event tempMsNextEvent = outlookService.getNextEventsForAccount(currentUser);
		CalendarResponse msNextEvent = new CalendarResponse();
		msNextEvent.setOrganizer(tempMsNextEvent.getOrganizer().getEmailAddress().getName());
		msNextEvent.setStart(tempMsNextEvent.getStart().getDateTime());
		msNextEvent.setEnd(tempMsNextEvent.getEnd().getDateTime());
		msNextEvent.setSubject(tempMsNextEvent.getSubject());

		if (msNextEvent != null && googleNextEvent != null) {
			if (msNextEvent.getStart().before(googleNextEvent.getStart())) {
			    //TODO brc by Djer |Thymleaf| Evite de mettre un "s" à la "variable" dans ton Modèle, cela confusant dans ta vue
				model.addAttribute("events", msNextEvent);
			} else {
				model.addAttribute("events", googleNextEvent);
			}
		}
		//FIXME brc by Djer |POO| Else ? Si un des 2 est Null, alors le "non null" sera le bon évènnement
		
		model.addAttribute("userKey", userKey);
		return "event";
	}
}