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

  //TODO gut by Djer |POO| SI tu ne précise pas, cette attribut aurat la même porté que la classe (donc public).
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
	//TODO gut by Djer |JPA| L'apel devrait se faire en utilisant un userKey (et devrait grouper Microsoft ET Google)
	@RequestMapping("/contact/getCount")
	public String getContactCount(@RequestParam final String userId) throws IOException{
		getLogger().debug("ContactController/getContactCount : Appel de contact service pour recuperer les contacts");
		int contactsCount = contactService.getContactCount(userId);			 
		return "{'contactCount':'" + contactsCount + "'}";
	}
	
	//TODO gut by Djer |SOA| Une bonne partie de ce code pourrait être dans un "MicrosoftCalendarService"
	@RequestMapping("/outlook/contacts")
	  public String contacts(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
	    HttpSession session = request.getSession();
	    //TODO gut by Djer |API Microsoft| il faut récupérer le "token" dans la BDD, sinon ton "lcient" DOITY d'abord "créer" une session hors notre APi est (censé être) stateless !
	    TokenResponse tokens = (TokenResponse)session.getAttribute("tokens");
	    if (tokens == null) {
	      //TODO gut by Djer |Log4J| Une petite Log ?
	      // No tokens in session, user needs to sign in
	        //TODO gut by Djer |Rest API| Un "FlashAttribute" n'a de sens que dans le cas d'une IHM en mode "html" (il faut une session)
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
	        //TODO gut by Djer |API Microsoft| La demande etait de compter le nombre de contatcs, mais avec ta limitation à "10" ca pas être top.
	      PagedResult<MicrosoftContact> contacts = outlookService.getContacts(
	          sort, properties, maxResults)
	          .execute().body();
	      model.addAttribute("contacts", contacts.getValue());
	    } catch (IOException e) {
	        //TODO gut by Djer |Log4J| Une petite Log ?
	      redirectAttributes.addFlashAttribute("error", e.getMessage());
	      //TODO gut by Djer |Spring| Un **Rest**Controller ne PEUT pas faire de redirect
	      return "redirect:/index.html";
	    }
	    
	    return "contacts";
	  }


}
