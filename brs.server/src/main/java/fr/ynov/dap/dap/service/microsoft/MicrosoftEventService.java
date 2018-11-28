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
			LOG.error("error", "please sign in to continue.");
			return null;
		}
	
		TokenResponse tokens = ensureTokens(account.getToken(), account.getTenantId());
		IOutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());
		
		String sort = "start/dateTime DESC";
	    // Only return the properties we care about
	    String properties = "organizer,subject,start,end";
	    // Return at most 10 events
	    Integer maxResults = 10;
	
		PagedResult<Event> event = null;
		
	
		try {
			event = outlookService.getEvents(
			          sort, properties, maxResults)
			          .execute().body();
			
			LOG.info(event);
			return event.getValue()[0];
		} catch (IOException e) {
			LOG.error("error", e);
		}
		
		return null;
	}

}
