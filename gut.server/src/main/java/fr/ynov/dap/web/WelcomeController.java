package fr.ynov.dap.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.web.MailController;

@Controller
public class WelcomeController extends BaseController {
	
	@Autowired MailController gmailRequests;

	
	@RequestMapping("/")
	public String welcome(ModelMap model) throws IOException {
		model.addAttribute("nbEmails", gmailRequests.getUnreadMessageCount("test"));
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
