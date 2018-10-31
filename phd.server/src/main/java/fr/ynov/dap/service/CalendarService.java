package fr.ynov.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

import fr.ynov.dap.config.Config;
/**
 * This class extends GoogleService and provides calendar service features
 * @author Dom
 *
 */
@Service
public class CalendarService extends GoogleService {
/**
 * Return the service of calendar according userId param
 * @param userId
 * @return
 * @throws GeneralSecurityException
 * @throws IOException
 */
	public Calendar getService(String userId) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userId))
				.setApplicationName(config.getApplicationName()).build();
		
		return service;
	}
	/**
	 * Return the next event according the userId param in format Map<String,Object>
	 * @param userId
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Map<String,Object> getNextEvent(String userId) throws IOException, GeneralSecurityException {
		 DateTime now = new DateTime(System.currentTimeMillis());
		 Events events = getService(userId).events().list("primary").setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
					.setSingleEvents(true).execute();
			List<Event> items = events.getItems();
			if (items.isEmpty()) {
				Map<String,Object> mHashMap = new HashMap<>();
				mHashMap.put("eventUncomming", "No upcoming events found");
				//TODO phd by Djer Evite les multiple return dans une méthode
				return mHashMap;
			} else {
				for (Event event : items) {
					Date eventBegin = new Date(event.getStart().getDateTime().getValue());
					Date eventFinish = new Date(event.getEnd().getDateTime().getValue());
					Map<String,Object> mHashMap = new HashMap<>();
					mHashMap.put("eventUncomming", event.getSummary());
					mHashMap.put("eventBegin", eventBegin.toLocaleString());
					mHashMap.put("eventFinish", eventFinish.toLocaleString());
					//TODO phd by Djer attention status de l'évènnement, PAS de l'utilisateur qui fait la demande !
					mHashMap.put("eventStatus",event.getStatus());
					return mHashMap;
					
				}
			}
			return null;
	}

}
