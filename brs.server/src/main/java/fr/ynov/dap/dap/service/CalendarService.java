package fr.ynov.dap.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.mortbay.log.Log;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

// TODO: Auto-generated Javadoc
/**
 * The Class CalendarService.
 */
@RestController
//TODO brs by Djer Un RestController sans méthode annotée "@RequetsMapping" ?
public class CalendarService extends GoogleServices {

	/**
	 * Instantiates a new calendar service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public CalendarService() throws IOException, GeneralSecurityException {
		super();
	}

	/** The instance. */
	private static CalendarService INSTANCE = null;

	/**
	 * Gets the single instance of CalendarService.
	 *
	 * @return single instance of CalendarService
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	//TODO brs by Djer Inutile, Spring le fait pour toi sur un @RestController (ou un @Service).
	// Deplus ton constructeur est public !
	// Deplus deplus ton unique méthode est static !!!
	public static CalendarService getInstance() throws IOException, GeneralSecurityException {
		if (INSTANCE == null) {
			INSTANCE = new CalendarService();

		}
		return INSTANCE;
	}

	/**
	 * Add a Google account (user will be prompt to connect and accept required
	 * access).
	 *
	 * @param user une string
	 * @return une liste des event
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public static List<Event> getNextEvent(String user) throws IOException, GeneralSecurityException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		if (user == null) {
			user = "sylvain69740@gmail.com";
		}
		//FIXME brs by Djer Attention tu utilise un systeme de log de Jetty !!!! Utiliser Log4J
		// C'est surement pas ce que tu veux, et "jetty" ne sera plus utile quand tes credential seront en mode "web" !
		Log.info("TOMA PAS GENTIL"); //TODO brs by Djer A bon ?
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, configuration.getJSON_FACTORY(),
				getCredentials(HTTP_TRANSPORT, configuration.getCREDENTIALS_FILE_PATH(), user))
						.setApplicationName(configuration.getAPPLICATION_NAME()).build();

		// List the next 10 events from the primary calendar.
		DateTime now = new DateTime(System.currentTimeMillis());
		Events events = service.events().list("primary").setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
				.setSingleEvents(true).execute();
		List<Event> items = events.getItems();

		return items;

	}
}
