package fr.ynov.dap.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import fr.ynov.dap.data.microsoft.MicrosoftContact;
import fr.ynov.dap.service.google.ContactService;
import fr.ynov.dap.service.microsoft.MicrosoftService;
import fr.ynov.dap.service.microsoft.OutlookService;
import fr.ynov.dap.service.microsoft.auth.TokenResponse;
import fr.ynov.dap.service.microsoft.helper.OutlookServiceBuilder;
import fr.ynov.dap.service.microsoft.helper.PagedResult;

@RestController
public class ContactController extends BaseController{

/**
 * Injection du service Contact faisant référence a l'api People
 */
	@Autowired ContactService contactService;

	/**
	 * Récupération et envoi au format json du nombre de contacts
	 * @param userId
	 * @return le nombre de contacts
	 * @throws IOException
	 */
	@RequestMapping("/contact/getCount")
	public String getContactCount(@RequestParam final String userId) throws IOException{
		getLogger().debug("ContactController/getContactCount : Appel de contact service pour recuperer les contacts");
		int contactsCount = contactService.getContactCount(userId);			 
		return "{'contactCount':'" + contactsCount + "'}";
	}
	
	@RequestMapping("/outlook/contacts")
	  public String contacts(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
	    HttpSession session = request.getSession();
	    TokenResponse tokens = (TokenResponse)session.getAttribute("tokens");
	    if (tokens == null) {
	      // No tokens in session, user needs to sign in
	      redirectAttributes.addFlashAttribute("error", "Please sign in to continue.");
	      return "redirect:/index.html";
	    }
	    
	    String tenantId = (String)session.getAttribute("userTenantId");
	  	
	  	  tokens = MicrosoftService.ensureTokens(tokens, tenantId);
	    
	    String email = (String)session.getAttribute("userEmail");
	    
	    OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);
	    
	    // Sort by given name in ascending order (A-Z)
	    String sort = "GivenName ASC";
	    // Only return the properties we care about
	    String properties = "GivenName,Surname,CompanyName,EmailAddresses";
	    // Return at most 10 contacts
	    Integer maxResults = 10;
	    
	    try {
	      PagedResult<MicrosoftContact> contacts = outlookService.getContacts(
	          sort, properties, maxResults)
	          .execute().body();
	      model.addAttribute("contacts", contacts.getValue());
	    } catch (IOException e) {
	      redirectAttributes.addFlashAttribute("error", e.getMessage());
	      return "redirect:/index.html";
	    }
	    
	    return "contacts";
	  }


}
