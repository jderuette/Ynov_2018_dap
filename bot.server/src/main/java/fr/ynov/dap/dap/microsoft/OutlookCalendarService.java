package fr.ynov.dap.dap.microsoft;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.auth.AuthHelper;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.OutlookAccount;
import fr.ynov.dap.dap.data.Token;
import fr.ynov.dap.dap.google.GoogleAccountService;
import fr.ynov.dap.dap.model.OutlookEvent;
import fr.ynov.dap.dap.model.OutlookPagedResult;
import fr.ynov.dap.dap.repository.AppUserRepository;

/**
 * The Class OutlookCalendarService.
 */
@Service
public class OutlookCalendarService {

	/** The log. */
  //TODO bot by Djer |Log4J| Static ? Au bon endroit ? Avec la bonne catégorie ("OutlookCalendarService" au lieu de "GoogleAccountService")
	private final Logger LOG = LogManager.getLogger(GoogleAccountService.class);

	/** The app user repo. */
	@Autowired
	private AppUserRepository appUserRepo;

	/**
	 * Gets the next event.
	 *
	 * @param account the account
	 * @return the next event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private OutlookEvent getNextEvent(final OutlookAccount account) throws IOException {

		if (account == null) {
			LOG.warn("MicrosoftAccount is null");
			return null;
		}

		Token tokens = AuthHelper.ensureTokens(account.getToken(), account.getTenantId());

		String sort = "start/dateTime ASC";
		String properties = "Organizer,subject,start,end,Attendees";
		Integer maxResults = 1;

		OutlookService outlookService = OutlookServiceBuilder
				.getOutlookService(tokens.getAccessToken());

		OutlookPagedResult<OutlookEvent> events = outlookService
				.getEvents(sort, properties, maxResults)
				.execute()
				.body();

		if (events.getValue().length == 0) {
		  //TODO bot by Djer |POO| Evite les multiples return dans une même méthode (si lenght > 0 : nextEvent = events.getValue()[0], et pas besoin de else, ni de multiple return)
			return null;
		}
		OutlookEvent nextEvent = events.getValue()[0];
		return nextEvent;
	}

	/**
	 * Gets the next event for all accounts.
	 *
	 * @param userKey the user key
	 * @return the next event for all accounts
	 */
	public OutlookEvent getNextEventForAllAccounts(String userKey) {
		AppUser appUser = appUserRepo.findByName(userKey);
		List<OutlookEvent> events = new ArrayList<>();
		for (OutlookAccount account : appUser.getOutlookAccounts()) {
			try {
				events.add(getNextEvent(account));
			} catch (IOException e) {
			  //TODO bot by Djer |Log4J| Contextualise tes messages (" for userkey : " + userKey)
				LOG.error("Error when trying get next event for all accounts", e);
			}
		}
		
		if(events.size() == 0) {
		  //TODO bot by Djer |Log4J| Contextualise tes messages (" for userkey : " + userKey)
			LOG.warn("No events available for outlook.");
			//TODO bot by Djer |POO| Evite les multiples return dans une même méthode
			return null;
		}

		//TODO bot by Djer |POO| Ta variable "next" contient en faite le précédent lors de la boucle ...
		OutlookEvent nextEvent = events.get(0);

		for (OutlookEvent event : events) {
		    //TODO bot by Djer |POO| Est-ce a cause de ce nom de varialbe ? Mais j'ai l'impression que tu fait l'inverse que ce qui est demandé.
			if (nextEvent.getStart().getDateTime().getTime() > 
			event.getStart().getDateTime().getTime()) {
				nextEvent = event;
			}
		}
		
		return nextEvent;
	}
}
