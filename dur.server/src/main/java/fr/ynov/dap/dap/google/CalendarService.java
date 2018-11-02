package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.gmail.GmailScopes;

import fr.ynov.dap.dap.Config;
import fr.ynov.dap.dap.model.EventModel;

// TODO: Auto-generated Javadoc
/**
 * The Class CalendarService.
 */
@Service
public class CalendarService extends GoogleService {
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Calendar getService() throws GeneralSecurityException, IOException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        //TODO dur by Djer Toutes les fille de "GoogleService" en ont besoin. Tu pourrias rendre accessible celle du parent
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

        //TODO dur by Djer Pas de version multi comptes ?
        Calendar serviceCal = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, this.GetCredentials("user"))
                .setApplicationName(Config.APPLICATION_NAME)
                .build();
		return serviceCal;
	}
	
	/**
	 * Gets the next event.
	 *
	 * @return the next event
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public EventModel getNextEvent() throws IOException, GeneralSecurityException{
		DateTime now = new DateTime(System.currentTimeMillis());
        Events events = getService().events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        if(items.size() == 0) {
        	return null;
        } else {
        	/* Oui j'aurais pu faire un constructeur */
    		EventModel nextEvent = new EventModel();
    		nextEvent.setTitle(items.get(0).getSummary());
    		//TODO dur by Djer Attention status de l'Event, pas de la personne faisant la demande
    		nextEvent.setStatus(items.get(0).getStatus());
    		nextEvent.setStartDate(items.get(0).getStart().getDateTime().getValue());
    		nextEvent.setEndDate(items.get(0).getEnd().getDateTime().getValue());
    		return nextEvent;
        }
	}
}
