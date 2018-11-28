package fr.ynov.dap.dap.service.microsoft;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.microsoft.Message;
import fr.ynov.dap.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.dap.data.microsoft.MicrosoftAccountRepository;
import fr.ynov.dap.dap.data.microsoft.PagedResult;
import fr.ynov.dap.dap.data.microsoft.TokenResponse;
import fr.ynov.dap.dap.data.microsoft.model.OutlookContact;
import fr.ynov.dap.dap.data.microsoft.model.OutlookEvent;
import fr.ynov.dap.dap.data.microsoft.model.OutlookFolder;
import fr.ynov.dap.dap.service.microsoft.builder.OutlookServiceBuilder;
import fr.ynov.dap.dap.service.microsoft.requestservice.OutlookRequestService;

/**
 * The Class OutlookService.
 */
@Service
public class OutlookService {

	/** The ms repository. */
	@Autowired
	private MicrosoftAccountRepository msRepository;

	/**
	 * Gets the mail inbox unread microsoft account.
	 *
	 * @param user
	 *            the user
	 * @return the mail inbox unread microsoft account
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Integer getMailInboxUnreadMicrosoftAccount(AppUser user) throws IOException {
		Integer unreadEmails = 0;

		if (user.getMicrosoftAccounts().size() == 0) {
			return 0;
		}
		for (MicrosoftAccount account : user.getMicrosoftAccounts()) {
			if (account == null) {
				return 0;
			}
			String email = account.getEmail();
			String tenantId = account.getTenantId();
			TokenResponse token = account.getToken();

			if (token == null || email.isEmpty() || email == null) {
				return 0;
			}
			TokenResponse newToken = OutlookCredentialService.ensureTokens(token, tenantId);

			OutlookRequestService outlookService = OutlookServiceBuilder.getOutlookService(newToken.getAccessToken(),
					email);

			OutlookFolder folder = outlookService.getOutlookFolder("inbox").execute().body();
			if (folder == null) {
				return 0;
			}

			unreadEmails += folder.getUnreadItemCount();
		}
		return unreadEmails;
	}

	/**
	 * Gets the next event.
	 *
	 * @param user
	 *            the user
	 * @return the next event
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public OutlookEvent getNextEvent(AppUser user) throws IOException {

		OutlookEvent nextEvent = null;

		for (MicrosoftAccount account : user.getMicrosoftAccounts()) {

			String tenantId = account.getTenantId();
			TokenResponse token = OutlookCredentialService.ensureTokens(account.getToken(), tenantId);
			OutlookRequestService outlookService = OutlookServiceBuilder.getOutlookService(token.getAccessToken(),
					account.getEmail());

			String sort = "start/dateTime ASC";
			String properties = "organizer,subject,start,end";
			Integer maxResults = 1;
			Date today = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String filter = "start/dateTime ge '" + dateFormat.format(today) + "'";

			PagedResult<OutlookEvent> events = outlookService.getEvents(sort, properties, maxResults, filter).execute()
					.body();

			if (events == null || events.getValue() == null || events.getValue().length == 0) {
				return null;
			}

			OutlookEvent event = events.getValue()[0];
			if (nextEvent != null) {
				if (event != null && event.getStart().getDateTime().before(nextEvent.getStart().getDateTime())) {
					nextEvent = event;
				}
			} else {
				nextEvent = event;
			}

		}
		return nextEvent;
	}

	/**
	 * Gets the next messages.
	 *
	 * @param user
	 *            the user
	 * @return the next messages
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public HashMap<String, PagedResult<Message>> getNextMessages(AppUser user) throws IOException {

		HashMap<String, PagedResult<Message>> mapMessages = new HashMap<String, PagedResult<Message>>();
		for (MicrosoftAccount account : user.getMicrosoftAccounts()) {
			String tenantId = account.getTenantId();
			TokenResponse token = account.getToken();
			TokenResponse newToken = OutlookCredentialService.ensureTokens(token, tenantId);
			OutlookRequestService outlookService = OutlookServiceBuilder.getOutlookService(newToken.getAccessToken(),
					account.getEmail());
			String folder = "inbox";
			String sort = "receivedDateTime DESC";
			String properties = "receivedDateTime,from,isRead,subject,bodyPreview";

			Integer maxResults = 5;

			mapMessages.put(account.getEmail(),
					outlookService.getMessages(folder, sort, properties, maxResults).execute().body());
		}

		return mapMessages;
	}

	/**
	 * Gets the nb contact from outlook.
	 *
	 * @param user
	 *            the user
	 * @return the nb contact from outlook
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public Integer getNbContactFromOutlook(AppUser user) throws IOException {
		if (user == null) {
			return 0;
		}

		if (user.getMicrosoftAccounts().size() == 0) {
			return 0;
		}
		Integer contacts = 0;
		for (MicrosoftAccount account : user.getMicrosoftAccounts()) {
			String tenantId = account.getTenantId();
			String email = account.getEmail();
			TokenResponse token = account.getToken();
			TokenResponse newToken = OutlookCredentialService.ensureTokens(token, tenantId);

			if (token == null || email.isEmpty() || email == null) {
				return 0;
			}
			OutlookRequestService outlookService = OutlookServiceBuilder.getOutlookService(newToken.getAccessToken(),
					email);

			String sort = "GivenName ASC";
			String properties = "GivenName,Surname,CompanyName,EmailAddresses";
			PagedResult<OutlookContact> outlookContact = outlookService.getContacts(sort, properties).execute().body();

			contacts += outlookContact.getValue().length;
		}
		return contacts;
	}

	/**
	 * Gets the contacts.
	 *
	 * @param user
	 *            the user
	 * @return the contacts
	 * @throws Exception
	 *             the exception
	 */
	public HashMap<String, PagedResult<OutlookContact>> getContacts(AppUser user) throws Exception {

		HashMap<String, PagedResult<OutlookContact>> mapContacts = new HashMap<String, PagedResult<OutlookContact>>();
		for (MicrosoftAccount account : user.getMicrosoftAccounts()) {
			String tenantId = account.getTenantId();
			TokenResponse newToken = OutlookCredentialService.ensureTokens(account.getToken(), tenantId);
			String email = account.getEmail();
			OutlookRequestService outlookService = OutlookServiceBuilder.getOutlookService(newToken.getAccessToken(),
					email);
			String sort = "givenName ASC";
			String properties = "givenName,surname,companyName,emailAddresses";
			mapContacts.put(email, outlookService.getContacts(sort, properties).execute().body());
		}
		return mapContacts;
	}
}
