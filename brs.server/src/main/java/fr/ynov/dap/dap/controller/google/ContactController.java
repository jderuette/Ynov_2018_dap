package fr.ynov.dap.dap.controller.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import Utils.LoggerUtils;
import fr.ynov.dap.dap.service.google.ContactService;
import fr.ynov.dap.dap.service.microsoft.MicrosoftContactService;

/**
 * The Class ContactController.
 */
@Controller
@RequestMapping("/contact")
public class ContactController extends LoggerUtils {

	/**
	 * Gets the nb contact.
	 *
	 * @param user the user
	 * @return the nb contact
	 */
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private MicrosoftContactService microsoftContactService;

	@RequestMapping("/nbContact")
	public String getNbContact(@RequestParam("userKey") String user,Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException, GeneralSecurityException {

		Integer people = 0;

		people = contactService.getNbTotalContact(user);
		people += microsoftContactService.getNbTotalContact(user);
		
		model.addAttribute("nbContact",people);
		model.addAttribute("content","contact");
		
		return "index";

	}

}
