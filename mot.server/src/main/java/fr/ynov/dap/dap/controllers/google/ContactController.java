package fr.ynov.dap.dap.controllers.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.services.google.ContactService;

/**
 * The Class ContactController.
 */
@RestController
@RequestMapping("/contact")
public class ContactController {

	/**
	 * Gets the nb contact.
	 *
	 * @param user the user
	 * @return the nb contact
	 */
	@Autowired
	private ContactService contactService;

	protected Logger LOG = LogManager.getLogger(ContactController.class);

	//TODO mot by Djer |Spring| "@ResponseBody" n'est pas utile, par defaut les méthodes "mappées" d'un **Rest**Controller produisent un Body
    //TODO mot by Djer |Spring| le "required = true" est la valeur par defaut, tu n'es pas obligé de le préciser
	@RequestMapping("nbContGoogle")
	public @ResponseBody String getNbContact(@RequestParam(value = "userKey", required = true) String userKey) {

		Integer nbContact = null;
		String response = "errorOccurs";

		try {
			nbContact = contactService.getNbContact(userKey);
			response = nbContact.toString();

		} catch (IOException e) {
		  //TODO mot by Djer |log4J| Contextualise tes messages (" for userkey : " + userKey)
			LOG.error("Error Contact google", e);

		} catch (GeneralSecurityException e) {
		  //TODO mot by Djer |log4J| Contextualise tes messages (" for userkey : " + userKey)
			LOG.error("Error Contact google", e);
		}
		return response;
	}
}