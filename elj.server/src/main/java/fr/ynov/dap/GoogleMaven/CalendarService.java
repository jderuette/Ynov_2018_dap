package fr.ynov.dap.GoogleMaven;
/*public class CalendarService
{
	public get{
		return this;
	}
}*/

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalendarService extends GoogleService {

    //private static Config maConfig = configuration;
    //TOOD elj by Djer Ne met pas "" comem catégorie, maisse Log4J la déterminer.
    private static final Logger logger = LogManager.getLogger("");
    private static CalendarService instance;
    private static DateTime now;
    private static List<Event> items;

    /**
     * @return the instance
     */
    public static CalendarService getInstance() {
        if (instance == null) {
            instance = new CalendarService();
        }
        return instance;
    }

    /**
     * @return the nbEvents
     * @throws GeneralSecurityException
     * @throws IOException
     */

    public static int getNbEvents() throws IOException, GeneralSecurityException {
        items = allEvent();
        return items.size();
    }

    //TODO elj by Djer Ceci inutile, tu as une méthdoe d'instance (donc l'appelant connait déja l'instance)
    // et une méthode "get" qui renvoie l'instance déja connu de l'appelant....
    public CalendarService get() {
        return this;
    }

    public static Calendar getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(configuration.getApplicationName()).build();

        return service;
    }

    //TODO elj by Djer Pourquoi static ?
    private static List<Event> allEvent() throws IOException, GeneralSecurityException {
        now = new DateTime(System.currentTimeMillis());
        //TODO elj by Djer Ta méthode s'appel "all" mais n'en revoie que 10.... Il faut que tu utilises la pagination si tu les veux tous.
        // Ou renomer ta méthode
        Events events = CalendarService.getService().events().list("primary").setMaxResults(10).setTimeMin(now)
                .setOrderBy("startTime").setSingleEvents(true).execute();
        items = events.getItems();
        return items;
    }

    @RequestMapping("/NextEvents")
    public String getNextEvent() throws IOException, GeneralSecurityException {
        String msg = null;
        try {
            List<Event> items = allEvent();

            if (items.isEmpty()) {
                msg = "No upcoming events found.";
            } else {
                //TODO elj by Djer Pas de sysout sur un Server ! Un LOG si besoin.
                System.out.println("Upcoming events");
                System.out.println("Upcoming events");
                for (Event event : items) {
                    DateTime start = event.getStart().getDateTime();
                    DateTime end = event.getEnd().getDateTime();
                    if (start == null) {
                        start = event.getStart().getDate();
                    }
                    //TODO elj by Djer Pas de sysout sur un Server ! Une LOG si besoin.
                    System.out.println("l'evenement " + event.getSummary() + " commence le : " + start
                            + " et se termine le " + end);
                    msg = " " + event.getSummary() + "(" + start + "s)";

                }

            }
        } catch (IOException e) {
            //TODO elj by Djer Evite de logger juste le message de l'exxeption. En premie paramètre met ton propre message.
            // Puis utilise le secon, pour indiquer la cause.
            //TODO elj by Djer Pourquoi info ?
            logger.info(e.getMessage());
        }
        //TODO elj by Djer Evite de renvoyer une chaine de texte. on crée une API reste, tu peux renvoyer du JSON 
        // (donc renvoyer direcrement un Objet, Spring va s'occuper de le transformer en JSON).
        // Et laisser le client décider du format/langue d'affichage
        return msg;
    }

}
