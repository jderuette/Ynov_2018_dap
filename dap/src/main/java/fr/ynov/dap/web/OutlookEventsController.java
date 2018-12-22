package fr.ynov.dap.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.service.OutlookEventService;
import fr.ynov.dap.service.OutlookService;
import fr.ynov.dap.service.OutlookServiceBuilder;
import fr.ynov.dap.service.MicrosoftPagedResultService;
import fr.ynov.dap.service.MicrosoftTokenResponseService;

@Controller
/**
 * Accède à l'URL relatives aux events microsoft.
 * @author abaracas
 *
 */
public class OutlookEventsController {

	@RequestMapping("/events")
	/**
	 * Redirige sur la page adéquates où seront affichés les événements de l'utilisateur.
	 * @param model Model
	 * @param request requête HttpServlet
	 * @param redirectAttributes redirection en cas d'erreur
	 * @return l'url où sont affichés les résultats
	 */
	public String events(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();
		MicrosoftTokenResponseService tokens = (MicrosoftTokenResponseService)session.getAttribute("tokens");
		if (tokens == null) {
			// No tokens in session, user needs to sign in
		  //TODO baa by Djer |Log4J| Une petite log ?
			redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
			return "redirect:/index";
		}
		
		Date now = new Date();
		if (now.after(tokens.getExpirationTime())) {
			// Token expired
			// TODO: Use the refresh token to request a new token from the token endpoint
			// For now, just complain
		  //TODO baa by Djer |Log4J| Une petite log ?
			redirectAttributes.addFlashAttribute("error", "The access token has expired. Please logout and re-login.");
			return "redirect:/index";
		}
		
		String email = (String)session.getAttribute("userEmail");
		
		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);
		
		// Sort by start time in descending order
		String sort = "Start/DateTime DESC";
		// Only return the properties we care about
		String properties = "Organizer,Subject,Start,End";
		// Return at most 10 events
		//TODO baa by Djer |API Microsoft| Utilise la pagination pour afficher plus d'event
		Integer maxResults = 10;
		
		try {
			MicrosoftPagedResultService<OutlookEventService> events = outlookService.getEvents(
					sort, properties, maxResults)
					.execute().body();
			model.addAttribute("events", events.getValue());
		} catch (IOException e) {
		  //TODO baa by Djer |Log4J| Une petite log ?
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/index";
		}
		
		return "events";
	}
	
	//TODO baa by Djer |API Microsoft| LE prochaine event (API Rest) ?
}
