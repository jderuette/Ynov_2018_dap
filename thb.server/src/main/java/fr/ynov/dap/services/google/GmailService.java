package fr.ynov.dap.services.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.interfaces.AppUserRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class GmailService.
 */
@Service
public class GmailService extends GoogleService {

	/** The app user repo. */
  //TODO thb by Djer |POO| Précise le modifier sinon le même que celui de la classe
	@Autowired
	AppUserRepository appUserRepo;

	/**
	 * Gets the inbox labels.
	 *
	 * @param user the user
	 * @return the inbox labels
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public Label getInboxLabels(String user) throws IOException, GeneralSecurityException {
		NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, super.getJSON_FACTORY(),
				super.getCredentials(HTTP_TRANSPORT, user)).setApplicationName(getConfig().getApplicationName())
						.build();

		Label label = service.users().labels().get("me", "INBOX").execute();

		return label;
	}

	/**
	 * Gets the all emails.
	 *
	 * @param user the user
	 * @return the all emails
	 */
	public Integer getAllEmails(String user) {
		Integer nbEmails = 0;

		AppUser appU = appUserRepo.findByUserKey(user);

		if (appU == null) {
			return 0;
		}

		for (GoogleAccount g : appU.getGoogleAccounts()) {
			try {
			  //TODO thb by Djer |Log4J| Contextualise tes messages
				LOG.info(g.getName());
				nbEmails += getInboxLabels(g.getName()).getMessagesUnread();
			} catch (IOException | GeneralSecurityException e) {
			  //TODO thb by Djer |Log4J| Contextualise tes messages. Tu peux regrouper tes deux log en une seul
				LOG.error(g.getName());
				LOG.error("Error for couting emails", e);
			}
		}

		return nbEmails;
	}
}
