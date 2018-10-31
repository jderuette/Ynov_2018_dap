package fr.ynov.dap.dapM2.Services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.mortbay.log.Log;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import fr.ynov.dap.dapM2.Config;

/**
 * The Class CalendarService.
 */
public class CalendarService extends GoogleService {
	
	/**
	 * Instantiates a new calendar service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public CalendarService() throws IOException, GeneralSecurityException {
		super();
	}

	/**
	 * Print events.
	 *
	 * @param user the user
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public void print(String user) throws IOException, GeneralSecurityException {
        List<Event> items = getEvents(user);
        
        if (items.isEmpty()) {
            //TODO thb by Djer Pas de SysOut sur un server. Une LOG ?
            System.out.println("No upcoming events found.");
        } else {
          //TODO thb by Djer Pas de SysOut sur un server. Une LOG ?
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
              //TODO thb by Djer Pas de SysOut sur un server. Une LOG ?
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }
	}
	
	
	/**
	 * Gets the events.
	 *
	 * @param user the user
	 * @return the events
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	//FIXME thb by Djer Pas claire, la liste contiendra au MAX un seul évènnement, autant renvoyer un "Event"
	public List<Event> getEvents(String user) throws IOException, GeneralSecurityException {
		//FIXME thb by Djer Ce message est "confus". Ce n'est PAS comme cela qu'on utilise LOG4J ! 
	    //Là tu utilise "par hasard" le systeme de LOG de Jetty (que tu va bientot supprimer car plus utile !!!!)
	    Log.warn("pdpd");
		cfg = Config.getInstance();
		//TODO thb by Djer Pourquoi journaliser le nom de l'application à chaque récupération d'évènnement ?
		Log.warn(cfg.getAPPLICATION_NAME());
		if (user == null) {
			user = "benjamin.thomas.sso@gmail.com";
		}
		
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, cfg.getJSON_FACTORY(), getCredentials(HTTP_TRANSPORT, user))
                .setApplicationName(cfg.getAPPLICATION_NAME())
                .build();

        //TODO thb by Djer Commentaire faux, le code ne récupère qu'un seul Evennement
        // Evite de mettre des "commentaire de code" et surtout évite qu'ils deviennent faux !
        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        
        return events.getItems();
	}
}
