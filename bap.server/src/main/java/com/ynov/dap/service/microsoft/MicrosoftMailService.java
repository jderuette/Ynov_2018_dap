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

@Service
public class MicrosoftMailService {

	@Autowired
	private AppUserRepository appUserRepository;

	private Integer getNbUnreadEmails(final MicrosoftAccount account) {

		if (account == null) {
			return 0;
		}

		String email = account.getEmail();
		String tenantId = account.getTenantId();
		TokenResponse tokens = account.getTokenResponse();

		System.out.println(email);
		System.out.println(tenantId);
		System.out.println(tokens);

		/*
		 * if (StrUtils.isNullOrEmpty(tenantId) || StrUtils.isNullOrEmpty(email)
		 * || tokens == null) { return 0; }
		 */

		// TokenResponse newTokens = getToken(microsoftAccount);

		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		Folder inboxFolder = null;
		try {
			inboxFolder = outlookService.getFolder("inbox").execute().body();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return inboxFolder.getUnreadItemCount();
	}

	public MailModel getNbUnreadEmails(String user) {

		AppUser appUser = appUserRepository.findByName(user);

		MailModel mail = new MailModel();
		Integer nbUnreadMails = 0;

		for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {

			nbUnreadMails += getNbUnreadEmails(account);

		}
		mail.setUnRead(nbUnreadMails);

		// getLogger().info("nb messages unread " + mail.getUnRead() + " for
		// user : " + user);
		return mail;
	}

	private Message[] getEmails(MicrosoftAccount account) {

		System.out.println(account.getName());
		
		String email = account.getEmail();
		String tenantId = account.getTenantId();
		TokenResponse tokens = account.getTokenResponse();
		tokens = AuthHelper.ensureTokens(tokens, tenantId);

		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		// Retrieve messages from the inbox
		String folder = "inbox";
		// Sort by time received in descending order
		String sort = "receivedDateTime DESC";
		// Only return the properties we care about
		String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
		// Return at most 10 messages
		Integer maxResults = 10;

		PagedResult<Message> messages = null;
		try {
			messages = outlookService.getMessages(folder, sort, properties, maxResults).execute().body();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(messages.getValue()[0].getSubject());

		return messages.getValue();
	}

	public List<Message[]> getEmails(String user) {
		AppUser appUser = appUserRepository.findByName(user);

		List<Message[]> messages = new ArrayList<Message[]>();
		
		for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {
			messages.add(getEmails(account));
		}
		
		System.out.println(messages);
		
		return messages;

	}

	/*
	 * @Override public String getClassName() { return
	 * GoogleMailService.class.getName(); }
	 */

}
