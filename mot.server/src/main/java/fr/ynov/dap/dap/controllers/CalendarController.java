package fr.ynov.dap.dap.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.Evenement;
import fr.ynov.dap.dap.services.CalendarService;

/**
 * The Class CalendarController.
 */
@RestController
//TODO mot by Djer Evite de mettre des "get" dans les URL c'est ambigue avec le verb "GET" de HTTP.
// Deplus une API Rest serait succeptible d'accepter du POST, PUT, ... et il faudrait alors modifier l'URL...
@RequestMapping("/getCalendar")
public class CalendarController {
	
	/**
	 * Gets the last event.
	 *
	 * @param userId the user id
	 * @return the last event
	 */
	@RequestMapping("/getLastEvent/{userId}")
	//TODO mot by Djer "last" serait le dernier exixtant, là c'est le NEXT qu'on renvoie
    public @ResponseBody Evenement getLastEvent(@PathParam("userId") String userId) {
        Evenement lastEvent = null;
        try {
        	lastEvent = CalendarService.lastEvent(userId);
        } catch (GeneralSecurityException e) {
            //TODO mot by Djer Traite le todo, un LOG en error serait plus approprié !
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
          //TODO mot by Djer Traite le todo, un LOG en error serait plus approprié !
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lastEvent;
    }
}
