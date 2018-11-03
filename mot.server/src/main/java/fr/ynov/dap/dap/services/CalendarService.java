package fr.ynov.dap.dap.services;

import java.io.IOException;
import java.security.GeneralSecurityException;


import org.springframework.stereotype.Service;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;


import fr.ynov.dap.dap.Evenement;

//TODO mot by Djer Format ton code source ! (et/ou configure les "save actions" de ton IDE)
/**
 * The Class CalendarService.
 */
@Service
public class CalendarService extends GoogleService {
	
	/**
	 * Instantiates a new calendar service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public CalendarService() throws IOException, GeneralSecurityException {
		super();
	}
	
	/**
	 * Last event.
	 *
	 * @param user the user
	 * @return the evenement
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	//FIXME mot by Djer Pourquoi static ? Et l'IOC ?
	//TODO mot by Djer "last" ?
	public static Evenement lastEvent (final String user)throws IOException, GeneralSecurityException {
		String userEMAIL = user == null ? getDefaultUser() : user;
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, cfg.getJSON_FACTORY(), getCredentials(userEMAIL))
                .setApplicationName(cfg.getAPPLICATION_NAME())
                .build();

        //TODO mot by Djer Ce commentaire est devenu faux !
        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        
        if (events.getItems().size() > 0) {
        	Event items = events.getItems().get(0);
        	//TODO mot by Djer atention status de l'évènnement, pas de l'utilisateur faisant la demande !
        	Evenement lastEvent = new Evenement(items.getStatus(), items.getStart().getDateTime(),items.getEnd().getDateTime(),items.getSummary(),items.getCreator().getSelf());
        	//TODO mot by Djer Evite plusieurs return par méthode
        	return lastEvent;
        }else {
        	Evenement lastEvent = new Evenement();
        	//TODO mot by Djer Evite plusieurs return par méthode
        	return lastEvent;
        }
        //List<String> infosEvent = new List<String>;
        
        
//        if (items.isEmpty()) {
//            System.out.println("No upcoming events found.");
//            } else {
//             System.out.println("Upcoming events");
//             for (Event event : items) {
//            	 //Evenement lastEvent = new Evenement(event.getStatus(), event.getStart().getDateTime(), null, userEMAIL, userEMAIL);
//                 DateTime start = event.getStart().getDateTime();
//                 if (start == null) {
//                     start = event.getStart().getDate();
//                 }
//                 System.out.printf("%s (%s)\n", event.getSummary(), start);
//             }
//         }
        
        
        
        
        
	}
}
