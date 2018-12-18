package fr.ynov.dap.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.data.Token;
import fr.ynov.dap.dap.google.GmailService;
import fr.ynov.dap.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.dap.repository.AppUserRepository;
import fr.ynov.dap.dap.repository.GoogleUserRepository;
import fr.ynov.dap.dap.repository.MicrosoftUserRepository;
import fr.ynov.dap.dap.service.Message;
import fr.ynov.dap.dap.service.OutlookService;
import fr.ynov.dap.dap.service.OutlookServiceBuilder;
import fr.ynov.dap.dap.service.PagedResult;


@Controller
public class Welcome {
	
  //TODO for by Djer |POO| Attention, devrait être privé. Si tu ne pécises pas l'attribut à la même porté que la classe (donc "public" ici) !
	@Autowired
	AppUserRepository userRepo;
	//TODO for by Djer |POO| Attention, devrait être privé. Si tu ne pécises pas l'attribut à la même porté que la classe (donc "public" ici) ! De plus tu n'utilises pas cetet attribut, mais ton IDE ne peu pas te l'indiqué car il est public
	@Autowired
	GoogleUserRepository googleRepo;
	//TODO for by Djer |POO| Attention, devrait être privé. Si tu ne pécises pas l'attribut à la même porté que la classe (donc "public" ici) !
	@Autowired
	MicrosoftUserRepository msRepo;

	int mailCount = 0;
	
	@RequestMapping(value= {"/"})
	public String welcome(ModelMap model, final HttpSession session)
	{
		int nbunreadEmails = 0;
		
		model.addAttribute("nbEmails", nbunreadEmails);
		return "welcome";
	}
	
	@RequestMapping(value= {"/countAllMails"})
	public String countAllMails(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes)
	{
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
	    // Return at most 10000 messages
	    Integer maxResults = 10000;
		try {
		      PagedResult<Message> messages = outlookService.getMessages(
		          folder, sort, properties, maxResults)
		          .execute().body();
		      mailCount += messages.getValue().length;
		    } catch (IOException e) {
		      redirectAttributes.addFlashAttribute("error", e.getMessage());
		      return "redirect:/index";
		    }
		
		GmailService gmail = new GmailService();
		googleRepo.findAll().forEach((temp) ->
		{
			try {
			    //TODO for by Djer |POO| Cette méthode attend un **userKey** !!
				mailCount += gmail.GetEmailNumber(temp.getAccountName());
			} catch (IOException e) {
			    //TODO for by Djer |Log4J| Utilise une log plutot que le "e.printStackTrace()". Traite les TO-DO au fur et à mesure
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
			  //TODO for by Djer |Log4J| Utilise une log plutot que le "e.printStackTrace()". Traite les TO-DO au fur et à mesure
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
	      model.addAttribute("nbEmails", mailCount);
		return "welcome";
	}
}
