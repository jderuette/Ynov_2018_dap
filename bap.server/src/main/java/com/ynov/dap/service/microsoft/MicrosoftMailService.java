package com.ynov.dap.service.microsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynov.dap.domain.AppUser;
import com.ynov.dap.domain.microsoft.MicrosoftAccount;
import com.ynov.dap.model.MailModel;
import com.ynov.dap.model.microsoft.Folder;
import com.ynov.dap.model.microsoft.Message;
import com.ynov.dap.model.microsoft.PagedResult;
import com.ynov.dap.model.microsoft.TokenResponse;
import com.ynov.dap.repository.AppUserRepository;
import com.ynov.dap.service.BaseService;

@Service
public class MicrosoftMailService extends BaseService {

	@Autowired
	private AppUserRepository appUserRepository;

	private Integer getNbUnreadEmails(final MicrosoftAccount account) throws IOException {
		if (account == null) {
			return 0;
		}

		String email = account.getEmail();
		TokenResponse tokens = account.getTokenResponse();
		
		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		Folder inboxFolder = outlookService.getFolder("inbox").execute().body();
		
		if (inboxFolder == null) {
			return 0;
		}

		getLogger().info("Nb messages unread " + inboxFolder.getUnreadItemCount() + " for the account '" + account.getName() + "'");
		return inboxFolder.getUnreadItemCount();
	}

	public MailModel getNbUnreadEmails(String userKey) throws IOException {
		AppUser appUser = appUserRepository.findByName(userKey);

		if (appUser == null) {
			getLogger().error("userKey '" + userKey + "' not found");
			return new MailModel(0);
		}
		
		MailModel mail = new MailModel();
		Integer nbUnreadMails = 0;

		for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {

			nbUnreadMails += getNbUnreadEmails(account);

		}
		mail.setUnRead(nbUnreadMails);

		getLogger().info("Total nb messages unread " + mail.getUnRead() + " for userKey : " + userKey);
		return mail;
	}

	private Message[] getEmails(MicrosoftAccount account) throws IOException {
		String email = account.getEmail();
		String tenantId = account.getTenantId();
		TokenResponse tokens = account.getTokenResponse();
		tokens = AuthHelper.ensureTokens(tokens, tenantId);

		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		String folder = "inbox";
		String sort = "receivedDateTime DESC";
		String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
		Integer maxResults = 10;

		PagedResult<Message> messages = null;
		messages = outlookService.getMessages(folder, sort, properties, maxResults).execute().body();

		return messages.getValue();
	}

	public List<Message[]> getEmails(String userKey) throws IOException {
		List<Message[]> messages = new ArrayList<Message[]>();

		AppUser appUser = appUserRepository.findByName(userKey);
		if (appUser == null) {
			getLogger().error("userKey '" + userKey + "' not found");
			return messages;
		}

		for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {
			messages.add(getEmails(account));
		}

		return messages;
	}

	@Override
	public String getClassName() {
		return MicrosoftMailService.class.getName();
	}

}
