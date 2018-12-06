package fr.ynov.dap.dap.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.repository.AppUserRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class GMailService.
 */
@Service
public class GoogleMailService extends GoogleService {
	
	/** The app user repo. */
	@Autowired
    private AppUserRepository appUserRepo;
	
	/** The log. */
	//TODO bot by Djer |Log4J| Static ? Au bon endroit ? Avec la bonne catégorie ("GoogleMailService" au lieu de "GoogleAccountService")
	private final Logger LOG = LogManager.getLogger(GoogleAccountService.class);

	/**
	 * Instantiates a new g mail service.
	 */
	public GoogleMailService() {
		super();
	}

	/**
	 * Gets the service.
	 *
	 * @param accountName the user id
	 * @return the service
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private Gmail getService(String accountName) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JACKSON_FACTORY,
				getCredentials(HTTP_TRANSPORT, accountName)).setApplicationName(configuration.getApplicationName()).build();
		return service;
	}

	
	/**
	 * Gets the nb mail inbox.
	 *
	 * @param accountName the account name
	 * @return the nb mail inbox
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	//TODO bot by Djer |POO| Pourquoi "public", seulement utilsié dans cette classe. Et aussi dans "welcome" mais Welcome pourrait utilsier la version "forAllAccount" (en modifiant aussi les "redirecte" des OAuthCallback)
	public int getNbMailInbox(final String accountName) throws IOException, GeneralSecurityException {
		Gmail service = getService(accountName);
		Label listResponse = service.users().labels().get("me", "INBOX").execute();
		return listResponse.getMessagesUnread();
	}
	
	/**
	 * Gets the nb mail inbox for all account.
	 *
	 * @param userKey the user key
	 * @return the nb mail inbox for all account
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public int getNbMailInboxForAllAccount(final String userKey) throws IOException, GeneralSecurityException {
		AppUser user = appUserRepo.findByName(userKey);
		int nbUnreadMail = 0;
		
		if(user != null) {
		    //TODO bot by Djer |POO| "currentData" c'est un Account ? donc "account" serait pas mal comme nom de variable ?
			for (GoogleAccount currentData : user.getGoogleAccounts()) {
	            nbUnreadMail += getNbMailInbox(currentData.getName());
	        }
			//TODO bot by Djer |POO| Evite les multiples return dans une même méthode
			return nbUnreadMail;
		}
		//TODO bot by Djer |Log4J| Contextualise tes messages (" for userkey : " + userKey)
		//TODO bot by Djer |log4J| Pas vraiment un Warning, on a le droit de ne pas avoir de mail en retard de lecture. Un level Info serait suffisant
		LOG.warn("No user found !");
		
		return 0;
	}
}
