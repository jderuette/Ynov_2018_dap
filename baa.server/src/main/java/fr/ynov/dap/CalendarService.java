package fr.ynov.dap;

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
import com.google.api.services.gmail.Gmail;
@RestController
public class CalendarService extends GoogleService {
//FIXME baa by Djer Séparation Controller/Services ?
    private static CalendarService instanceCalendar;
    /**
     * Constructeur en Singleton de la classe
     * @throws GeneralSecurityException
     * @throws IOException
     */
    private CalendarService() throws GeneralSecurityException, IOException {
	super();
    }
    /**
     * Fonction qui vérifie qu'une instance existe, et si c'est le cas la renvoie.
     * @return l'instance existante, ou créee si il n'y en a pas
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static CalendarService get() throws GeneralSecurityException, IOException {
	if (instanceCalendar == null) {
	    return instanceCalendar = new CalendarService();
	}
	return instanceCalendar;	
    }
    @RequestMapping("/calendar/nexEvent")
    /**
     * Récupère les évènements à venir
     * @param user
     * @return la liste des évènements
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public String getNextEvent(String user) throws IOException, GeneralSecurityException {
	//TODO baa by Djer Attention tu écrase le paramètre de la méthode !!
	user = "me";
	//TODO baa by Djer Déja présent dans le aprent, pourquoi ne créer une version local ?
	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, user))
		//TODO baa by Djer utiliser la Config ??!?
		.setApplicationName("Ynov DAP").build();
	String message = "";
	// List the next 10 events from the primary calendar.
	DateTime now = new DateTime(System.currentTimeMillis());
	Events events = service.events().list("primary").setMaxResults(10).setTimeMin(now).setOrderBy("startTime")
		.setSingleEvents(true).execute();
	List<Event> items = events.getItems();
	if (items.isEmpty()) {
	    message = "Aucun évènement à venir";
	} else {
	    message = "Evenements à venir : <br>";
	    for (Event event : items) {
		DateTime start = event.getStart().getDateTime();
		DateTime end = event.getEnd().getDateTime();
		if (start == null) {
		    start = event.getStart().getDate();
		}
		message += event.getSummary() + "<br>"+ "Débute le : " + start + "<br>" + "Se termine le : " + end + "<br><br>";
	    }
	}
	System.out.println(message.replace("<br>", "\n"));
	return message;
    }
    /**
     * 
     * @param userId
     * @return
     * @throws IOException
     */
    public Gmail getService(String userId) throws IOException {
	Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, super.getCredentials(HTTP_TRANSPORT, userId))
		.setApplicationName(maConfig.getApplicationName()).build();
	return service;
    }
}