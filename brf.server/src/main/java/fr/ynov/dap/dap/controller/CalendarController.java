package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.util.List;

import javax.security.auth.callback.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.dap.service.CalendarService;
import fr.ynov.dap.dap.service.GoogleService;

/**
 * 
 * @author Florian BRANCHEREAU
 *
 */
@RestController
public class CalendarController extends GoogleService implements Callback{
	
	@Autowired
	private CalendarService calendarservice;
	
	public CalendarController() throws Exception, IOException
	{
	}
	
	/**
	 * 
	 * @param userKey
	 * @return Le prochain évènement
	 * @throws IOException
	 * @throws Exception
	 */
	@RequestMapping("/calendarNextEvent")
	public String GetNextEvent(@RequestParam("userKey") final String userKey) throws IOException, Exception
	{
		String response = "";
		List<Event> nextevent = calendarservice.GetNextEvents(userKey);
		for (Event event : nextevent) {
			response += "Sujet du prochain évenement : " + event.getSummary() + " - Date de début : " + event.getStart();
			response += " - Date de fin : " + event.getEnd() + " - Etat : " + event.getStatus();
		}
		return response;
	}
}
