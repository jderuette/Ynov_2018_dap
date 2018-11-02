package fr.ynov.dap;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.gmail.Gmail;

//TODO gut by Djer Configure le "formatter" de ton IDE !! cf Mémo Eclispe.
//TODO gut by Djer TRaite les remarques des outils de controle qualité !!
@Service
public class CalendarService extends GoogleService{
	
/**
 * 
 * @throws IOException
 */
	public CalendarService() throws IOException {
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
