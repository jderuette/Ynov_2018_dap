package fr.ynov.dap.controller;

import fr.ynov.dap.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.outlook.dev.auth.AuthHelper;
import com.outlook.dev.auth.TokenResponse;
import com.outlook.dev.service.*;

@Controller
public class MailController {
	@Autowired
	GmailService gmailService;

	@RequestMapping("gmail/unread/{gUser}")
	public int messageNonLus(@RequestParam("userKey") String userKey, @PathVariable("gUser") String gUser)
			throws IOException, GeneralSecurityException {
		return gmailService.getUnreadMessageCount(userKey, gUser);
	}

	@RequestMapping("/outlook")
	public String messages(Model model, HttpServletRequest request, ModelMap mo) {
		UUID state = UUID.randomUUID();
		UUID nonce = UUID.randomUUID();

		// Save the state and nonce in the session so we can
		// verify after the auth process redirects back
		HttpSession session = request.getSession();
		session.setAttribute("expected_state", state);
		session.setAttribute("expected_nonce", nonce);
		String loginUrl = AuthHelper.getLoginUrl(state, nonce);
		model.addAttribute("loginUrl", loginUrl);
		mo.addAttribute("loginUrl", loginUrl);
		// Name of a definition in WEB-INF/defs/pages.xml
		return "index";
	}

	@RequestMapping("/mail")
	public String mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession();

		TokenResponse tokens = (TokenResponse) session.getAttribute("tokens");
		if (tokens == null) {
			// No tokens in session, user needs to sign in
			redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
			return "redirect:/index";
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
