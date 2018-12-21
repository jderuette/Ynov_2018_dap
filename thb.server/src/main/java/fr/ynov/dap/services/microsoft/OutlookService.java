package fr.ynov.dap.services.microsoft;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.Message;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.data.PagedResult;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.data.interfaces.OutlookServiceInterface;

/**
 * The Class OutlookService.
 */

@Service
public class OutlookService extends MicrosoftService {

	/** The app user repo. */
  //TODO thb by Djer |POO| Précise le modifier sinon le même que celui de la classe
	@Autowired
	AppUserRepository appUserRepo;

	/**
	 * Gets the unread emails.
	 *
	 * @param user the user
	 * @return the unread emails
	 */
	public Integer getUnreadEmails(String user) {
		Integer nbUnreadEmails = 0;

		AppUser appU = appUserRepo.findByUserKey(user);

		if (appU == null) {
		    //TODO thb by Djer |Log4J| Contextualise tes messages
			LOG.error("unknow user");
			return nbUnreadEmails;
		}

		LOG.info("get microsoft emails for " + user);
		for (MicrosoftAccount m : appU.getMicrosoftAccounts()) {
			nbUnreadEmails += getEmailsCountByAccount(m);
		}

		return nbUnreadEmails;
	}

	/**
	 * Gets the messages.
	 *
	 * @param user     the user
	 * @param username the username
	 * @return the messages
	 */
	public Message[] getMessages(String user, String username) {
		AppUser appU = appUserRepo.findByUserKey(user);

		if (appU == null) {
		    //TODO thb by Djer |Log4J| Contextualise tes messages
			LOG.error("unknow user");
			return null;
		}

		Message[] messages = null;

		for (MicrosoftAccount m : appU.getMicrosoftAccounts()) {
			if (m.getName().equals(username)) {
				messages = getEmailMessageByAccount(m);
			}
		}

		return messages;
	}

	/**
	 * Gets the email message by account.
	 *
	 * @param account the account
	 * @return the email message by account
	 */
	private Message[] getEmailMessageByAccount(MicrosoftAccount account) {
		if (account.getToken() == null) {
		  //TODO thb by Djer |log4J| Une petite log ?
			// No tokens, user needs to sign in
			LOG.error("error", "please sign in to continue.");
			return null;
		}

		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());
		OutlookServiceInterface outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

		String folder = "inbox";
		String sort = "receivedDateTime DESC";
		String properties = "isRead, receivedDateTime, from, subject, bodyPreview";
		Integer maxResults = 5;

		PagedResult<Message> messages = null;
		//TODO thb by Djer |IDE| Ton IDE t'indique que ca n'est pas utilisé. Bug ? A supprimer ? 
		Integer nbUnreadEmails = 0;

		try {
			messages = outlookService.getMessages(folder, sort, properties, maxResults).execute().body();

			//TODO thb by Djer |POO| Evite les multiples return dans une même méthode
			return messages.getValue();
		} catch (IOException e) {
		  //TODO thb by Djer |Log4J| Contextualise tes messages
			LOG.error("error", e);
		}

		return null;
	}

	/**
	 * Gets the emails count by account.
	 *
	 * @param account the account
	 * @return the emails count by account
	 */
	private Integer getEmailsCountByAccount(MicrosoftAccount account) {
		if (account.getToken() == null) {
		  //TODO thb by Djer |log4J| Une petite log ?
			// No tokens, user needs to sign in
			LOG.error("error", "please sign in to continue.");
			return 0;
		}

		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());
		OutlookServiceInterface outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

		String folder = "inbox";
		String sort = "receivedDateTime DESC";
		String properties = "isRead";
		Integer maxResults = 5000;

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

			LOG.info(nbUnreadEmails);
			//TODO thb by Djer |POO| Evite les multiples return dans une même méthode
			return nbUnreadEmails;
		} catch (IOException e) {
		  //TODO thb by Djer |Log4J| Contextualise tes messages
			LOG.error("error", e);
		}

		return 0;
	}
}
