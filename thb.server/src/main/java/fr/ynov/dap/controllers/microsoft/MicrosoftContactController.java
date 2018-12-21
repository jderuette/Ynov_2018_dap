package fr.ynov.dap.controllers.microsoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import fr.ynov.dap.services.microsoft.MicrosoftContactService;
import fr.ynov.dap.utils.ExtendsUtils;

/**
 * The Class MicrosoftContactController.
 */
@RequestMapping("/microsoft")
@Controller
public class MicrosoftContactController extends ExtendsUtils {

	/** The service. */
	@Autowired
	private MicrosoftContactService service;

	/**
	 * Events.
	 *
	 * @param userKey the user key
	 * @param model   the model
	 * @return the string
	 */
	//TODO thb by Djer |Spring| Si tu n'as pas besoin du model, ne le met pas dans la signature de ta méthode
	@RequestMapping("/contact")
	public @ResponseBody String events(@RequestParam(value = "userKey", required = true) String userKey, Model model) {
	    //TODO thb by Djer |MVC| Tu devrait ajouter les données renvoyé par le service dans le modèle pour qu'ils puissent être utilisé dans la vue
		LOG.info(service.getContacts(userKey));

		//TODO thb by Djer |MVC| Tu devrais renvoyer vers une vue "contact"
		return "success";
	}
}