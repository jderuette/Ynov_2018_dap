package fr.ynov.dap.services.microsoft;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.Contact;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.data.PagedResult;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.data.interfaces.OutlookServiceInterface;

@Service
public class MicrosoftContactService extends MicrosoftService {
	
	@Autowired
	AppUserRepository appUserRepo;
	
	public Integer getContacts(String user) {
		Integer nbUnreadEmails = 0;
		
		AppUser appU = appUserRepo.findByUserKey(user);
		
		if (appU == null) {
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

	    TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());

	    OutlookServiceInterface outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

	    String sort = "GivenName ASC";
	    String properties = "GivenName,Surname,CompanyName,EmailAddresses";
	    Integer maxResults = 1000;

	    try {
	      PagedResult<Contact> contacts = outlookService.getContacts(
	          sort, properties, maxResults)
	          .execute().body();
	      
	      return contacts.getValue().length;
	    } catch (IOException e) {
	      LOG.error(e);
	    }
		
		return 0;
	}
}
