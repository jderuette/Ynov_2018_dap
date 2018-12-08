package fr.ynov.dap.dap.service.microsoft;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.auth.TokenResponse;
import fr.ynov.dap.dap.controller.google.CalendarController;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.service.microsoft.ObjectUtils.Message;
import fr.ynov.dap.dap.service.microsoft.ObjectUtils.PagedResult;

@Service
public class MicrosoftOutlookService extends MicrosoftService {
	

	
	@Autowired
	AppUserRepository appUserRepo;

	public Integer getNbMailUnread(String user) {
		Integer nbUnreadEmails = 0;
		
		AppUser appU = appUserRepo.findByUserkey(user);
		
		if (appU == null) {
		  //TODO brs by Djer |Log4J| Contextualise tes messages
			LOG.error("unknow user");
			return nbUnreadEmails;
		}
		
		LOG.info("get microsoft emails for " + user);
		for(MicrosoftAccount m: appU.getMicrosoftAccounts()) {
			nbUnreadEmails += getEmailsByAccount(m);
		}
		
		return nbUnreadEmails;
	}
	public Message[] getListEmail(String user,String name) {
		Message [] message_list = null;
		
		//TODO brs by Djer |POO| Beaucoup de code en commun avec la méthdoe juste au dessus. Tu pourias créer un méthode "getMicrosfotAccount(String userKey)" Cette méthode pourais même être partagé par toutes tes méthode "microisoft by userKey"
		AppUser appU = appUserRepo.findByUserkey(user);
		
		if (appU == null) {
		  //TODO brs by Djer |Log4J| Contextualise tes messages
			LOG.error("unknow user");
			return message_list;
		}
		
		LOG.info("get microsoft emails for " + user);
		for(MicrosoftAccount m: appU.getMicrosoftAccounts()) {
			if(m.getName().equals(name)) {
				message_list = getListEmail(m);
			}
			
		}
		
		return message_list;
	}
	private Integer getEmailsByAccount(MicrosoftAccount account) {
	    
	    //TODO brs by Djer |POO| Beaucoup de code dupliqué avec la méthode "getListEmail()". Tu aurais pu passer en paramètre à "getListEmail()" les "properties" à récupérer et mutualiser une grande partie du code
		if (account.getToken() == null) {
			// No tokens, user needs to sign in
		  //TODO brs by Djer |POO| Devrait être une exception
			LOG.error("error", "please sign in to continue.");
			return 0;
		}
	
		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());
		IOutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());
		
		String folder = "inbox";
		String sort = "receivedDateTime DESC";
		String properties = "isRead";
		Integer maxResults = 10;
	
		PagedResult<Message> messages = null;
		Integer nbUnreadEmails = 0;
	
		try {
			messages = outlookService.getMessages(folder, sort, properties, maxResults).execute().body();
			Message[] val = messages.getValue();
	
			for (int i = 0; i < val.length; i++) {
				if (!val[i].getIsRead()) {
					nbUnreadEmails++;
				}
			}
			
			//TODO brs by Djer |Log4J| Contextualise tes messages
			LOG.info(nbUnreadEmails);
			return nbUnreadEmails;
		} catch (IOException e) {
		  //TODO brs by Djer |Log4J| Contextualise tes messages
			LOG.error("error", e);
		}
		
		return 0;
	}
	
	public Message[] getListEmail(MicrosoftAccount account) {
 		Message[] response;
 		if (account.getToken() == null) {
 			// No tokens, user needs to sign in
 			LOG.error("error", "please sign in to continue.");
 			return null;
 		}
 	
 		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());
 		IOutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());
 		
 		// Retrieve messages from the inbox
 		String folder = "inbox";
 		// Sort by time received in descending order
 		String sort = "receivedDateTime DESC";
 		String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
 		Integer maxResults = 10;
 	
 		PagedResult<Message> messages = null;
 	
 		try {
 			
 			messages = outlookService.getMessages(folder, sort, properties, maxResults).execute().body();
 			
 			response = messages.getValue();
 			//TODO brs by Djer |POO| Evite lesm ultiple return dans une même méthode. Deplus dans cette méthode tu renvois deja "response" (à la fin de la méthode).
 			return response;
 		} catch (IOException e) {
 			response = null;
 			//TODO brs by Djer |Log4J| Bien il ya du contexte, mais il indique une information fuasse "count Email" alors que cette méthode "récupère les emails"
 			LOG.error("Error during CountMicrosoftMail", e);
 		}
 		
 		return response;
 	}

}
