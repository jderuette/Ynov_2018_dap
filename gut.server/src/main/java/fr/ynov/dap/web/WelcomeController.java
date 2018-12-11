package fr.ynov.dap.web;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.service.microsoft.MicrosoftService;
import fr.ynov.dap.web.MailController;

@Controller
public class WelcomeController extends BaseController {

  //TODO gut by Djer |POO| SI tu ne précise pas, cette attribut aurat la même porté que la classe (donc public).
	@Autowired
	MailController gmailRequests;

	@RequestMapping("/")
	public String welcome(ModelMap model, HttpServletRequest request) throws IOException {
		UUID state = UUID.randomUUID();
		UUID nonce = UUID.randomUUID();

		// Save the state and nonce in the session so we can
		// verify after the auth process redirects back
		HttpSession session = request.getSession();
		session.setAttribute("expected_state", state);
		session.setAttribute("expected_nonce", nonce);

		String loginUrl = MicrosoftService.getLoginUrl(state, nonce);
		model.addAttribute("loginUrl", loginUrl);
		// Name of a definition in WEB-INF/defs/pages.xml
		// model.addAttribute("nbEmails", gmailRequests.getUnreadMessageCount("test"));
		return "welcome";
	}

	@RequestMapping("/all_credentials")
	public String all_credentials(ModelMap model) throws IOException {
//		
//		GoogleAuthorizationCodeFlow flow = googleService.getFlow();
//		DataStore<StoredCredential> storedCredential = flow.getCredentialDataStore();
//		Map<String, String> storedCredentialMap = new HashMap<String, String>();

//		Iterator it = storedCredential.keySet().iterator();
//	    while (it.hasNext()) {
//	        Map.Entry pair = (Map.Entry)it.next();
//	        storedCredentialMap.put(pair.getKey(), pair.getValue());
//	        it.remove(); // avoids a ConcurrentModificationException
//	    }

		model.addAttribute("nbEmails", gmailRequests.getUnreadMessageCount("test"));
		return "all_credentials";
	}

}
