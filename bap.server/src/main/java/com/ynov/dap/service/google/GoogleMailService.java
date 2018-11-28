package com.ynov.dap.service.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.ynov.dap.domain.AppUser;
import com.ynov.dap.domain.google.GoogleAccount;
import com.ynov.dap.model.MailModel;
import com.ynov.dap.repository.AppUserRepository;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleMailService extends GoogleService {

	@Autowired
	private AppUserRepository appUserRepository;

	/**
	 * Gets the nb unread emails.
	 *
	 * @param user the user
	 * @return the nb unread emails
	 * @throws Exception   the exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public MailModel getNbUnreadEmails(final String userKey) throws Exception, IOException {
		final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		AppUser appUser = appUserRepository.findByName(userKey);

		if (appUser == null) {
			getLogger().error("userKey '" + userKey + "' not found");
			return new MailModel(0);
		}

		List<GoogleAccount> accounts = appUser.getGoogleAccounts();
		MailModel mail = new MailModel();
		Integer nbUnreadMails = 0;

		for (GoogleAccount account : accounts) {
			Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(account.getName()))
					.setApplicationName(getConfig().getApplicationName()).build();

			Label label = service.users().labels().get("me", "INBOX").execute();
			nbUnreadMails += label.getMessagesUnread();
		}
		mail.setUnRead(nbUnreadMails);
		getLogger().info("nb messages unread " + mail.getUnRead() + " for user : " + userKey);

		return mail;
	}

	@Override
	public String getClassName() {
		return GoogleMailService.class.getName();
	}

}
