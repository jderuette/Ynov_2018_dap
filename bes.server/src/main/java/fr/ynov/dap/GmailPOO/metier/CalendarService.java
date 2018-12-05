package fr.ynov.dap.GmailPOO.metier;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.stereotype.Service;
@Service
public class CalendarService extends GoogleService {
   

    //TODO bes by Djer |POO| Dans un service (ou un controller) qui sont pas défaut des Singleton, on évite d'avoir des variables d'instances. Tu risques d'écraser des valeurs entre 2 appels (2 utilisateurs) La pluspart du temps, le traitement sera fait pour un utilisateur différent, il FAUT donc refaire un appel vers Google.
    private   DateTime nowTime;
    //TODO bes by Djer |POO|  Dans un service (ou un controller) qui sont pas défaut des Singleton, on évite d'avoir des variables d'instances.
    private   List<Event> allEvents;


   
    /**
     * @return the nbEvents
     * @throws GeneralSecurityException
     * @throws IOException
     */

    public    int getNbEvents(String user) throws IOException, GeneralSecurityException {
        //TODO bes by Djer |POO| Tu peux utiliser un varaible "local" ici au lieu de ta variable d'instance.
    	allEvents = allEvent( user);
        return allEvents.size();
    }

    //TODO bes by Djer |Spring| Inutile, Srping fait un Singleton par defaut sur les @RestController
    //Deplus get est une méthode d'instance, il te faut donc une instance, pour ... récupérer l'instance
    /**
     * @throws IOException 
     * @throws GeneralSecurityException 
     * @throws UnsupportedOperationException 
   	 * 
   	 */
    
   	public CalendarService() throws UnsupportedOperationException, GeneralSecurityException, IOException {
   		super();
   		// TODO Auto-generated constructor stub
   	}

    public  Calendar getService(String user) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT,user))
                .setApplicationName(configuration.getApplicationName()).build();

        return service;
    }

   

	
    public   List<Event> allEvent(String user) throws IOException, GeneralSecurityException {
        //TODO bes by Djer |POO| Attention to "now" (et ton "item") sont des attributs de la classe, cela est très perturbant. Cré ton "nowTime" en local à cette méthode.
        nowTime = new DateTime(System.currentTimeMillis());
        Events events = getService(user).events().list("primary").setMaxResults(10).setTimeMin(nowTime)
                .setOrderBy("startTime").setSingleEvents(true).execute();
        allEvents = events.getItems();
        
        return allEvents;
    }

   // @RequestMapping("/getNextEvent")
    public String getNextEvent() throws IOException, GeneralSecurityException {

        //TODO bes by Djer |POO| N'utilise pas cet attribut, rapelle "allEvent" pour avoir ta liste d'évènnements. Que tu stocke dans une varaible local à cette fonction, pour la traiter ensuite.
        String msg = null;
        if (allEvents.isEmpty()) {
            msg = "No upcoming events found.";
        } else {
            System.out.println("Upcoming events");
            msg="";
            for (Event event : allEvents) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
               
                msg += " " + event.getSummary() + "(" + start + "s)";
            }
            
        }
        return msg;
    }

   

}
