package fr.ynov.dap.service.google;

import java.io.IOException;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

//TODO gut by Djer Configure le "formatter" de ton IDE !! cf Mémo Eclispe.
//TODO gut by Djer TRaite les remarques des outils de controle qualité !!
@Service
public class CalendarService extends GoogleService{
	
/**
 * 
 * @throws IOException
 */
	public CalendarService() throws IOException {
		getLogger().debug("Initialisation de CalendarService");
		init();
	    //System.out.println(maConfiguration.getApplicationName() + " / " + maConfiguration.getCredentialsFolder() );
	}
	
	/**
	 * Initialisation du service
	 * @param userId
	 * @return
	 * @throws IOException
	 */
	public Calendar getService(String userId) throws IOException {
		getLogger().debug("Récupération du service calendar");
		return new Calendar.Builder(getConfiguration().getHTTP_TRANSPORT(), JSON_FACTORY, getCredentials(userId))
        .setApplicationName(getConfiguration().getApplicationName())
        .build();
	}
	
	/**
	 * 
	 * @param userId
	 * @return List d'évènements
	 * @throws IOException
	 */
	public List<Event> getEvents(final String userId) throws IOException{
		getLogger().debug("Recuperation des 10 premiers evenements");
    	DateTime now = new DateTime(System.currentTimeMillis());
        Events events = getService(userId).events().list("primary")
                .setMaxResults(10)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
       
        return items;
    }


}
