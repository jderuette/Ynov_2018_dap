package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.service.CalendarService;



//TODO phd by Djer Une classe ne "return" jamais rien, se sont les méthodes qui "return".
// Une classe "décrit et "implemente" un comportement
/**
 * 
 * @author Dom
 *This class return the next Event with a string param "userId" in format Map<String,Object>
 */
@RestController
public class CalendarController {
	/**
	 * Return the unique instance of calendarService with annotation Autowired
	 */
	@Autowired
	CalendarService calendarService;
	
	/**
	 * This function return the next event with string param userId according to the annotated route
	 * @param userId
	 * @return
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	 @RequestMapping("/getEventUncomming")
	public Map<String,Object> getEvent(@RequestParam("userId") final String userId) throws GeneralSecurityException, IOException {
		
			return calendarService.getNextEvent(userId);

	}

}
