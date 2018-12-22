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
//TODO baa by Djer |MVC| Séparation entre Controller et service ?
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
		//TODO baa by Djer |Rest API| Stocke dans la BDD, si tu utilise la session, l'API reste de vra se "conencter" avant chaque appels, hors notre API est doit être stateLess. Deplus la seul facon de se "connecter" est d'ajouter un compte Microsoft
		MicrosoftTokenResponseService tokens = (MicrosoftTokenResponseService)session.getAttribute("tokens");
		if (tokens == null) {
			// No tokens in session, user needs to sign in
		  //TODO baa by Djer |Log4J| Une petite log ?
			redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
			return "redirect:/index";
		}
		
		Date now = new Date();
		if (now.after(tokens.getExpirationTime())) {
		  //TODO baa by Djer |Log4J| Une petite log ?
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
		//TODO baa by Djer |API Microsoft| Utilise la pagination pour afficher plus de contacts
		Integer maxResults = 100;
		
		try {
			MicrosoftPagedResultService<OutlookContactService> contacts = outlookService.getContacts(
					sort, properties, maxResults)
					.execute().body();
			model.addAttribute("contacts", contacts.getValue());
		} catch (IOException e) {
		  //TODO baa by Djer |Log4J| Une petite log ?
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/index";
		}
		
		return "contacts";
	}
	
	//TODO baa by Djer |API Microsoft| Le nombre de contact (API Rest) ?
}
