package fr.ynov.dap.GmailPOO.metier;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.google.common.collect.ObjectArrays;

import fr.ynov.Outlook.auth.*;
import fr.ynov.Outlook.service.*;
import fr.ynov.dap.GmailPOO.data.Account;
import fr.ynov.dap.GmailPOO.data.MicrosoftAccount;

@Service
public class MailService {
	@Autowired
	Data data;

	public OutlookService ConnexionOutlook(Model model, HttpServletRequest request,String compte) {
	
			Account outlook = data.getAccount(compte);
			String email = outlook.getAdrMail();
			String tenantId = (((MicrosoftAccount) outlook)).getTenantId();
			TokenResponse ancienTokens = data.toTokenObjectById(outlook.getId());
			ancienTokens.setChanged(false);
			TokenResponse nouveauTokens = AuthHelper.ensureTokens(ancienTokens, tenantId);
			if (nouveauTokens.getError() == null) {
				if(nouveauTokens.isChanged())
				                 {data.Update(nouveauTokens, outlook.getId()); 
				                
				                 }
				return OutlookServiceBuilder.getOutlookService(nouveauTokens.getAccessToken(), email);
			}
		
		return null;

	}

	public Message[]  Listmail(OutlookService outlookService) throws IOException {

	  //TODO bes by Djer |IDE| TOn IDE t(indique que ca n'est pas utilisé. Buig ? A supprimer ? 
		int nb = 0;
		//TODO bes by Djer |IDE| TOn IDE t(indique que ca n'est pas utilisé. Buig ? A supprimer ? 
		int total = 0;

		// Retrieve messages from the inbox
		String folder = "inbox";
		// Sort by time received in descending order
		String sort = "receivedDateTime DESC";
		// Only return the properties we care about
		String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
		// Return at most 10 messages
		//TODO bes by Djer |IDE| TOn IDE t(indique que ca n'est pas utilisé. Buig ? A supprimer ? 
		Integer maxResults = 10;
		//TODO bes by Djer |IDE| TOn IDE t(indique que ca n'est pas utilisé. Buig ? A supprimer ? 
		List<Message> msg;
		PagedResult<Message> messages;
		messages = outlookService.getMessages(folder, sort, properties, 1000000).execute().body();
		

		return messages.getValue();

	}
	public int unreadMail(OutlookService outlookService) throws IOException {
		int i=0;
		Message[] messages=Listmail(outlookService);
		for (Message m : messages) {
			if (m.getIsRead() == false)
			i++;	

		}
		
		return i;
	}

}
