package fr.ynov.dap.web;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.google.GoogleAccount;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.data.microsoft.MicrosoftMessage;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.service.google.GmailService;
import fr.ynov.dap.service.microsoft.MicrosoftService;
import fr.ynov.dap.service.microsoft.OutlookService;
import fr.ynov.dap.service.microsoft.auth.TokenResponse;
import fr.ynov.dap.service.microsoft.helper.OutlookServiceBuilder;
import fr.ynov.dap.service.microsoft.helper.PagedResult;

@RestController
public class MailController extends BaseController {
	
  //TODO gut by Djer |POO| SI tu ne précise pas, cette attribut aurat la même porté que la classe (donc public).
	/**
	 * Service Gmail permettant d'appeler l'API Google
	 */
	@Autowired GmailService gmailService;

	//TODO gut by Djer |POO| SI tu ne précise pas, cette attribut aurat la même porté que la classe (donc public).
	@Autowired AppUserRepository appUserRepository;
	
	
	/**
	 * @param userId
	 * @return Le nombre de mails non lus
	 * @throws IOException
	 */
	@RequestMapping("/gmail/countUnread")
	public String getUnreadMessageCount(@RequestParam final String userId) throws IOException {		
		getLogger().debug("MailController/getUnreadMessageCount : Appel de gmail service pour recuperer le nb de messages non lus");
		List<Message> messages =  gmailService.getUnreadMessageCount(userId);
		int unreadMessageCount = 0;
		for (Message message : messages) {
			unreadMessageCount++;
		}
		return "{'mailCount':'" + unreadMessageCount + "'}";
	}
	/**
	 * @param userId
	 * @return la liste des labels sur Gmail
	 * @throws IOException
	 */
	@RequestMapping("/gmail/labels")
	public String getLabels(@RequestParam final String userId) throws IOException {	    
		getLogger().debug("MailController/getLabels : Appel de gmail service pour recuperer les labels lie au compte gmail du user");
		List<String> listeLabels =  gmailService.getLabels(userId);
		String labelsInString = "{'labels': [";
		if (!listeLabels.isEmpty()) {
			int i = 0;
			for (String label : listeLabels) {
				labelsInString += "{'label':'" + label + "'}";
				if(i++ != listeLabels.size() - 1){
					labelsInString += ",";
				}
			}
		}else{
			labelsInString += "{'error':'Aucuns labels trouvés !'}";
		}
		labelsInString += "]}";
		return labelsInString;
	}
	
	/**
	 * @return Le nombre de mails non lus
	 * @throws IOException
	 */
	@RequestMapping("/gmail/countUnreadForAllAccounts")
	public String getUnreadMessageCountForEachUsers(@RequestParam final String userKey) throws IOException {
		getLogger().debug("MailController/getUnreadMessageCountForEachUsers : "
				+ "récupération du nombre de mails non lus opur TOUT les comptes gmail liés à l'utilisateur");
		AppUser myUser = appUserRepository.findByUserKey(userKey);
		int unreadMessageCount = 0;
		for(GoogleAccount googleAccount : myUser.getGoogleAccounts()) {
			List<Message> messages =  gmailService
					.getUnreadMessageCount(
							googleAccount.getAccountName()
							);		
			for (Message message : messages) {
			    //TODO gut by Djer |POO| Tu peux utiliser la methode "size" sur une collection plutot que de la parcourir
				unreadMessageCount++;
			}
		}
		
//		for(MicrosoftAccount microsoftAccount : myUser.getMicrosoftAccounts()) {
//			OutlookService outlookService = OutlookServiceBuilder.getOutlookService(microsoftAccount.getAccessToken(), microsoftAccount.getAccountName());
//		
//			PagedResult<MicrosoftMessage> messages =  
//					outlookService.getMessagesUnread( )
//					.execute()
//					.body();
//					
//			for (Message message : messages) {
//				unreadMessageCount++;
//			}
//		}
		
		
		
	
		return "{'mailCount':'" + unreadMessageCount + "'}";

	}
	
	
	@RequestMapping("/outlook/mail")
	public String mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
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
		
		// Retrieve messages from the inbox
		String folder = "inbox";
		// Sort by time received in descending order
		String sort = "ReceivedDateTime DESC";
		// Only return the properties we care about
		String properties = "ReceivedDateTime,From,IsRead,Subject,BodyPreview";
		// Return at most 10 messages
		Integer maxResults = 10;
		
		try {
			PagedResult<MicrosoftMessage> messages = outlookService.getMessages(
					folder, sort, properties, maxResults)
					.execute().body();
			model.addAttribute("messages", messages.getValue());
		} catch (IOException e) {
			redirectAttributes.addFlashAttribute("error", e.getMessage());
			return "redirect:/index.html";
		}
		
		return "mail";
	}

}
