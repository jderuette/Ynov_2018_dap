package fr.ynov.dap.dap.Services.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import fr.ynov.dap.dap.Config;
import fr.ynov.dap.dap.Models.CustomEvent;

@Service
public class CalendarService extends GoogleService {	
	public CalendarService() {
		super();
	}
	
	/**
	 * 
	 * @param user
	 * @return Return the Calendar services that will provide info from our account
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public Calendar getService(String user) throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

        Calendar serviceCal = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, this.GetCredentials(user))
              //TODO scb by Djer Et la conf injectée ?
                .setApplicationName(Config.APPLICATION_NAME)
                .build();
		return serviceCal;
	}
	
	/**
	 * 
	 * @param user
	 * @return a Custom Event that represent start, end , subject and title
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public CustomEvent getNextEvent(String user) throws IOException, GeneralSecurityException{
		DateTime now = new DateTime(System.currentTimeMillis());
        Events events = getService(user).events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        //TODO scb by Djer Evite cette syntaxe "sans acollade", pas claire, et risque d'erreurs ! 
        if(items.size() == 0) 
            //TODO scb by Djer Evite les multiples return dans une méthode (ici, ca reste "plutot acecptable")
        	return null;
        
        Date start = new Date(items.get(0).getStart().getDateTime().getValue());
        Date end = new Date(items.get(0).getEnd().getDateTime().getValue());
        //TODO scb by Djer Stauts de l'event, pas de l'utilisateur effectuant le requete
        CustomEvent e = new CustomEvent(start, end, items.get(0).getSummary(),items.get(0).getStatus());
		return e;
	}
}
