package fr.ynov.dap.controllers.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.services.google.ContactService;
import fr.ynov.dap.services.microsoft.MicrosoftContactService;
import fr.ynov.dap.utils.ExtendsUtils;
import fr.ynov.dap.utils.JSONResponse;

/**
 * The Class ContactController.
 */
@RestController
@RequestMapping("/contact")
public class ContactController extends ExtendsUtils {

	/** The google service. */
    //TODO thb by Djer |POO| Précise le modifier sinon le même que celui de la classe
	@Autowired
	ContactService googleService;

	/** The microsoft service. */
	//TODO thb by Djer |POO| Précise le modifier sinon le même que celui de la classe
	@Autowired
	MicrosoftContactService microsoftService;

	/**
	 * Gets the contacts.
	 *
	 * @param userKey the user key
	 * @return the contacts
	 */
	@RequestMapping("/total")
	//TODO thb by Djer |Spring| Par defaut les méthodes mappées dans un **Rest**Controller renvoie le body. C'est redondant de le préciser
	//TODO thb by Djer |Sping| "required=true" est la valeur par défaut, ca n'est pas utile de le préciser
	public @ResponseBody String getContacts(@RequestParam(value = "userKey", required = true) String userKey) {
		Integer total = 0;

		//TODO thb by Djer |POO| Attention tu t'es trompé de service, tu as appelé le microsoft à la place du google
		Integer googleNbContacts = microsoftService.getContacts(userKey);
		if (googleNbContacts != null) {
			total += googleNbContacts;
		}

		Integer microsoftNbContacts = microsoftService.getContacts(userKey);
		if (microsoftNbContacts != null) {
			total += microsoftNbContacts;
		}

		return JSONResponse.responseString("total_contacts", total);
	}
}
