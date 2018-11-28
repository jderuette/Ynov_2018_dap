package fr.ynov.dap.services.microsoft;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.Event;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.data.PagedResult;
import fr.ynov.dap.data.TokenResponse;
import fr.ynov.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.data.interfaces.OutlookServiceInterface;

/**
 * The Class EventService.
 */
@Service
public class EventService extends MicrosoftService {

	/** The app user repo. */
	@Autowired
	AppUserRepository appUserRepo;

	/**
	 * Gets the next event.
	 *
	 * @param user the user
	 * @return the next event
	 */
	public Event getNextEvent(String user) {
		AppUser appU = appUserRepo.findByUserKey(user);
		Event nextEvent = null;

		if (appU == null) {
			LOG.error("unknow user");
			return null;
		}

		LOG.info("get microsoft events for " + user);

		for (MicrosoftAccount m : appU.getMicrosoftAccounts()) {
			Event e = getFirstEventByAccount(m);
			if (e != null) {
				nextEvent = e;
			}
		}

		return nextEvent;
	}

	/**
	 * Gets the all events.
	 *
	 * @param user the user
	 * @return the all events
	 */
	public ArrayList<Event> getAllEvents(String user) {
		ArrayList<Event> events = new ArrayList<Event>();

		AppUser appU = appUserRepo.findByUserKey(user);

		if (appU == null) {
			LOG.error("unknow user");
			return events;
		}

		LOG.info("get microsoft events for " + user);

		for (MicrosoftAccount m : appU.getMicrosoftAccounts()) {
			Event e = getFirstEventByAccount(m);
			if (e != null) {
				events.add(e);
			}
		}

		return events;
	}

	/**
	 * Gets the first event by account.
	 *
	 * @param account the account
	 * @return the first event by account
	 */
	private Event getFirstEventByAccount(MicrosoftAccount account) {
		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());
		OutlookServiceInterface outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

		String sort = "start/dateTime ASC";
		String properties = "organizer,subject,start,end";
		Integer maxResults = 1;
		String filter = "start/dateTime ge '" + Instant.now().toString() + "'";

		try {
			PagedResult<Event> e = outlookService.getEvents(sort, filter, properties, maxResults).execute().body();
			return e.getValue()[0];
		} catch (IOException e) {
			LOG.error("can't catch events", e.getLocalizedMessage());
			return null;
		}
	}
}
