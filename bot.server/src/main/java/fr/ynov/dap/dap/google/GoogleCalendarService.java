package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.dap.comparator.SortByDate;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.model.GoogleCalendarResponse;
import fr.ynov.dap.dap.repository.AppUserRepository;

/**
 * The Class CalendarService.
 */
@Service
public class GoogleCalendarService extends GoogleService { 
	
	/** The app user repo. */
	@Autowired
    private AppUserRepository appUserRepo;
	
	/** The log. */
	private final Logger LOG = LogManager.getLogger(GoogleAccountService.class);
	
	/**
	 * Instantiates a new calendar service.
	 */
	public GoogleCalendarService() {
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
	private Calendar getService(String accountName) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JACKSON_FACTORY,
				getCredentials(HTTP_TRANSPORT, accountName)).setApplicationName(configuration.getApplicationName()).build();
		return service;
	}
	
	/**
	 * Gets the next event.
	 *
	 * @param accountName the user id
	 * @return the next event
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	private GoogleCalendarResponse getNextEvent(String accountName)  throws IOException, GeneralSecurityException {
		Calendar service = getService(accountName);
		DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
                .setMaxResults(1)
                .setTimeMin(now)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();
        List<Event> items = events.getItems();
        
        if(items.size() <= 0) {
        	return null;
        }
        
        GoogleCalendarResponse calendarRes = new GoogleCalendarResponse(
        		new Date(items.get(0).getStart().getDateTime().getValue()),
        		new Date(items.get(0).getEnd().getDateTime().getValue()),
        		items.get(0).getStatus(),
        		items.get(0).getSummary()
        		);
        return calendarRes;
	}
	
	/**
	 * Gets the next event for all accounts.
	 *
	 * @param userKey the user key
	 * @return the next event for all accounts
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public GoogleCalendarResponse getNextEventForAllAccounts(final String userKey) throws IOException, GeneralSecurityException {
		LOG.info("getNextEventAllAccount");
		AppUser user = appUserRepo.findByName(userKey);
		ArrayList<GoogleCalendarResponse> events = new ArrayList<>();
		
		if(user != null) {
			for (GoogleAccount currentData : user.getGoogleAccounts()) {
				GoogleCalendarResponse eventTemp = getNextEvent(currentData.getName());
				if(eventTemp != null) {
					events.add(eventTemp);
				}
			}
		}
		if(events.size() == 0) {
			LOG.warn("No events available for google.");
			return null;
		}
		
		Collections.sort(events, new SortByDate());
		
		return events.get(0);
	}
}
