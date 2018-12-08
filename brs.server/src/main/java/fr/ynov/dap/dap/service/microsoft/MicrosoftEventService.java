package fr.ynov.dap.dap.service.microsoft;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.auth.TokenResponse;
import fr.ynov.dap.dap.controller.google.CalendarController;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.service.microsoft.ObjectUtils.Event;
import fr.ynov.dap.dap.service.microsoft.ObjectUtils.Message;
import fr.ynov.dap.dap.service.microsoft.ObjectUtils.PagedResult;

@Service
public class MicrosoftEventService extends MicrosoftService {

	
	@Autowired
	AppUserRepository appUserRepo;
	
	public Event getNextEvent(String user) {
		Event nextEvent = null;
		
		AppUser appU = appUserRepo.findByUserkey(user);
		
		if (appU == null) {
		  //TODO brs by Djer |Log4J| Contextualise tes messages
			LOG.error("unknow user");
			return nextEvent;
		}
		
		LOG.info("get microsoft next event for " + user);
		for(MicrosoftAccount m: appU.getMicrosoftAccounts()) {
			nextEvent = getNExtEventByAccount(m);
		}
		
		return nextEvent;
	}
	
	private Event getNExtEventByAccount(MicrosoftAccount account) {
		if (account.getToken() == null ) {
			// No tokens, user needs to sign in
		    //TODO brs by Djer |POO| Devrait être une exception
			LOG.error("error", "please sign in to continue.");
			return null;
		}
	
		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());
		IOutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());
		
		String sort = "start/dateTime DESC";
	    // Only return the properties we care about
	    String properties = "organizer,subject,start,end";
	    // Return at most 10 events
	    //TODO brs by Djer |API Microsoft| Tu pourrais limiter à 1 event comme tu ne veux que le prochain.
	    Integer maxResults = 10;
	
		PagedResult<Event> event = null;
		
	
		try {
		    //TODO brs by Djer |API Microsoft| Tu ne précise pas de date début de recherhe ("now")? Est-ce que par defaut Microsoft ne renvoi que ceux à venir ?  
			event = outlookService.getEvents(
			          sort, properties, maxResults)
			          .execute().body();
			
			//TODO brs by Djer |Log4J| Conextualise tes messages
			LOG.info(event);
			//TODO brs by Djer |POO| Evite les multiple return dans une même méthode
			//TODO brs by Djer |API Microsoft| Plante si pas d'évennement à venir (ArrayOutOfBoundException)
			return event.getValue()[0];
		} catch (IOException e) {
		  //TODO brs by Djer |Log4J| Conextualise tes messages
			LOG.error("error", e);
		}
		
		return null;
	}

}
