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
import fr.ynov.dap.dap.service.microsoft.ObjectUtils.Contact;
import fr.ynov.dap.dap.service.microsoft.ObjectUtils.Message;
import fr.ynov.dap.dap.service.microsoft.ObjectUtils.PagedResult;

@Service
public class MicrosoftContactService extends MicrosoftService {
	
	
	@Autowired
	AppUserRepository appUserRepo;
	
	

	public Integer getNbTotalContact(String user) {
		Integer nbUnreadEmails = 0;
		
		AppUser appU = appUserRepo.findByUserkey(user);
		
		if (appU == null) {
		    //TODO brs by Djer |Log4J| Contextualise tes messages
			LOG.error("unknow user");
			return nbUnreadEmails;
		}
		
		LOG.info("get microsoft emails for " + user);
		for(MicrosoftAccount m: appU.getMicrosoftAccounts()) {
			nbUnreadEmails += getContactByAccount(m);
		}
		
		return nbUnreadEmails;
	}
	private Integer getContactByAccount(MicrosoftAccount account) {
		if (account.getToken() == null) {
			// No tokens, user needs to sign in
		    //TODO brs by Djer |Log4J| Les logs sont dans un "fichier" pour les developpeurs, pas pour les utilisateurs. Contextualise pour indiquer au developpeur qu'un utilisateur à essayé d'appeler une méthode SANS s'identifier.
		    //TODO brs by Djer |Gestion Exception| Ta méthode pourrais lever une excpetion, qui pourais être interceptée par le controller qui pourait alors prendre la décision d'afficher un message d'erreur, et/ou re-diriger vers la page de login
			LOG.error("error", "please sign in to continue.");
			return 0;
		}
	
		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());
		IOutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());
		
		String sort = "GivenName ASC";
	    // Only return the properties we care about
		//TODO brs by Djer |API Microsoft| Tu n'a pas besoi nde toutes ces propriétées comme tu en souhaite QUE compter. Il y a aussi une méthode d'API "count" spécifique dans l'API contact
	    String properties = "GivenName,Surname,CompanyName,EmailAddresses";
	    // Return at most 10 contacts
	    Integer maxResults = 10;
	
		PagedResult<Contact> contacts = null;
		Integer nbContact = 0;
	
		try {
			contacts = outlookService.getContacts(
			          sort, properties, maxResults)
			          .execute().body();
			 nbContact = contacts.getValue().length;
	
			
			//TODO brs by Djer |Log4J| Conextualise tes messages
			LOG.info(nbContact);
			//TODO brs by Djer |POO| Evite les multiple return dans une même méthode
			return nbContact;
		} catch (IOException e) {
		  //TODO brs by Djer |Log4J| Conextualise tes messages
			LOG.error("error", e);
		}
		
		return 0;
	}

}
