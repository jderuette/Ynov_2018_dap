package com.ynov.dap.service.microsoft;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ynov.dap.domain.AppUser;
import com.ynov.dap.domain.microsoft.MicrosoftAccount;
import com.ynov.dap.model.CalendarModel;
import com.ynov.dap.model.microsoft.Event;
import com.ynov.dap.model.microsoft.PagedResult;
import com.ynov.dap.model.microsoft.TokenResponse;
import com.ynov.dap.repository.AppUserRepository;
import com.ynov.dap.service.BaseService;

@Service
public class MicrosoftCalendarService extends BaseService {

	@Autowired
	private AppUserRepository appUserRepository;

	private Event getEvent(final MicrosoftAccount account) throws Exception {
		String email = account.getEmail();
		String tenantId = account.getTenantId();
		TokenResponse tokens = account.getTokenResponse();

		tokens = AuthHelper.ensureTokens(tokens, tenantId);

		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		String sort = "start/dateTime ASC";
		String filterDate = "start/dateTime ge '" + Instant.now().toString() + "'";
		String properties = "organizer,subject,start,end";
		Integer maxResults = 1;

		Event[] events = outlookService.getEvents(sort, filterDate, properties, maxResults).execute().body().getValue();
		
		if (events == null || events.length != 1) {
			return null;
		}
		
		return events[0];
	}

	public CalendarModel getNextEvent(String userKey) throws Exception {
		AppUser appUser = appUserRepository.findByName(userKey);
		
		if (appUser == null) {
			getLogger().error("userKey '" + userKey + "' not found");
			return new CalendarModel();
		}
		
		List<Event> events = new ArrayList<Event>();

		for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {
			events.add(getEvent(account));
		}
		Event finalEvent = null;
		
        if (events.isEmpty() || events.get(0) == null) {
            getLogger().info("No upcoming events found for userKey : " + userKey);
            return new CalendarModel();
        } else if (events.size() == 1) {
			Event event = events.get(0);
			return new CalendarModel(event.getSubject(), new Date(event.getStart().getDateTime().getTime()),
					new Date(event.getEnd().getDateTime().getTime()), "UNKNOWN");
		} else {
			for (int i = 0; i < events.size(); i++) {
				Event event = events.get(i);
				if (new Date(event.getStart().getDateTime().getTime())
						.before(new Date(finalEvent.getStart().getDateTime().getTime()))) {
					finalEvent = event;
				}
			}

			if (finalEvent == null) {
				return new CalendarModel("", null, null, "");
			}

			return new CalendarModel(finalEvent.getSubject(), new Date(finalEvent.getStart().getDateTime().getTime()),
					new Date(finalEvent.getEnd().getDateTime().getTime()), "UNKNOWN");
		}
	}

	private Event[] getEvents(final MicrosoftAccount account) throws Exception {

		String email = account.getEmail();
		String tenantId = account.getTenantId();
		TokenResponse tokens = account.getTokenResponse();

		tokens = AuthHelper.ensureTokens(tokens, tenantId);

		OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

		String sort = "start/dateTime DESC";
		String properties = "organizer,subject,start,end";
		Integer maxResults = 10;

		PagedResult<Event> events = outlookService.getEvents(sort, properties, maxResults).execute().body();

		return events.getValue();
	}

	public List<Event[]> getEvents(String user) throws Exception {

		AppUser appUser = appUserRepository.findByName(user);
		List<Event[]> events = new ArrayList<Event[]>();

		for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {

			events.add(getEvents(account));

		}

		return events;
	}

    @Override
    public String getClassName() {
        return MicrosoftCalendarService.class.getName();
    }
}
