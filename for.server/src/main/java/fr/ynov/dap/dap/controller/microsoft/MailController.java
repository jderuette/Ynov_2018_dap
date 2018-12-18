package fr.ynov.dap.dap.controller.microsoft;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.data.Token;
import fr.ynov.dap.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.dap.service.Message;
import fr.ynov.dap.dap.service.OutlookContact;
import fr.ynov.dap.dap.service.OutlookService;
import fr.ynov.dap.dap.service.OutlookServiceBuilder;
import fr.ynov.dap.dap.service.PagedResult;
import retrofit2.Response;

@Controller
public class MailController {

  @RequestMapping(value= {"/mail"})
  public String mail(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    //TODO for by Djer |Rest API| Trop dépendant de la session, fonctionenra très mal sur l'API prévue
    HttpSession session = request.getSession();
    Token tokens = (Token)session.getAttribute("tokens");
    if (tokens == null) {
      // No tokens in session, user needs to sign in
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
    String sort = "receivedDateTime DESC";
        // Only return the properties we care about
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
    // Return at most 10 messages
    Integer maxResults = 10;

    try {
      PagedResult<Message> messages = outlookService.getMessages(
          folder, sort, properties, maxResults)
          .execute().body();
      model.addAttribute("messages", messages.getValue());
    } catch (IOException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/index";
    }

    return "mail";
  }
  
  //TODO for by Djer |POO| Est-ce que le "MailController" est adapté pour cette méthode ? 
  @RequestMapping(value= {"/getContactNumber"})
	public String getContactNumber(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException
	{
	  HttpSession session = request.getSession();
	  String sort = "GivenName ASC";
      String properties = "GivenName,Surname,CompanyName,EmailAddresses";
      
      //TODO for by Djer |API Microsoft| Tel que tu as structuré ton code c'est au Controller de s'assurer que les token sont toujours valides (eten re-demander si nécéssaire) via AuthHelper.ensureTokens(tokens, tenantId) 

      //TODO for by Djer |Rest API| L'utilsiateur (via le client de l'A1PI) devra se "connecter" pour appelr cette méthdoe (la seul façon dans ton appli ets de créer un compte Microsoft) AVANT d'appeler cette méthode. Récupère le token stocké en BDD
      Token newTokens = (Token)session.getAttribute("tokens");

      //TODO for by Djer |Rest API| L'utilsiateur devra se connecter. Stocke cette information dans la BDD ou récupère le à chaque fois que tu en as besoin.
      String email = (String)session.getAttribute("userEmail");
      OutlookService outlookService = OutlookServiceBuilder.getOutlookService(newTokens.getAccessToken(), email);

      Response<PagedResult<OutlookContact>> response = outlookService.getContacts(sort, properties).execute();

      PagedResult<OutlookContact> contacts = response.body();
      
      return "contact number: " + contacts.getValue().length;
	}
}