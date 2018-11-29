package fr.ynov.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.gmail.Gmail;
@Service
/**
 * Permet la récupérations des infos liées au calendar google du compte associé.
 * @author abaracas
 *
 */
public class GoogleCalendarService extends GoogleService {

    private static Logger LOG = LogManager.getLogger();
    /**
     * Constructeur de la classe
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     */
    private GoogleCalendarService() throws GeneralSecurityException, IOException {
	super();
    }
   
    /**
     * Récupère les événements à venir, dans la limite de 20, ou uniquement le prochain selon les paramètres.
     * @param userKey user de l'application
     * @param nbEvents defini à 1 ou 20 selon si on veut le ou les prochains events
     * @return la liste des événements
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    public String getNextEvents(String userKey, Integer nbEvents) throws IOException, GeneralSecurityException {
	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userKey))
		.setApplicationName(maConfig.getApplicationName()).build();
	String message = "";
	// List the next 10 events from the primary calendar.
	DateTime now = new DateTime(System.currentTimeMillis());
	Events events = service.events().list("primary").setMaxResults(nbEvents).setTimeMin(now).setOrderBy("startTime")
		.setSingleEvents(true).execute();
	List<Event> items = events.getItems();
	if (items.isEmpty()) {
	    message = "Aucun événement à venir";
	} else {
	    if (nbEvents == 1) {
		message = "Prochain événement : <br>";
	    }
	    else {
		message = "Evenements à venir : <br>";
	    }	    
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
	LOG.info("utilisateur " + userKey + " => " + message);
	return message;
    }
    
    
    /**
     * Recupère le bon service Gmail.
     * @param userId l'id de l'utilisateur gmail
     * @return le service
     * @throws IOException exception
     * @throws GeneralSecurityException  exception
     */
    public Gmail getService(String userId) throws IOException, GeneralSecurityException {
	Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, super.getCredentials(userId))
		.setApplicationName(maConfig.getApplicationName()).build();
	return service;
    }
}