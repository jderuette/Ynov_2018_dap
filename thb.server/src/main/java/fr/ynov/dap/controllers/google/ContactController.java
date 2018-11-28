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
	@Autowired
	ContactService googleService;

	/** The microsoft service. */
	@Autowired
	MicrosoftContactService microsoftService;

	/**
	 * Gets the contacts.
	 *
	 * @param userKey the user key
	 * @return the contacts
	 */
	@RequestMapping("/total")
	public @ResponseBody String getContacts(@RequestParam(value = "userKey", required = true) String userKey) {
		Integer total = 0;

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
