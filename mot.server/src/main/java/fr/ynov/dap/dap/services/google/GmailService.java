package fr.ynov.dap.dap.services.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.dap.data.google.AppUser;
import fr.ynov.dap.dap.data.google.GoogleAccount;
import fr.ynov.dap.dap.data.interfaces.AppUserRepository;

/**
 * The Class GmailService.
 */
@Service
public class GmailService extends GoogleService {
	@Autowired
	AppUserRepository appUserRepository;

	protected Logger LOG = LogManager.getLogger(GmailService.class);

	public GmailService() throws IOException, GeneralSecurityException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Gets the service.
	 *
	 * @param user the user
	 * @return the label
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public Label getLabel(String user) throws IOException, GeneralSecurityException {
		NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, user))
				.setApplicationName(getCfg().getApplicationName()).build();

		Label label = service.users().labels().get("me", "INBOX").execute();

		return label;
	}

	/**
	 * Nbr email unread.
	 *
	 * @param user the user
	 * @return the nb UnreadEmails
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException              Signals that an I/O exception has occurred.
	 */
	public Integer nbrEmailUnread(String user) throws IOException, GeneralSecurityException {
		Integer response = 0;

		AppUser appUser = appUserRepository.findByUserKey(user);

		if (appUser == null) {
			return response;
		}

		for (GoogleAccount g : appUser.getAccounts()) {
			try {
				if (getLabel(g.getName()).getMessagesUnread() != null) {
					response += getLabel(g.getName()).getMessagesUnread();
				}
			} catch (IOException | GeneralSecurityException e) {
				LOG.error("Error nombre de mails", e);
			}
		}
		return response;
	}
}
