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

  //TODO baa by Djer |Log4J| Devrait être final (la (pseudo) référence ne sera pas modifiée)
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
     * TODO baa by Djer |JavaDOc| Le nom de ce paramètre n'est plus adapté, et la documentation est fausse !
     * @param userKey user de l'application
     * @param nbEvents defini à 1 ou 20 selon si on veut le ou les prochains events
     * @return la liste des événements
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    public String getNextEvents(String userKey, Integer nbEvents) throws IOException, GeneralSecurityException {
	
	//TODOD baa by Djer |POO| Pourquoi en pas utiliser ta méthode "getService" qui fait exactement les 2 lignes ci-dessous ?
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
		//TODO baa by Djer |API Google| getStart().getDateTime() est null pour les évènnements qui durent "toutes la journée", il faut laors utiliser getStart().getDate()
		DateTime start = event.getStart().getDateTime();
		DateTime end = event.getEnd().getDateTime();
		if (start == null) {
		    start = event.getStart().getDate();
		}
		
		//TODO baa by Djer |API Google| Gestion de "MON" status ?
		message += event.getSummary() + "<br>"+ "Débute le : " + start + "<br>" + "Se termine le : " + end + "<br><br>";
	    }
	}
	//TODO baa by Djer |Rest API| pas de SysOut sur un serveur
	System.out.println(message.replace("<br>", "\n"));
	LOG.info("utilisateur " + userKey + " => " + message);
	//TODO baa by Djer |MVC| Ne formate pas la réponse dans un service, récupère et renvoie les données. C'est au controlelr (via la Vue) ou au client directement de formater les messages
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