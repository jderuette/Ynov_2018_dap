package fr.ynov.dap.controllers.google;

import java.util.Date;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.services.google.CalendarService;
import fr.ynov.dap.services.microsoft.EventService;
import fr.ynov.dap.utils.ExtendsUtils;
import fr.ynov.dap.utils.JSONResponse;


/**
 * The Class CalendarController.
 */
@RestController
@RequestMapping("/calendar")
public class CalendarController extends ExtendsUtils {
	
	/** The google service. */
	@Autowired
    private CalendarService googleService;
	
	/** The microsoft service. */
	@Autowired
	EventService microsoftService;

	/**
	 * Index.
	 *
	 * @param user the user
	 * @return the string
	 */
	@RequestMapping("/{user}")
    public @ResponseBody String index(@PathVariable String user)  {
		JSONObject event = new JSONObject(googleService.getNextEventFromAll(user));
		return JSONResponse.responseString("event", event.toString());
    }
	
	/**
	 * Next event.
	 *
	 * @param userKey the user key
	 * @return the string
	 */
	@RequestMapping("/next")
    public @ResponseBody String nextEvent(@RequestParam(value = "userKey", required = true) String userKey)  {
		Event nextGoogleEvent = googleService.getNextEventFromAll(userKey);
		fr.ynov.dap.data.Event nextMicrosoftEvent = microsoftService.getNextEvent(userKey);
		
		JSONObject googleEventToJSON = new JSONObject(nextGoogleEvent);
		JSONObject microsoftEventToJSON = new JSONObject(nextMicrosoftEvent);
		
		if(nextGoogleEvent != null && nextMicrosoftEvent != null) {
			Date googleDate = new Date(nextGoogleEvent.getStart().getDateTime().getValue());
			Date microsoftDate = new Date(nextMicrosoftEvent.getStart().getDateTime().getTime());
			
			if (googleDate.compareTo(microsoftDate) > 0) {
				return JSONResponse.responseString("event", googleEventToJSON.toString());
			} else {
				return JSONResponse.responseString("event", microsoftEventToJSON.toString());
			}	
		} else {
			if (nextGoogleEvent == null && nextMicrosoftEvent != null) {
				return JSONResponse.responseString("event", microsoftEventToJSON.toString());
			} else if (nextGoogleEvent != null && nextMicrosoftEvent == null){
				return JSONResponse.responseString("event", googleEventToJSON.toString());
			} else {
				return JSONResponse.responseString("event", "empty");
			}
		}
    }
}