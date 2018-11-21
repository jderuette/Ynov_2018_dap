package fr.ynov.dap.microsoft.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.microsoft.AuthHelper;
import fr.ynov.dap.microsoft.data.*;
import fr.ynov.dap.microsoft.models.TokenResponse;
import fr.ynov.dap.microsoft.service.OutlookService;
import fr.ynov.dap.microsoft.service.OutlookServiceBuilder;

@Controller
public class MailController {

	@RequestMapping("/mail")
	public String mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();
		TokenResponse tokens = (TokenResponse) session.getAttribute("tokens");
		if (tokens == null) {
			// No tokens in session, user needs to sign in
			redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
			return "redirect:/index.html";
		}

		String tenantId = (String) session.getAttribute("userTenantId");

		tokens = AuthHelper.ensureTokens(tokens, tenantId);

		String email = (String) session.getAttribute("userEmail");

		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		// Retrieve messages from the inbox
		String folder = "inbox";
		// Sort by time received in descending order
		String sort = "receivedDateTime DESC";
		// Only return the properties we care about
		String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
		// Return at most 10 messages
		Integer maxResults = 10;

		try {
			PagedResult<Message> messages = outlookService.getMessages(folder, sort, properties, maxResults).execute()
					.body();
			model.addAttribute("messages", messages.getValue());
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/index";
		}

		return "mail";
	}
}