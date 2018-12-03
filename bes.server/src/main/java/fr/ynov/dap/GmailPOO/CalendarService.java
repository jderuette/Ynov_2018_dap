package fr.ynov.dap.GmailPOO;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//FIXME bes by Djer |POO| Semble être du "vieux code" suppprime le ! (semble être repris dans fr.ynov.dap.GmailPOO.metier.CalendarService). Deplus tu as deux classe avec le même nom, Spring va s'emmeler les pinceaux avec ca ! 
//@RestController
//TODO bes by Djer |MVC| Séparation Controller/Service ?
public class CalendarService extends GoogleService {
    //TODO bes by Djer |Spring| Inutile, Srping fait un Singleton par defaut sur les @RestController
    @Autowired
    private static CalendarService instance;

    //TODO bes by Djer |POO| Dans un service (ou un controller) qui sont pas défaut des Singleton, on évite d'avoir des variables d'instances. Tu risques d'écraser des valeurs entre 2 appels (2 utilisateurs) La pluspart du temps, le traitement sera fait pour un utilisateur différent, il FAUT donc refaire un appel vers Google.
    @Autowired
    private static DateTime now;
    @Autowired
    private static List<Event> items;
    @Autowired
    private static int nbEvents;

    /**
     * @return the instance
     */
    //TODO bes by Djer |Spring| Inutile, Srping fait un Singleton par defaut sur les @RestController
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

    //TODO bes by Djer |Spring| Inutile, Srping fait un Singleton par defaut sur les @RestController. Deplus get est une méthode d'instance, il te faut donc une instance, pour ... récupérer l'instance
    public CalendarService get() {
        return this;
    }

    public static Calendar getService() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(configuration.getApplicationName()).build();

        return service;
    }

    //TODO bes by Djer |POO| Pourquoi static ?
    private static List<Event> allEvent() throws IOException, GeneralSecurityException {
        //TODO bes by Djer |POO|  Attention to "now" (et ton "item") sont des attributs de la classe, cela est très perturbant.
        now = new DateTime(System.currentTimeMillis());
        Events events = CalendarService.getService().events().list("primary").setMaxResults(10).setTimeMin(now)
                .setOrderBy("startTime").setSingleEvents(true).execute();
        items = events.getItems();
        return items;
    }

    @RequestMapping("/getNextEvent")
    public String getNextEvent() throws IOException, GeneralSecurityException {

        //TODO bes by Djer |POO| N'utilise pas cet attribut, rapelle "allEvent" pour avoir ta liste d'évènnements. Que tu stocke dans une varaible local à cette fonction, pour la traiter ensuite.
        String msg = null;
        if (items.isEmpty()) {
            msg = "No upcoming events found.";
        } else {
            System.out.println("Upcoming events");
            //TODO bes by Djer |SOA| Pourquoi un double affichage ?
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
                //TODO bes by Djer |POO| Attention ton "msg" ne contiendra que les infos du dernier évènnement (il faut que tu concatene)
                msg = " " + event.getSummary() + "(" + start + "s)";
            }
        }
        return msg;
    }

    /*
     * public static void main(String... args) throws IOException,
     * GeneralSecurityException { // Build a new authorized API client service.
     * 
     * 
     * // List the next 10 events from the primary calendar. DateTime now = new
     * DateTime(System.currentTimeMillis()); Events events =
     * service.events().list("primary") .setMaxResults(10) .setTimeMin(now)
     * .setOrderBy("startTime") .setSingleEvents(true) .execute(); List<Event> items
     * = events.getItems(); if (items.isEmpty()) {
     * System.out.println("No upcoming events found."); } else {
     * System.out.println("Upcoming events"); for (Event event : items) { DateTime
     * start = event.getStart().getDateTime(); if (start == null) { start =
     * event.getStart().getDate(); } System.out.printf("%s (%s)\n",
     * event.getSummary(), start); } } // Refer to the Java quickstart on how to
     * setup the environment: //
     * https://developers.google.com/calendar/quickstart/java // Change the scope to
     * CalendarScopes.CALENDAR and delete any stored // credentials.
     * 
     * Event event = new Event() .setSummary("Google I/O 2015")
     * .setLocation("800 Howard St., San Francisco, CA 94103")
     * .setDescription("A chance to hear more about Google's developer products.");
     * 
     * DateTime startDateTime = new DateTime("2015-05-28T09:00:00-07:00");
     * EventDateTime start = new EventDateTime() .setDateTime(startDateTime)
     * .setTimeZone("America/Los_Angeles"); event.setStart(start);
     * 
     * DateTime endDateTime = new DateTime("2015-05-28T17:00:00-07:00");
     * EventDateTime end = new EventDateTime() .setDateTime(endDateTime)
     * .setTimeZone("America/Los_Angeles"); event.setEnd(end);
     * 
     * String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
     * event.setRecurrence(Arrays.asList(recurrence));
     * 
     * EventAttendee[] attendees = new EventAttendee[] { new
     * EventAttendee().setEmail("lpage@example.com"), new
     * EventAttendee().setEmail("sbrin@example.com"), };
     * event.setAttendees(Arrays.asList(attendees));
     * 
     * EventReminder[] reminderOverrides = new EventReminder[] { new
     * EventReminder().setMethod("email").setMinutes(24 * 60), new
     * EventReminder().setMethod("popup").setMinutes(10), }; Event.Reminders
     * reminders = new Event.Reminders() .setUseDefault(false)
     * .setOverrides(Arrays.asList(reminderOverrides));
     * event.setReminders(reminders);
     * 
     * String calendarId = "primary"; event = service.events().insert(calendarId,
     * event).execute(); System.out.printf("Event created: %s\n",
     * event.getHtmlLink());
     * 
     * }
     */

}
