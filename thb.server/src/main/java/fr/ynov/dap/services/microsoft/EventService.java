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
  //TODO thb by Djer |POO| Précise le modifier sinon le même que celui de la classe
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
		  //TODO thb by Djer |Log4J| Contextualise tes messages
			LOG.error("unknow user");
			return null;
		}

		LOG.info("get microsoft events for " + user);

		for (MicrosoftAccount m : appU.getMicrosoftAccounts()) {
			Event e = getFirstEventByAccount(m);
			//TODO thb by Djer |POO| Ton algo est faux, l'event du dernier  microsoft account n'est pas forcément le prochain. Tu DOIS comparer les dates
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
		  //TODO thb by Djer |Log4J| Contextualise tes messages
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
	//TODO thb by Djer |POO| Nom de méthode faux, le "premier à partir de maintenant" c'est le "next". Le "first" serait le tout premier ajouté dans le calendrier de cette utilisateur
	private Event getFirstEventByAccount(MicrosoftAccount account) {
		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());
		OutlookServiceInterface outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

		String sort = "start/dateTime ASC";
		String properties = "organizer,subject,start,end";
		Integer maxResults = 1;
		String filter = "start/dateTime ge '" + Instant.now().toString() + "'";

		try {
			PagedResult<Event> e = outlookService.getEvents(sort, filter, properties, maxResults).execute().body();
			//TODO thb by Djer |API Microsoft| Si pas d'évennement à venir tu auras un "ArrayOutOfBoundException"
			//TODO thb by Djer |POO| Evite les multiples returns dans une même méthode
			return e.getValue()[0];
		} catch (IOException e) {
		    //TODO thb by Djer |Log4J| ne met pas le meessage del'exception dans "ton" message, ajoute l'exeption en deuxième paramètre. Log4J prévcisera le message de l'exception et sa pile.
			LOG.error("can't catch events", e.getLocalizedMessage());
			return null;
		}
	}
}
