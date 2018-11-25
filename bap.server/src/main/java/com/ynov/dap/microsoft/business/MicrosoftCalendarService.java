package com.ynov.dap.microsoft.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ynov.dap.data.AppUser;
import com.ynov.dap.google.models.CalendarModel;
import com.ynov.dap.microsoft.data.MicrosoftAccount;
import com.ynov.dap.microsoft.models.Event;
import com.ynov.dap.microsoft.models.PagedResult;
import com.ynov.dap.microsoft.models.TokenResponse;
import com.ynov.dap.repositories.AppUserRepository;

public class MicrosoftCalendarService {
	
    @Autowired
    private AppUserRepository appUserRepository;
    
    public PagedResult<Event> resultCalendar(final MicrosoftAccount microsoftAccount) throws Exception {

    	/*
        if (userId == null) {
            return new Event();
        }
        */

        String email = microsoftAccount.getEmail();
        String tenantId = microsoftAccount.getTenantId();
        TokenResponse tokens = microsoftAccount.getTokenResponse();

        tokens = AuthHelper.ensureTokens(tokens, tenantId);


        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken(), email);

        // Sort by start time in descending order
        String sort = "start/dateTime ASC";
        // Only return the properties we care about
        String properties = "organizer,subject,start,end";
        // Return at most 10 events
        Integer maxResults = 1;

        try {
          return outlookService.getEvents(
              sort, properties, maxResults)
              .execute().body();
        } catch (IOException e) {
        	//
        }
        
        
		return null;
        
        //
    }
	
	public CalendarModel getLastEvent(String gUser) {
		
	    AppUser appUser = appUserRepository.findByName(gUser);
        List<Event> events = new ArrayList<Event>();

	    for (MicrosoftAccount account : appUser.getMicrosoftAccounts()) {

	    	//events.addAll(resultCalendar(account));

	    }

        //getLogger().info("nb messages unread " + mail.getUnRead() + " for user : " + user);
        return new CalendarModel("", null, null, "");
	}

}
