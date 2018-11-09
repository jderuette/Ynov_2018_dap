package fr.ynov.dap.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import fr.ynov.dap.dap.models.CalendarResponse;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * The Class CalendarService.
 */
@Service
public class CalendarService extends GoogleService {

	private CalendarService(){
		super();
	}
	
	private final static Logger logger = LogManager.getLogger(CalendarService.class);
	
    /**
     * Result calendar.
     *
     * @param userId the user id
     * @return the calendar response
     * @throws Exception the exception
     */
    public CalendarResponse resultCalendar(final String accountName) throws Exception {
    	
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, jsonFactory, getCredentials(HTTP_TRANSPORT, cfg.getCredentialsFilePath(),accountName))
                .setApplicationName(cfg.getApplicationName())
                .build();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            logger.info("No upcoming events found. for user : " + accountName);
            return new CalendarResponse("No upcoming events found. for user : " + accountName);
        } else {
            logger.info("Upcoming events");
            
            Date start = new Date(items.get(0).getStart().getDateTime().getValue());
            Date end =  new Date(items.get(0).getEnd().getDateTime().getValue());
	        String summary = items.get(0).getSummary();
	        String status = items.get(0).getStatus();
	        
	        logger.info("start : " + start);
	        logger.info("end : " + end);
	        logger.info("summary : " + summary);
	        logger.info("status : " + status);
	        
	        return new CalendarResponse(summary,start,end,status);   
        }
    }
}