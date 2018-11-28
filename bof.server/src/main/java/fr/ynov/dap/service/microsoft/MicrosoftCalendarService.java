package fr.ynov.dap.service.microsoft;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import fr.ynov.dap.auth.AuthHelper;
import fr.ynov.dap.auth.TokenResponse;
import fr.ynov.dap.microsoft.Event;
import fr.ynov.dap.microsoft.PagedResult;
import fr.ynov.dap.model.EventModel;
import fr.ynov.dap.model.microsoft.OutlookAccountModel;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.repository.microsoft.OutlookAccountRepository;

@Service
public class MicrosoftCalendarService {
	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	OutlookAccountRepository outlookAccountRepository;
	
	public EventModel getNextEvent(OutlookAccountModel accountModel) {
		
		TokenResponse tokens = AuthHelper.ensureTokens(accountModel.getToken(), accountModel.getTenantID());
		if (tokens == null) {
			// No tokens in session, user needs to sign in
			return null;
		}
				
		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());
		
		// Sort by start time in descending order
		String sort = "start/DateTime DESC";
		// Only return the properties we care about
		String properties = "organizer,subject,start,end";
		// Return at most 10 events
		Integer maxResults = 1;
		
		try {
			PagedResult<Event> events = outlookService.getEvents(
					sort, properties, maxResults)
					.execute().body();
			return new EventModel(events.getValue()[0].getSubject(), events.getValue()[0].getStart().getDateTime(), events.getValue()[0].getEnd().getDateTime());
		} catch (IOException e) {
			return null;
		}
	}
}
