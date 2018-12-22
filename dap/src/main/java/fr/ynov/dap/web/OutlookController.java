package fr.ynov.dap.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.AuthHelper;
import fr.ynov.dap.service.OutlookMessageService;
import fr.ynov.dap.service.OutlookService;
import fr.ynov.dap.service.OutlookServiceBuilder;
import fr.ynov.dap.service.MicrosoftPagedResultService;
import fr.ynov.dap.service.MicrosoftTokenResponseService;

@Controller
/**
 * Accède à l'URL relatives aux mails microsoft.
 * @author abaracas
 *
 */
public class OutlookController {

	@RequestMapping("/mail")
	/**
	 * Redirige vers l'URL où son afficher les mails recus.
	 * @param model Model
	 * @param request HttpServlet
	 * @param redirectAttributes redirection en cas de problème
	 * @return l'url de la page mails.
	 */
	public String mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();
		MicrosoftTokenResponseService tokens = (MicrosoftTokenResponseService)session.getAttribute("tokens");
		if (tokens == null) {
			// No tokens in session, user needs to sign in
		  //TODO baa by Djer |Log4J| Une petite log ?
			redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
			return "redirect:/index";
		}
		
		String tenantId = (String)session.getAttribute("userTenantId");
		
		tokens = AuthHelper.ensureTokens(tokens, tenantId);
		
		String email = (String)session.getAttribute("userEmail");
		
		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);
		
		// Retrieve messages from the inbox
		String folder = "inbox";
		// Sort by time received in descending order
		String sort = "ReceivedDateTime DESC";
		// Only return the properties we care about
		String properties = "ReceivedDateTime,From,IsRead,Subject,BodyPreview";
		// Return at most 10 messages
		//TODO baa by Djer |API Microsoft| Utilise la pagination pour afficher plus de mails
		Integer maxResults = 10;
		
		try {
			MicrosoftPagedResultService<OutlookMessageService> messages = outlookService.getMessages(
					folder, sort, properties, maxResults)
					.execute().body();
			//TODO baa by Djer |Thymleaf| Le terme "message" est un peu générique. Ta Vue va devenir "peu" lisible
			model.addAttribute("messages", messages.getValue());
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/index";
		}
		
		return "mail";
	}
	
	//TODO baa by Djer |API Microsoft| Le nombre d'email (API Rest) ?
}
