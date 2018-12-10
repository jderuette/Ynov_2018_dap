package fr.ynov.dap.GoogleMaven.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import fr.ynov.dap.GoogleMaven.ContactService;


@Controller
public class PeopleController {

	@Autowired
	static ContactService contactService;
	//private static final Logger logger = LogManager.getLogger();
	/**
	 * 
	 * @param user
	 * @return people controller with google api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@RequestMapping("/NombreDeContact")
	public String GetContact(@RequestParam final String userKey) throws IOException, GeneralSecurityException {
		
	    //TODO elj by Djer |Thymleaf| C'est le travail de la vue d'internationnaliser et du coups de "produire le texte".
		String nbcontacts = "vous avez : "+contactService.GetContact(userKey)+" contacts";
		return nbcontacts;
		
	}
}
