package fr.ynov.dap.GmailPOO.web;

import java.io.IOException;
import org.springframework.stereotype.Service;




import fr.ynov.Outlook.service.*;

@Service
public class EventsController {

	// @RequestMapping("/events")
	public Event[] events(OutlookService outlookService) throws IOException {
		
		
		// Sort by start time in descending order
		String sort = "start/DateTime DESC";
		// Only return the properties we care about
		String properties = "organizer,subject,start,end";
		// Return at most 10 events
		Integer maxResults = 10;
		
		
			PagedResult<Event> events = outlookService.getEvents(
					sort, properties, maxResults)
					.execute().body();
	
			
     
        return events.getValue();
	}
}