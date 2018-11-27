package fr.ynov.dap.web;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.service.google.ContactService;

@RestController
public class ContactController extends BaseController{

/**
 * Injection du service Contact faisant référence a l'api People
 */
	@Autowired ContactService contactService;

	/**
	 * Récupération et envoi au format json du nombre de contacts
	 * @param userId
	 * @return le nombre de contacts
	 * @throws IOException
	 */
	@RequestMapping("/contact/getCount")
	public String getContactCount(@RequestParam final String userId) throws IOException{
		getLogger().debug("ContactController/getContactCount : Appel de contact service pour recuperer les contacts");
		int contactsCount = contactService.getContactCount(userId);			 
		return "{'contactCount':'" + contactsCount + "'}";
	}


}
