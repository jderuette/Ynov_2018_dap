package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.dap.service.CalendarService;

/**
 * The Class CalendarController.
 */
@RestController
//TODO brs by Djer Evite les "get" dans les URL des API "Rest", c'est ambigue avec le verb "GET" de HTTP
@RequestMapping("/getCalendar")
public class CalendarController {

	/**
	 * Gets the last event.
	 *
	 * @param user the user
	 * @return the last event
	 */
    //TODO brs by Djer ton URL devra être "GetLastEvent/userKey=xxx".
    //En "Path" pas besoin de mettre de prefixe.
    //Les varaibles après le ? sont des RequestParam, et la il faut bien préciser clef=valeur 
    // (mais pas besoin de le précisier dans le template d'URL de spring)
	@RequestMapping("GetLastEvent/userKey={user}")
	//TODO brs by Djer "last" serait le dernier exixtant, là c'est le NEXT qu'on renvoie
	public @ResponseBody String getLastEvent(@PathParam("user") String user) {

		List<Event> event = null;

		try {
		    //TODO brs by Djer IOC ?
			event = CalendarService.getNextEvent(user);
			JSONObject json = new JSONObject();
			for (Event e : event) {

				JSONArray EventArray = new JSONArray();
				JSONObject item = new JSONObject();
				item.put("summary", e.getSummary());
				item.put("date début", e.getStart().getDateTime());
				item.put("date de fin", e.getEnd().getDateTime());
				//FIXME brs by Djer status de l'évènnement, pas de l'utilsiateur qui fait la requete
				item.put("status", e.getStatus());
				item.put("self", e.getCreator().getSelf());
				EventArray.put(item);
				json.put("event", EventArray);
			}
			//TODO brs by Djer Evite les multiple return dans la même méthode
			return json.toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
		    //TODO brs by Djer "e.printStackTrace()" affiche la pile dans la console. Utilise une LOG à la place !
			e.printStackTrace();
			//TODO brs by Djer NE fait PAS de return dans un catch, tu vas de "créer" des bugs !
			return e.getMessage();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.getMessage();
		}

	}

}
