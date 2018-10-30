package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import fr.ynov.dap.Config;

/**
 * The Class CalendarService.
 */
@RestController
public class CalendarService {
	//FIXME cha by Djer Séparation COntroller/Service ?
	
	/** The Constant INSTANCE. */
	//TODO cha by Djer eclipse indique que cette constante n'est pas utilisée, pouquoi ne pas traiter ?
	private static final CalendarService INSTANCE = new CalendarService();
	
	/**
	 * Instantiates a new calendar service.
	 */
	public CalendarService(){
		
	}
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Calendar getService() throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, GoogleService.getJsonFactory(), GoogleService.getCredentials(HTTP_TRANSPORT))
                //TODO cha by Djer utiliser le principe "ZeroConf" plutot qu'une Conf "en dur"
        		.setApplicationName(Config.APPLICATION_NAME)
                .build();
		return service;
	}
	
	/**
	 * Gets the nex event.
	 *
	 * @return the nex event
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	@RequestMapping(value="/event")
	public String getNexEvent() throws IOException, GeneralSecurityException {
		Calendar service = getService();
		String nextEvent = null;
		DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
        	//TODO cha by Djer Sysout sur Server inutiles ! 
            System.out.println("No upcoming events found.");
        } else {
        	//TODO cha by Djer Sysout sur Server inutiles ! 
            System.out.println("Upcoming events");
            for (Event event : items) {
            	
            	String label = event.getSummary();
                DateTime start = event.getStart().getDateTime();
                DateTime end = event.getEnd().getDateTime();
                //TODO getStatus renvoie le status de l'EVENT, pas de la personne qui "fait l'appel" !
                String status = event.getStatus();
                
                if (start == null) {
                	start = event.getStart().getDate();
                }
                
                nextEvent = "Le prochain " + label + " commencera " + start +
                		" et finira " + end + " Ma réponse :" + status;
            }
        }
        
        return nextEvent;
	}
	
}
