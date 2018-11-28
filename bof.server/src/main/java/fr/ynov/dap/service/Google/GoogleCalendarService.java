package fr.ynov.dap.service.Google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.model.EmptyData;
import fr.ynov.dap.model.EventModel;
import fr.ynov.dap.model.MasterModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.mortbay.util.ajax.JSON;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Florent
 * Service class for all the calendar request
 */
@Service
public class GoogleCalendarService extends GoogleService {

	/**
	 * 
	 * @param userID id of user who try to access to the service
	 * @return Calendar Instance of calendar service
	 * @throws GeneralSecurityException
	 * @throws IOException
	 * 
	 */
	private Calendar getService(String userID) throws GeneralSecurityException, IOException {

		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, cfg.getJSON_FACTORY(), getCredentials(userID))
				.setApplicationName(cfg.getAPPLICATION_NAME()).build();
		return service;
	}

	public GoogleCalendarService() throws GeneralSecurityException, IOException {
		super();
	}

	/**
	 * 
	 * @param userID id of user who try to access to the service
	 * @return EventModel The request response formated in JSON
	 * @throws Exception
	 */
	public EventModel getUpcomingEvent(String userID) throws Exception {
		DateTime now = new DateTime(System.currentTimeMillis());
		Calendar service = getService(userID);
		Events events = service.events().list("primary").setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
				.setSingleEvents(true).execute();
		List<Event> items = events.getItems();
		if (items.isEmpty()) {
			return null;
		} else {
			Event upcoming = items.get(0);
			Date startDate = new Date(upcoming.getStart().getDateTime().getValue());
			Date endDate = new Date(upcoming.getEnd().getDateTime().getValue());
			return new EventModel(upcoming.getSummary(), startDate, endDate);
		}
	}

}