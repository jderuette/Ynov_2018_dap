package fr.ynov.dap.web;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.CalendarService;

@RestController
public class CalendarController {

	/**
	 * Service permettant de communiquer avec la People API de google
	 */
	@Autowired CalendarService calendarService;
	
	/**
	 * 
	 * @param userId
	 * @return liste des évenements à venir format JSON
	 * @throws IOException
	 */
	 @RequestMapping("/calendar/events")
	 public String getEvents(@RequestParam final String userId) throws IOException{
		 List<Event> events = calendarService.getEvents(userId);
		 String stringEvent = "Pas d'évènements";
		 if (events.isEmpty()) {
	         System.out.println(stringEvent);
	     } else {
	    	 stringEvent = "{events:[";
	    	 int i = 0;
	         for (Event event : events) {
	        	 DateTime eventStart = event.getStart().getDateTime();
	        	 DateTime eventStop = event.getEnd().getDateTime();
	        	 //String responseAttendees = event.getAttendees();
    			 String eventName = event.getSummary();
	             if (eventStart == null) {
	            	 eventStart = event.getStart().getDate();
	             }
	             if (eventStop == null) {
	            	 eventStop = event.getEnd().getDate();
	             }
	         	stringEvent += "{'name':'" + eventName + "', 'start':'" + eventStart + "', 'end':'" + eventStop + "'}";
	         	if(i++ != events.size() - 1){
	         		stringEvent += ",";
	            }
	         }
	         stringEvent += "]}";
	     }		 
		 return stringEvent;
	 }
}
