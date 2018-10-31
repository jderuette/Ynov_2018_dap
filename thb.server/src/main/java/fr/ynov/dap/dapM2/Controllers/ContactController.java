package fr.ynov.dap.dapM2.Controllers;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.websocket.server.PathParam;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.people.v1.model.ListConnectionsResponse;
import fr.ynov.dap.dapM2.Services.ContactService;
import fr.ynov.dap.dapM2.Services.GoogleService;

@RestController
@RequestMapping("/contact")
//FIXME thb by Djer Pourquoi étendre GoogleService ?
public class ContactController extends GoogleService {

	public ContactController() throws IOException, GeneralSecurityException {
		super();
	}

	/**
	 * Events.
	 *
	 * @param user the user
	 * @return the string
	 */
	@RequestMapping("/{user}")
    public @ResponseBody String events(@PathParam("user") String user) {
		if (user == null) {
			user = "benjamin.thomas.sso@gmail.com";
		}
		
        try {
            //TODO thb by Djer "new Service" ? Et l'IOC ?
        	ListConnectionsResponse nb_contacts = new ContactService().getContacts(user);
        	
        	JSONObject response = new JSONObject();
        	response.put("contacts", nb_contacts.getTotalItems());
        	
			return response.toString();
		} catch (IOException e) {
		    //TODO thb by Djer en cas d'erreur tu ne renvoie plus du JSON, mais du texte, ce qui va perturber les clients.
			return e.getMessage();
		} catch (GeneralSecurityException e) {
			return e.getMessage();
		}
    }
}