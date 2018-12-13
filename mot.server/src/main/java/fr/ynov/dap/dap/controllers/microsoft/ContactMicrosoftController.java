package fr.ynov.dap.dap.controllers.microsoft;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.services.microsoft.ContactMicrosoftService;

/**
 * The Class ContactMicrosoftController.
 */
@Controller
public class ContactMicrosoftController {

	@Autowired
	ContactMicrosoftService contactMicrosoftService;

	/**
	 * Nb contact.
	 *
	 * @param userKey            the user key
	 * @param model              the model
	 * @param request            the request
	 * @param redirectAttributes the redirect attributes
	 * @return the thymeleaf template Contact with nbContact Microsoft
	 */
	//TODO mot by Djer |Spring| le "required = true" est la valeur par defaut, tu n'es pas obligé de le préciser
	@RequestMapping("/nbContactMicrosoft")
	public String nbContact(@RequestParam(value = "userKey", required = true) String userKey, Model model,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Integer nbContact = 0;

		if (contactMicrosoftService.getContacts(userKey) != null) {
			nbContact = contactMicrosoftService.getContacts(userKey);
		}

		if (nbContact == null) {
			nbContact = 0;
		}

		model.addAttribute("nbContact", nbContact);
		model.addAttribute("userKey", userKey);

		return "contact";
	}
}
