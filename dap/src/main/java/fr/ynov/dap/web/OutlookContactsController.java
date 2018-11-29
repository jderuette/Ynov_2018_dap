package fr.ynov.dap.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.service.OutlookContactService;
import fr.ynov.dap.service.OutlookService;
import fr.ynov.dap.service.OutlookServiceBuilder;
import fr.ynov.dap.service.MicrosoftPagedResultService;
import fr.ynov.dap.service.MicrosoftTokenResponseService;

@Controller
/**
 * Accède à l'URL relatives aux contacts microsoft.
 * @author abaracas
 *
 */
public class OutlookContactsController {
	@RequestMapping("/contacts")
	/**
	 * Redirige sur la page qui affiche les contacts du compte.
	 * @param model Model
	 * @param request HTTP servlet
	 * @param redirectAttributes redirection en cas de probleme
	 * @return l'URL de la page contacts.
	 */
	public String contacts(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();
		MicrosoftTokenResponseService tokens = (MicrosoftTokenResponseService)session.getAttribute("tokens");
		if (tokens == null) {
			// No tokens in session, user needs to sign in
			redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
			return "redirect:/index";
		}
		
		Date now = new Date();
		if (now.after(tokens.getExpirationTime())) {
			redirectAttributes.addFlashAttribute("error", "The access token has expired. Please logout and re-login.");
			return "redirect:/index";
		}
		
		String email = (String)session.getAttribute("userEmail");
		
		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);
		
		// Sort by given name in ascending order (A-Z)
		String sort = "GivenName ASC";
		// Only return the properties we care about
		String properties = "GivenName,Surname,CompanyName,EmailAddresses";
		// Return at most 100 contacts
		Integer maxResults = 100;
		
		try {
			MicrosoftPagedResultService<OutlookContactService> contacts = outlookService.getContacts(
					sort, properties, maxResults)
					.execute().body();
			model.addAttribute("contacts", contacts.getValue());
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/index";
		}
		
		return "contacts";
	}
}
