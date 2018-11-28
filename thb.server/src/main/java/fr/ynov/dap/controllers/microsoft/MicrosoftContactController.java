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
	@RequestMapping("/contact")
	public @ResponseBody String events(@RequestParam(value = "userKey", required = true) String userKey, Model model) {
		LOG.info(service.getContacts(userKey));

		return "success";
	}
}