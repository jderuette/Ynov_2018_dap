package fr.ynov.dap.dapM2.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.dapM2.Services.CalendarService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
@RequestMapping("/calendar")
public class CalendarController {

	/**
	 * Events.
	 *
	 * @param user the user
	 * @return the string
	 */
	@RequestMapping("/events/{user}")
    public @ResponseBody String events(@PathParam("user") String user) {
    	try {
    	    //FIXME thb by Djer "new Service" ? et l'IOC ???
			List<Event> events = new CalendarService().getEvents(user);
			
			Integer lastEventId = events.size() - 1;
			//TODO thb by Djer Format ton code !! Ce style de "if" est très peu lisible ! 
			// Pas besoin d'économiser des caractères en Java le code est COMPILE !
			if (events.isEmpty()) { return eventsIsEmpty().toString(); } 
			//FIXME thb by Djer Pourquoi mettre à jour une variable que tu n'utilise PAS ensuite ?
			if (lastEventId < 0)  { lastEventId = 0; }
			
			
			Event e = events.get(events.size() - 1);
			
			//TODO thb by Djer Bonne idée, mais tu aurait pu renvoyer l'Event Google netier et Laisser Spring transformer en JSON.
			// Chaque client aurait put allor "piocher" les informations dont il a besoin.
			// Ton approche permet d'éviter d'exposer le "modèle métier de Google" ce qui est bien, mais pas nécéssaire ici.
			JSONObject response = new JSONObject();
			response.put("creator", e.getCreator());
			response.put("summary", e.getSummary());
			response.put("start", e.getStart());
			response.put("end", e.getEnd());
			response.put("status", e.getStatus());
			
			return response.toString();
		} catch (IOException e) {
			return e.getMessage();
		} catch (GeneralSecurityException e) {
			return e.getMessage();
		}
    }
    
    /**
     * Events is empty.
     *
     * @return the JSON object
     */
    private JSONObject eventsIsEmpty() {
    	JSONObject response = new JSONObject();
    	response.put("error", "they are no events");
    	return response;
    }

}
