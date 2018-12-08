package fr.ynov.dap.dap.controller.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import Utils.LoggerUtils;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.service.google.CalendarService;
import fr.ynov.dap.dap.service.microsoft.MicrosoftEventService;

/**
 * The Class CalendarController.
 */
@Controller
@RequestMapping("/calendar")
public class CalendarController extends LoggerUtils {

	@Autowired
	public AppUserRepository repo;

	@Autowired
	private CalendarService calendarService;

	@Autowired
	private MicrosoftEventService microsoftEventService;

	/**
	 * Gets the last event.
	 *
	 * @param user the user
	 * @return the last event
	 */
	@RequestMapping("/nextEvent")
	public String getNextEvent(@RequestParam("userKey") String user,Model model) {

		Event eventGoogle = calendarService.getNextEvent(user);
		fr.ynov.dap.dap.service.microsoft.ObjectUtils.Event eventMicrosoft = microsoftEventService.getNextEvent(user);

		JSONObject returnedJSON = new JSONObject();
		if (eventGoogle == null && eventMicrosoft == null) {
		    //TODO brs by Djer |Log4J| Ajoute du contexte à ts messages ("for userKey : " + user).
		  //TODO brs by Djer |Log4J| pas vraiment uen erreur, on a le droit de ne pas avoir d'évènnement à venir. Un level Info serait suffisant
			LOG.error("There is not event");
			return null;
		} else {

			JSONObject jsonMicrosoft = new JSONObject();
			JSONObject jsonGoogle = new JSONObject();

			if (eventGoogle == null && eventMicrosoft != null) {

				jsonMicrosoft.put("start", eventMicrosoft.getStart().getDateTime().toString());
				jsonMicrosoft.put("end", eventMicrosoft.getEnd().getDateTime().toString());
				jsonMicrosoft.put("subject", eventMicrosoft.getSubject().toString());
				jsonMicrosoft.put("organiser", eventMicrosoft.getOrganizer().getEmailAddress().getName().toString());
				returnedJSON = jsonMicrosoft;
				//TODO brs by Djer |Log4J| Contextualise tes messages
				LOG.info(jsonMicrosoft);
			} else if (eventGoogle != null && eventMicrosoft == null) {

				Date start = new Date(eventGoogle.getStart().getDateTime().getValue());
				Date end = new Date(eventGoogle.getEnd().getDateTime().getValue());
				jsonGoogle.put("start",start.toString() );
				jsonGoogle.put("end", end.toString());
				jsonGoogle.put("subject", eventGoogle.getSummary());
				jsonGoogle.put("organiser", eventGoogle.getOrganizer().toString());
				returnedJSON = jsonGoogle;
				
			} else {
				Date googleDate = new Date(eventGoogle.getStart().getDateTime().getValue());

				Date microsoftDate = new Date(eventMicrosoft.getStart().getDateTime().getTime());
				if (googleDate.compareTo(microsoftDate) > 0) {
					returnedJSON = jsonGoogle;
				} else {
					//LOG.info(jsonMicrosoft);
					returnedJSON = jsonMicrosoft;
					//TODO brs by Djer |Log4J| Contextualise tes messages
					LOG.info(jsonMicrosoft);
				}
			}

		}
		//TODO brs by Djer |Log4J| Contextualise tes messages ("next Event start at : " returnedJSON.getString("start"))
		LOG.info(returnedJSON.getString("start"));
		model.addAttribute("content", "event");
		
		model.addAttribute("start",returnedJSON.getString("start"));
		model.addAttribute("end",returnedJSON.getString("end"));
		model.addAttribute("subject",returnedJSON.getString("subject"));
		model.addAttribute("organiser",returnedJSON.getString("organiser"));
		
		return "index";
	}

}
