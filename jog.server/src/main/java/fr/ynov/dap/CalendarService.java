package fr.ynov.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.gmail.Gmail;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
//TODO jog by Djer devrait etre un dans un sous package "web"
//TODO jog by Djer séparation Controller/service
public class CalendarService extends GoogleService {

	private static CalendarService gCalendarService;
	
	private CalendarService() throws GeneralSecurityException, IOException {
		super();
		
	}
	
	//TODO jog by Djer Maintenant que c'est un "@RestController" Spring en fait un Singleton par defaut pour toi.
	public static CalendarService get() throws GeneralSecurityException, IOException {
		if (gCalendarService == null) {
			return gCalendarService = new CalendarService();
		}
		return gCalendarService;
	}
	
	public Gmail getService(String userId) throws IOException, GeneralSecurityException {
	    Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY,
	                super.getCredentials(HTTP_TRANSPORT, userId)).setApplicationName(configuration.getApplication_name()).build();
	    return service;
	    }
	

	/**
	 * Fonction qui prend l'utilisateur et renvoi les prochains évenements
	 * @param user
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@RequestMapping("calendar")
		public String getNextEvents(String user) throws IOException, GeneralSecurityException {
		//TODO jog by Djer ATtention tu écrase le user passé en paramètre !!
	    user ="me";
		String prochainEvent;

		    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		    Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, user))
		        .setApplicationName(configuration.getApplication_name()).build();

		    // List the next 10 events from the primary calendar.
		    DateTime now = new DateTime(System.currentTimeMillis());
		    //TODO jog by Djer Affiche les 10 prochains événnements, et pas LE suivant.
		    Events events = service.events().list("primary").setMaxResults(10).setTimeMin(now).setOrderBy("startTime")
		        .setSingleEvents(true).execute();
		    List<Event> items = events.getItems();
		    if (items.isEmpty()) {
		    	prochainEvent = "il n'y a pas d'évenements";
		    	//TODO jog by Djer PAS de Sysout sur un serveur ! (à la limite un LOG)
		        System.out.println("No upcoming events found.");
		    } else {
		    	prochainEvent = "Voici le prochain évenement: \n" ;
		        
		        for (Event event : items) {
		        DateTime start = event.getStart().getDateTime();
		        if (start == null) {
		            start = event.getStart().getDate();
		        }
		        prochainEvent+=(event.getSummary()+" \n l'évenement commence le : " + start + "\n");
		      //TODO jog by Djer PAS de Sysout sur un serveur ! (à la limite un LOG)
		        System.out.println(prochainEvent);
		        }
		    }
		    
		    //TODO jog by Djer Renvoie de préférence un Objet et laisse l'appelant décider de l'affichage.
		    // Comme on est dans une API Rest, renvoyer du JSON n'est pas génant, c'est même plutot attendu.
			return prochainEvent;
		    }
	    }

