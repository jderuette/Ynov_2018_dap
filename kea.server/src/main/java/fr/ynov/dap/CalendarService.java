package fr.ynov.dap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.gmail.Gmail;

@RestController
//FIXME séparation Controller/Service ??
public class CalendarService extends GoogleService {
    private static Calendar calendarService = null;
    //TODO kea by Djer Pourquoi cette variable ? Une variable local dans la méthode serait suffisante !
    public static List<Event> listeEvenements = null;

    /**
     * Get all dependencies needed to create the service.
     * @throws IOException nothing special
     * @throws InstantiationException nothing special
     * @throws IllegalAccessException nothing special
     */
    public CalendarService() throws IOException, InstantiationException, IllegalAccessException {
        init();
        //TODO dea by Djer Inutile, Spring s'en occupe via le @autowire. Faire un set du get n'est pas très pertinet.
        setCustomConfig(getCustomConfig());
    }

    /**
     * Creates a new service if it has never been created
     * else it returns the service instantiated previously.
     * @return the Calendar service
     * @throws IOException nothing special
     */
    public Calendar getService() throws IOException {
        //TODO kea by Djer L'utilisateur "me" est un identifiant chez Google.
        // Il faut utiliser un ID "interne" (userKey) pour pouvir gérer du "multi-compte".
        if (calendarService == null) {
            calendarService = new Calendar.Builder(getCustomConfig().getHttpTransport(), JSON_FACTORY,
                    getCredentials(user)).setApplicationName(getCustomConfig().getApplicationName()).build();
        }
        return calendarService;
    }

    /**
     * Uses the Calendar Service to get the 2
     * next events and the date of beginning.
     * @return an event list
     * @throws IOException nothing special
     */
    //TODO kea by Djer Utilisé uniquement dans cette classe, devrait être privé !
    public List<Event> get2nextEvents() throws IOException {
        calendarService = getService();
        //TODO kea by Djer Pourquoi copier la liste "de Google" dans une liste local (static en plus !!) ? 
        // Tu peux renvoyer la liste de Google.
        listeEvenements = new ArrayList<Event>();
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = calendarService.events().list("primary").setMaxResults(2).setTimeMin(now)
                .setOrderBy("startTime").setSingleEvents(true).execute();
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            logger.info("Aucun evenements trouves");
        } else {
            //System.out.println("Evènements à venir :");
            for (Event event : items) {
                listeEvenements.add(event);
            }
        }
        return listeEvenements;
    }

    /**
     * Uses data from CalendarAPI and adds some text to it.
     * @param evenement the event from googleCalendar Service
     * @return formatted event in string
     */
    public static String eventToString(final Event evenement) {
        DateTime eventDateTime = evenement.getStart().getDateTime();
        String eventName = evenement.getSummary();
        if (eventDateTime == null) {
            eventDateTime = evenement.getStart().getDate();
        }
        String stringEvent = "nom évènement:\n" + eventName + " : " + eventDateTime;
        return stringEvent;
    }

    /**
     * Concatenate all events in the list in one string to display in view.
     * @return string that contains all events in the list
     * @throws IOException nothing special
     */
    @RequestMapping("/events/nextEvents")
    public String eventsToString() throws IOException {
        //TODO kea by Djer pourquoi mettre dans la variable le retour de la méthode,
        // alors que la méthode valorise cette attribut ?
        // Cette attributs est en plus static
        listeEvenements = get2nextEvents();
        String stringRes = "Prochains évènements :<br><br>";
        for (Event event : listeEvenements) {
            stringRes = stringRes + "<br><br>" + eventToString(event);
        }
        if (stringRes.equals("")) {
            stringRes = "Aucun évènements à venir !";
        }
        return stringRes;
    }
}
