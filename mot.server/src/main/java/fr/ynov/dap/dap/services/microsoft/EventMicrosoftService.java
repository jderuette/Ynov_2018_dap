package fr.ynov.dap.dap.services.microsoft;

import java.io.IOException;
import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.data.google.AppUser;
import fr.ynov.dap.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.dap.data.interfaces.OutlookInterface;
import fr.ynov.dap.dap.data.microsoft.EventMicrosoft;
import fr.ynov.dap.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.dap.data.microsoft.PagedResult;
import fr.ynov.dap.dap.data.microsoft.TokenResponse;

/**
 * The Class EventMicrosoftService.
 */
@Service
public class EventMicrosoftService extends MicrosoftService {

	@Autowired
	AppUserRepository appUserRepository;

	protected Logger LOG = LogManager.getLogger(EventMicrosoftService.class);

	/**
	 * Gets the next events.
	 *
	 * @param user the user
	 * @return the next events for all Microsoft account
	 */
	public EventMicrosoft getNextEvents(String user) {
		EventMicrosoft response = null;

		AppUser appUser = appUserRepository.findByUserKey(user);

		if (appUser == null) {
			return response;
		}

		for (MicrosoftAccount m : appUser.getMicrosoftAccount()) {
			EventMicrosoft e = getFirstEventByAccount(m);
			if (e != null) {
				response = e;
			}
		}

		return response;
	}

	/**
	 * Gets the first event by account.
	 *
	 * @param account the account
	 * @return the first event by Microsoft Account
	 */
	private EventMicrosoft getFirstEventByAccount(MicrosoftAccount account) {
		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());
		OutlookInterface outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), null);

		String sort = "start/dateTime ASC";
		String properties = "organizer,subject,start,end";
		Integer maxResults = 1;
		String filter = "start/dateTime ge '" + Instant.now().toString() + "'";

		try {
			PagedResult<EventMicrosoft> e = outlookService.getEvents(sort, filter, properties, maxResults).execute()
					.body();

			if (e.getNextPageLink() == null) {
			    //TOD mot by Djer |API Microsoft| Pourquoi tout arreter si les résultats tiennent sur une seul page ? 
				return null;
			}
			//TODO mot by Djer |API Microsoft| Tu auras un "ArrayOutOfBoundException" si pas d'évènnements
			return e.getValue()[0];

		} catch (IOException e) {
		    //TODO mot by Djer |Log4J| Contextualise tes messages (" for microsofot accountname : " + account)
            //TODO mot by Djer |Log4J| Evite d'ajouter le message de l'excpetion dans ton propre message. Met la cause ("e") en deuxième paramètre et Log4J s'occupera d'afficher le message et la pile de la cause.
			LOG.error("Error during getFirstEventByAccount", e.getMessage());
			//TODO mot by Djer |POO| Evite les multiples return dans une même méthode
			return null;
		}
	}
}
