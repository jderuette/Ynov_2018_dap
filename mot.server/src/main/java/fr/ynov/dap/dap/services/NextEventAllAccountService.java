package fr.ynov.dap.dap.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.dap.data.microsoft.EventMicrosoft;
import fr.ynov.dap.dap.services.google.CalendarService;
import fr.ynov.dap.dap.services.microsoft.EventMicrosoftService;
import net.minidev.json.JSONObject;

/**
 * The Class NextEventAllAccountService.
 */
@Service
public class NextEventAllAccountService {

	@Autowired
	private EventMicrosoftService microsoftEvent;

	@Autowired
	private CalendarService googleEvent;

	/**
	 * Gets the next event.
	 *
	 * @param user the user
	 * @return the next event of all Accounts
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public String getNextEvent(String user) throws IOException, GeneralSecurityException {
		String response = "";

		EventMicrosoft nextEventMicrosoft = new EventMicrosoft();
		Event nextEventGoogle = new Event();

		nextEventMicrosoft = microsoftEvent.getNextEvents(user);
		nextEventGoogle = googleEvent.getNextEvent(user);

		if (nextEventMicrosoft == null && nextEventGoogle == null) {
			return response = "No events found";
		}

		if (nextEventMicrosoft != null && nextEventGoogle != null) {
		    //TODO mot by Djer |API Google| Attention "getStart().getDateTime()" est Null pour les évennements qui durent toute la journée, il faut alors utiliser "getStart().getDate()"
			Date dateGoogle = new Date(nextEventGoogle.getStart().getDateTime().getValue());

			//TODO mot by Djer |POO| Tu aurais pu utiliser la méthode "before()" de "Date"
			Integer compareDate = dateGoogle.compareTo(nextEventMicrosoft.getStart().getDateTime());

			if (compareDate > 0) {
				response = getResponseMicrosoft(nextEventMicrosoft);
			} else {
				response = getResponseGoogle(nextEventGoogle);
			}
		} else if (nextEventMicrosoft == null && nextEventGoogle != null) {
			response = getResponseGoogle(nextEventGoogle);
		} else {
			response = getResponseMicrosoft(nextEventMicrosoft);
		}

		return response;
	}

	/**
	 * Gets the response google.
	 *
	 * @param nextEventGoogle the next event google
	 * @return the response for a google Event
	 */
	private String getResponseGoogle(Event nextEventGoogle) {
		JSONObject responseGoogle = new JSONObject();

		//TODO mot by Djer |API Google| Attention contient le status de l'évènnement pas "MON" status
		responseGoogle.put("Status", nextEventGoogle.getStatus());
		responseGoogle.put("Date debut", new Date(nextEventGoogle.getStart().getDateTime().getValue()));
		responseGoogle.put("Date fin", new Date(nextEventGoogle.getEnd().getDateTime().getValue()));
		responseGoogle.put("Sujet ", nextEventGoogle.getSummary());
		responseGoogle.put("Self ", nextEventGoogle.getCreator().getSelf());

		return responseGoogle.toString();
	}

	/**
	 * Gets the response microsoft.
	 *
	 * @param nextEventMicrosoft the next event microsoft
	 * @return the response for a microsoft account
	 */
	private String getResponseMicrosoft(EventMicrosoft nextEventMicrosoft) {
		//TODO mot by Djer |POO| Attention ta representation d'un évènnement "Google" et "Microsoft" sont différentes, cela va te compliquer la vie ! 
	    JSONObject responseMicrosoft = new JSONObject();

		responseMicrosoft.put("Sujet", nextEventMicrosoft.getSubject());
		responseMicrosoft.put("Date debut", nextEventMicrosoft.getStart().getDateTime());
		responseMicrosoft.put("Date fin", nextEventMicrosoft.getEnd().getDateTime());
		responseMicrosoft.put("Organisateur", nextEventMicrosoft.getOrganizer().getEmailAddress().getName());

		return responseMicrosoft.toString();
	}

}
