package fr.ynov.dap.GoogleMaven;
/*public class CalendarService
{
	public get{
		return this;
	}
}*/

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

@Service
public class CalendarService extends GoogleService {

	//private static Config maConfig = configuration;
    //TODO elj by Djer |L.og4J| Devrait être static. Créer un Logger est couteux, ici Spring fait un Singgleton donc ca va. Sauf nécéssité les Logger sont static final
	private final Logger logger = LogManager.getLogger();
	//private static CalendarService instance;
	//TODO elj by Djer |POO| Attention ton Service ets un Singleton, évite d'avoir des attributs d'états, c'est peigeux (les données peuvent être écrasés entre 2 utilisateurs)
	private DateTime now;
	private List<Event> items;
	/**
	 * @return the instance
	 */
	/*public static CalendarService getInstance() {
		if (instance == null) {
			instance = new CalendarService();
		}
		return instance;
	}*/

	/**
	 * @return the nbEvents
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */

	public int getNbEvents(String userKey) throws IOException, GeneralSecurityException {
	    //TODO elj by Djer |POO| pasa besoin que "items" soient un attributs, une varaible dans la porté de la méthdoe est suffisant. Tu **dois** faire l'appel à chaque fois de toutes façons
		items = NextEvents(userKey);
		return items.size();
	}

	public CalendarService() {
		super();
	}
	

	public Calendar getService(String userKey) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, userKey))
		.setApplicationName(configuration.getApplicationName()).build();

		return service;
	}

	/**
	 * 
	 * @param user
	 * @return next event with google api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	//TODO elj by Djer |POO| Attention au nomage de tes méthodes ! (pas de majuscules au début)
	//TODO elj by Djer |POO| Cette méthode n'est utilisée que par d'autres méthodes de cette classe, elle devrait être privée
	public List<Event> NextEvents(String userKey) throws IOException, GeneralSecurityException {
		//TODO elj by Djer |POO| "now" et "items" devraient etre local à cette méthode
	    now = new DateTime(System.currentTimeMillis());
		Events events = getService(userKey).events().list("primary").setMaxResults(10).setTimeMin(now)
				.setOrderBy("startTime").setSingleEvents(true).execute();
		items = events.getItems();
		return items;
	}
	
	protected String buildRedirectUri(final HttpServletRequest req, final String destination) {
		final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath(destination);
		return url.build();
	}

	/**
	 * 
	 * @param user
	 * @return next event method with google api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public String getNextEvent(final HttpServletRequest request, @RequestParam final String userKey) throws IOException, GeneralSecurityException {
		String msg = null;
		try{
			List<Event> items = NextEvents(userKey);

			if (items.isEmpty()) {
				//System.out.println("Pas d'evenement qui arrive");
				msg = "Pas d'evenement qui arrive";
			} else {
				
				for (Event event : items) {
					DateTime start = event.getStart().getDateTime();
					DateTime end = event.getEnd().getDateTime();
					if (start == null) {
						start = event.getStart().getDate();
					}
					//TODO elj by Djer |API Google| gestion de "mon" status sur l'évennement ? 
					//System.out.println("l'evenement "+ event.getSummary() + " commence le : "+ start+" et se termine le "+ end);
					msg +=  "(" + start + "s)" + event.getSummary() + "(" + end + "s)";

				}

			}
		} catch (IOException e) {
		    //TODO elj by Djer |Log4J| Ajoute le "userKey" comme contexte dan ton message
		    //TODO elj by Djer |log4J| Evite d'ajouter "e.getMessages" dans TON message, ajotuer la cause (l'exception) en deuxième paramètre. Log4J va alors afficher le message de la cause et la pile d'excception
			logger.debug("Erreur détectée pour la récupération des prochains évènements, voici les détails : "+e.getMessage());
		}
		
		//TODO elj by Djer |MVC| Ne format pas tes messages dans le service. Le service renvoie un "model" (des données métiers). C'est la Vue (ou le client de l'API) qui formate les messages. A la limite le controller peut être responsable de produire les messages, mais pas le service metier.
		return msg;	
	}




}
