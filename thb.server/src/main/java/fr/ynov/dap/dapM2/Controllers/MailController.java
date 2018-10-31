package fr.ynov.dap.dapM2.Controllers;

import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.dapM2.Services.GmailService;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.websocket.server.PathParam;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The Class MailController.
 */
@RestController
@RequestMapping("/mail")
public class MailController {

	/**
	 * Events.
	 *
	 * @param user the user
	 * @return the string
	 */
	@RequestMapping("/{user}")
    public @ResponseBody String events(@PathParam("user") String user) {
        try {
        	Label label = new GmailService().getLabel(user);
        	Integer nbEmailTotal = label.getMessagesTotal();
        	Integer nbEmailUnread = label.getMessagesUnread();
        	
        	JSONObject response = new JSONObject();
        	response.put("emails", nbEmailTotal);
        	response.put("emails_unread", nbEmailUnread);
        	
			return response.toString();
		} catch (IOException e) {
			return e.getMessage();
		} catch (GeneralSecurityException e) {
			return e.getMessage();
		}
    }
}
