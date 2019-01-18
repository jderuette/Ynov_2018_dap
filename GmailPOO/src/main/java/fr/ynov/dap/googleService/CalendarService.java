package fr.ynov.dap.googleService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

@Service
public class CalendarService extends GoogleService {

    /**
     * @return the nbEvents
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public int getNbEvents(final String user) throws IOException, GeneralSecurityException {

        List<Event> allEvents;
        allEvents = allEvent(user);
        return allEvents.size();
    }

    /**
     * @throws IOException 
     * @throws GeneralSecurityException 
     * @throws UnsupportedOperationException 
     * 
     */

    public CalendarService() throws UnsupportedOperationException, GeneralSecurityException, IOException {
        super();

    }

    //TODO bes by Djer |POO| "private" sera suffisant
    public Calendar getService(String user) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, user))
                .setApplicationName(configuration.getApplicationName()).build();

        return service;
    }

    public List<Event> allEvent(String user) throws IOException, GeneralSecurityException {
        DateTime nowTime = new DateTime(System.currentTimeMillis());
        Events events = getService(user).events().list("primary").setMaxResults(10).setTimeMin(nowTime)
                .setOrderBy("startTime").setSingleEvents(true).execute();
        List<Event> allEvents;

        allEvents = events.getItems();

        return allEvents;
    }

    // @RequestMapping("/getNextEvent")
    public String getNextEvent(String user) throws IOException, GeneralSecurityException {
        List<Event> allEvents = allEvent(user);

        String msg = null;
        if (allEvents.isEmpty()) {
            msg = "No upcoming events found.";
        } else {
            //TODO bes by Djer |API| pas de sysout sur un serveur
            System.out.println("Upcoming events");
            msg = "";
            for (Event event : allEvents) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }

                //TODO bes by Djer |SOA| Ne formate pas de "message utilisateur" dans un service "metier", renvoie la donnée est laisse l'appelant (un controller ou RestController) décider de l'affichage (en général il le "delguera" à la Vue ou au Client)
                msg += " " + event.getSummary() + "(" + start + "s)";
            }

        }
        return msg;
    }

}
