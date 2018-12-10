package fr.ynov.dap.GoogleMaven;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.GoogleMaven.auth.TokenResponse;
import fr.ynov.dap.GoogleMaven.service.Contact;
import fr.ynov.dap.GoogleMaven.service.OutlookService;
import fr.ynov.dap.GoogleMaven.service.OutlookServiceBuilder;
import fr.ynov.dap.GoogleMaven.service.PagedResult;

@Service
public class ContactsOutlook {
	private  final static Logger logger = LogManager.getLogger();
	/**
	 * 
	 * @param user
	 * @return contact method with Outlook api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Contact[] contacts(ModelMap model, HttpServletRequest request) throws IOException {
		PagedResult<Contact> contacts = null;
		try {
		HttpSession session = request.getSession();
		TokenResponse tokens = (TokenResponse)session.getAttribute("tokens");
		Date now = new Date();
		String email = (String)session.getAttribute("userEmail");
		
		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);
		
		// Sort by given name in ascending order (A-Z)
		String sort = "givenName ASC";
		// Only return the properties we care about
		String properties = "givenName,surname,companyName,emailAddresses";
		// Return at most 10 contacts
		Integer maxResults = 10;
		contacts = outlookService.getContacts(
				sort, properties, maxResults)
				.execute().body();
		model.addAttribute("contacts", contacts.getValue());
		}
		catch (Exception e){
			logger.warn("Une erreur a été détecter dans le service contact outlook avec comme details : "+e.getMessage());	
		}
		return contacts.getValue();
	}
}
