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
	
	//FIXME brc by Djer Ce logger n'est pas censé être modifié, un final serait mieux
	//TODO brc by Djer le LogManager.getLogger de Log4J peu être appelé sans paramètre, il va automatique utilisé le nom, qualifié, de la classe.
	// Tu peux laisser le paramètre pour clarifier, car les autres "LogManager" n'ont en général pas cette fonctionnalité.
	private static Logger logger = LogManager.getLogger(CalendarService.class);
	
    /**
     * Result calendar.
     *
     * @param userId the user id
     * @return the calendar response
     * @throws Exception the exception
     */
    public CalendarResponse resultCalendar(final String userId) throws Exception {
		//TODO brc by Djer Eclipse te dit que ce n'est pas utilisé ... A supprimer ? Bug ?
        CalendarResponse appointment = null;
    	
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, cfg.getJSON_FACTORY(), getCredentials(HTTP_TRANSPORT, cfg.CREDENTIALS_FILE_PATH(),userId))
                .setApplicationName(cfg.getAPPLICATION_NAME())
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
            //TODO brc by Djer Contectualise tes logs, un "for user : + userId" est très utile
            logger.info("No upcoming events found.");
            return new CalendarResponse("No upcoming events found.");
        } else {
            //TODO brc by Djer Bien de logger, mais tu devrait en profiter pour mettre des info sur l'event.
            // Et du coups logger, après le traitement
            logger.info("Upcoming events");
            
            Date start = new Date(items.get(0).getStart().getDateTime().getValue());
            Date end =  new Date(items.get(0).getEnd().getDateTime().getValue());
	        String summary = items.get(0).getSummary();
	        String status = items.get(0).getStatus();

	        return new CalendarResponse(summary,start,end,status);
            
        }
    }
}