package fr.ynov.dap.service.microsoft;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.auth.AuthHelper;
import fr.ynov.dap.auth.TokenResponse;
import fr.ynov.dap.microsoft.Message;
import fr.ynov.dap.microsoft.OutlookMailFolder;
import fr.ynov.dap.microsoft.PagedResult;
import fr.ynov.dap.model.MailModel;
import fr.ynov.dap.model.MasterModel;
import fr.ynov.dap.model.microsoft.OutlookAccountModel;
import fr.ynov.dap.repository.AppUserRepository;

@Service
public class MicrosoftMailService {

	@Autowired
	AppUserRepository appUserRepository;

	public Message[] getMails(final OutlookAccountModel accountModel) {

		TokenResponse tokens = accountModel.getToken();
		String tenantId = accountModel.getTenantID();
		if (tokens == null) {
			// No tokens in session, user needs to sign in
			return null;
		}

		tokens = AuthHelper.ensureTokens(tokens, tenantId);

		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

		// Retrieve messages from the inbox
		String folder = "inbox";
		// Sort by time received in descending order
		String sort = "receivedDateTime DESC";
		// Only return the properties we care about
		String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
		// Return at most 10 messages
		Integer maxResults = 10;

		try {
			PagedResult<Message> messages = outlookService.getMessages(folder, sort, properties, maxResults).execute()
					.body();
			return messages.getValue();
		} catch (IOException e) {
		    //TODO bof by Djer |Log4J| Une petite log pour éviter que cette exception soit silencieusement étouffée
			return null;
		}
	}

	public MasterModel getNumberOfInboxMail(final OutlookAccountModel accountModel) {
		TokenResponse tokens = AuthHelper.ensureTokens(accountModel.getToken(), accountModel.getTenantID());
		if (tokens == null) {
			// No tokens in session, user needs to sign in
			return new MailModel(0);
		}


		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

		// Retrieve messages from the inbox
		String folder = "inbox";
		// Return at most 10 messages

		try {
			OutlookMailFolder mailFolder = outlookService.getMailFolders(folder).execute().body();
			return new MailModel(mailFolder.getUnreadItemCount());
		} catch (IOException e) {
		  //TODO bof by Djer |Log4J| Une petite log pour savoir qu'on a renvoyé une information potentiellement fausse à l'utilisateur ? 
			return new MailModel(0);
		}
	}
}
