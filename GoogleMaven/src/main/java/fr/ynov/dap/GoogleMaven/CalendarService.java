package fr.ynov.dap.GoogleMaven;
/*public class CalendarService
{
	public get{
		return this;
	}
}*/

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

@Service
public class CalendarService extends GoogleService {

	//private static Config maConfig = configuration;
	private final Logger logger = LogManager.getLogger();
	//private static CalendarService instance;
	private DateTime now;
	private List<Event> items;
	/**
	 * @return the instance
	 */
	/*public static CalendarService getInstance() {
		if (instance == null) {
			instance = new CalendarService();
		}
		return instance;
	}*/

	/**
	 * @return the nbEvents
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */

	public int getNbEvents(String userKey) throws IOException, GeneralSecurityException {
		items = NextEvents(userKey);
		return items.size();
	}

	public CalendarService() {
		super();
	}
	

	public Calendar getService(String userKey) throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, userKey))
		.setApplicationName(configuration.getApplicationName()).build();

		return service;
	}

	/**
	 * 
	 * @param user
	 * @return next event with google api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public List<Event> NextEvents(String userKey) throws IOException, GeneralSecurityException {
		now = new DateTime(System.currentTimeMillis());
		Events events = getService(userKey).events().list("primary").setMaxResults(10).setTimeMin(now)
				.setOrderBy("startTime").setSingleEvents(true).execute();
		items = events.getItems();
		return items;
	}
	
	protected String buildRedirectUri(final HttpServletRequest req, final String destination) {
		final GenericUrl url = new GenericUrl(req.getRequestURL().toString());
		url.setRawPath(destination);
		return url.build();
	}

	/**
	 * 
	 * @param user
	 * @return next event method with google api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public String getNextEvent(final HttpServletRequest request, @RequestParam final String userKey) throws IOException, GeneralSecurityException {
		String msg = null;
		try{
			List<Event> items = NextEvents(userKey);

			if (items.isEmpty()) {
				//System.out.println("Pas d'evenement qui arrive");
				msg = "Pas d'evenement qui arrive";
			} else {
				
				for (Event event : items) {
					DateTime start = event.getStart().getDateTime();
					DateTime end = event.getEnd().getDateTime();
					if (start == null) {
						start = event.getStart().getDate();
					}
					//System.out.println("l'evenement "+ event.getSummary() + " commence le : "+ start+" et se termine le "+ end);
					msg +=  "(" + start + "s)" + event.getSummary() + "(" + end + "s)";

				}

			}
		} catch (IOException e) {
			logger.debug("Erreur détectée pour la récupération des prochains évènements, voici les détails : "+e.getMessage());
		}
		
		return msg;	
	}




}
