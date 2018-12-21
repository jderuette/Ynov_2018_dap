package fr.ynov.dap.dap.services.microsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.OutlookAccount;
import fr.ynov.dap.dap.models.MailFolder;
import fr.ynov.dap.dap.models.Message;
import fr.ynov.dap.dap.models.PagedResult;

@Service
public class OutlookMailService {
  //TODO scb by Djer |POO| Pourquoi public ?
	@Autowired
	AppUserRepository repository;
	
	public Integer getNbUnreadEmails(String userid) {
		AppUser appUser = repository.findByName(userid);
		List<OutlookAccount> otAccounts = appUser.getOutlookAccounts();
		Integer total = 0;
		for(int i=0; i < otAccounts.size(); i++) {
		    OutlookService outlookService = OutlookServiceFactory.getOutlookService(otAccounts.get(i).getIdToken().getAccessToken());
		    try {
		        //TODO scb by Djer |API Microsoft| Filtre sur les "unRead"
			    MailFolder mailFolder = outlookService.getMailFolders("INBOX").execute().body();
			    total += mailFolder.getUnreadItemCount();
		    }catch(IOException e) {
		      //TODO scb by Djer |Log4J| Une petite log ?
		        //TODO scb by Djer |Gestion Exception| Es-tu sure de vouloire intérompre, et ne rien indiquer à l'appelant ?
		    	return -1;
		    }
		    
		}
		
		
		
		return total;
	}

	public List<PagedResult<Message>> getMails(String userid) {
		AppUser appUser = repository.findByName(userid);
		//TODO scb by Djer |POO| Véririe que appUser ne soit pas null
		List<OutlookAccount> otAccounts = appUser.getOutlookAccounts();
		List<PagedResult<Message>> messages = new ArrayList<PagedResult<Message>>();
		
		for(int i=0; i < otAccounts.size(); i++) {
		    OutlookService outlookService = OutlookServiceFactory.getOutlookService(otAccounts.get(i).getIdToken().getAccessToken());
		    // Retrieve messages from the inbox
		    String folder = "inbox";
		    // Sort by time received in descending order
		    String sort = "receivedDateTime DESC";
	        // Only return the properties we care about
	        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
		    // Return at most 10 messages
		    Integer maxResults = 10;
		    try {
			    PagedResult<Message> pMessage = outlookService.getMessages(folder, sort, properties, maxResults).execute().body();
			    messages.add(pMessage);
		    }catch(IOException e) {
		      //TODO scb by Djer |Log4J| Une petite log ?
		      //TODO scb by Djer |Gestion Exception| Es-tu sure de vouloire intérompre, et ne rien indiquer à l'appelant ?
		    	return null;
		    }
		    
		}
		
		return messages;
	}

}
