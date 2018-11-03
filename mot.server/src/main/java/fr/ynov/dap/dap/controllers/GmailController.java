package fr.ynov.dap.dap.controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.websocket.server.PathParam;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.services.GmailService;

/**
 * The Class GmailController.
 */
@RestController
//TODO mot by Djer Evite de mettre des "get" dans les URL 
@RequestMapping("/getEmails")
public class GmailController {
	
	/**
	 * Gets the nbr unread mail.
	 *
	 * @param userId the user id
	 * TODO mot by Djer FAUX ! parfois renvoie un "message d'exception" !
	 * @return the nbr unread mail (TODO mot by Djer Préciser qu'il s'agit de JSON ? Le format ?
	 */
	@RequestMapping("/nbrunreadmail/{userId}")
    public @ResponseBody Object getNbrUnreadMail(@PathParam("userId") String userId) {
        Integer nbrEmailUnread = null;
        try {
            nbrEmailUnread = GmailService.nbrEmailUnread(userId);
            
            JSONObject json = new JSONObject();
            json.put("nbmailunread", nbrEmailUnread);
            return json.toString();
            
        } catch (GeneralSecurityException e) {
          //TODO mot by Djer Traite le todo, un LOG en error serait plus approprié !
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        } catch (IOException e) {
          //TODO mot by Djer Traite le todo, un LOG en error serait plus approprié !
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
