package fr.ynov.dap.web;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.service.google.GmailService;

@RestController
public class MailController extends BaseController {
	
	/**
	 * Service Gmail permettant d'appeler l'API Google
	 */
	@Autowired GmailService gmailService;

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
				unreadMessageCount++;
			}
		}
		
	
		return "{'mailCount':'" + unreadMessageCount + "'}";

	}

}
