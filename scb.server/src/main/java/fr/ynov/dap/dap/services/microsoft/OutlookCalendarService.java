 package fr.ynov.dap.dap.services.microsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.OutlookAccount;
import fr.ynov.dap.dap.models.CustomEvent;
import fr.ynov.dap.dap.models.Event;
import fr.ynov.dap.dap.models.PagedResult;

@Service
public class OutlookCalendarService {
    @Autowired
    AppUserRepository repository;
    
    public CustomEvent GetNextEvent(String userid) {
		AppUser appUser = repository.findByName(userid);
		List<OutlookAccount> otAccounts = appUser.getOutlookAccounts();
		CustomEvent customEvent;
		List<Event> foundEvent = new ArrayList<Event>();

		for(int i=0; i < otAccounts.size(); i++) {
		    OutlookService outlookService = OutlookServiceFactory.getOutlookService(otAccounts.get(i).getIdToken().getAccessToken());
		    try {
		    	 // Sort by start time in descending order
			    String sort = "start/dateTime ASC";
			    // Only return the properties we care about
			    String properties = "organizer,subject,start,end";
			    // Return at most 10 events
			    Integer maxResults = 10;
			    PagedResult<Event> events = outlookService.getEvents(sort, properties, maxResults).execute().body();
			    for(int y = 0; y< events.getValue().length; y++) {
		    		foundEvent.add(events.getValue()[y]);
			    }
		    }catch(IOException e) {
		    	return null;
		    }
		}
		customEvent = new CustomEvent(foundEvent.get(0).getStart().getDateTime(), foundEvent.get(0).getEnd().getDateTime(), foundEvent.get(0).getSubject(), "confirmed");
		Date now = new Date();
		for(int i = 1; i< foundEvent.size(); i++) {
			Event e = foundEvent.get(i);
			
			if(e.getStart().getDateTime().after(now)) {
				return new CustomEvent(e.getStart().getDateTime(), e.getEnd().getDateTime(), e.getSubject(), "confirmed");
			}
		}
	    return customEvent;

    }
}
