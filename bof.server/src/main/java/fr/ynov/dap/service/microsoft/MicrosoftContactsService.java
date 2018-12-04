package fr.ynov.dap.service.microsoft;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.auth.AuthHelper;
import fr.ynov.dap.auth.TokenResponse;
import fr.ynov.dap.microsoft.Contact;
import fr.ynov.dap.microsoft.PagedResult;
import fr.ynov.dap.model.ContactModel;
import fr.ynov.dap.model.MailModel;
import fr.ynov.dap.model.MasterModel;
import fr.ynov.dap.model.microsoft.OutlookAccountModel;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.repository.microsoft.OutlookAccountRepository;

@Service
public class MicrosoftContactsService {

	@Autowired
	AppUserRepository appUserRepository;
	
	//TODO bof by Djer |POO| Si tu ne précise pas de modifier "hérite" de celui de la classe. Cet atribut est donc public ! Il n'est pas utilisé mais ton IDE ne peut pas te le signaler car public
	@Autowired
	OutlookAccountRepository outlookAccountRepository;
	
	public int countContacts(OutlookAccountModel accountModel) {
		TokenResponse tokens = AuthHelper.ensureTokens(accountModel.getToken(), accountModel.getTenantID());
		if (tokens == null) {
			return 0;
		}

		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

		// Retrieve messages from the inbox
		//TODO bof by Djer |IDE| Ton IDE t'indique que ca n'est pas/plus utilisé. Bug ? A supprimé ?
		String folder = "inbox";
		// Return at most 10 messages

		try {
			PagedResult<Contact> contacts = outlookService.getAllContacts().execute().body();
			return contacts.getValue().length;
		} catch (IOException e) {
			return 0;
		}
	}
	
}
