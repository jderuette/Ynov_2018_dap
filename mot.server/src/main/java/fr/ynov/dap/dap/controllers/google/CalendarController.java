package fr.ynov.dap.dap.controllers.google;

import java.io.IOException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.dap.services.google.CalendarService;
import net.minidev.json.JSONObject;

import org.apache.logging.log4j.Logger;

/**
 * The Class CalendarController.
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController {

	@Autowired
	private CalendarService calendarService;

	protected Logger LOG = LogManager.getLogger(CalendarController.class);

	/**
	 * Next event.
	 *
	 * @param userKey the user key
	 * @return the Next Event Google
	 */
	//TODO mot by Djer |Spring| "@ResponseBody" n'est pas utile, par defaut les méthodes "mappées" d'un **Rest**Controller produisent un Body
	//TODO mot by DJer |Spring| le "required = true" est la valeur par defaut, tu n'es pas obligé de le préciser
	@RequestMapping("/nextEventGoogle")
	public @ResponseBody String nextEvent(@RequestParam(value = "userKey", required = true) String userKey) {
		Event lastEvent = null;
		JSONObject response = new JSONObject();

		try {
			if (calendarService.getNextEvent(userKey) != null) {
				lastEvent = calendarService.getNextEvent(userKey);
				response.put("Status", lastEvent.getStatus());
				response.put("Date debut", new Date(lastEvent.getStart().getDateTime().getValue()));
				response.put("Date fin", new Date(lastEvent.getEnd().getDateTime().getValue()));
				response.put("Sujet ", lastEvent.getSummary());
				response.put("Self ", lastEvent.getCreator().getSelf());
			}else {
				response.put("Next Event : ","No event found");
			}
			
		} catch (IOException e) {
		    //TODO mot by Djer |log4J| Contextualise tes messages (" for userkey : " + userKey)
			LOG.error("Error during getNextEvent google", e);
		}

		return response.toString();
	}
}
