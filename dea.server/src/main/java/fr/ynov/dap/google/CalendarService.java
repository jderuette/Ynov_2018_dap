
package fr.ynov.dap.google;


import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * Service gérant le calendrier google
 * 
 * @author antod
 *
 */
@Service
public class CalendarService extends GoogleServices
{
  /**
   * Constructeur du CalendarService
   * 
   * @throws GeneralSecurityException
   * @throws IOException
   */
  public CalendarService() throws GeneralSecurityException, IOException
  {
    super();
  }

  public CalendarService instanceCalendarService;

  private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private Logger logger = LogManager.getLogger();

  /**
   * Récupère le calendrier en appelant le service google
   * 
   * @param userId
   * @return
   * @throws GeneralSecurityException
   * @throws IOException
   */
  public Event getUpcomingEvent(String userId) throws GeneralSecurityException, IOException
  {
    logger.info("Début fonction getUpcomingEvent pour l'utilisateur " + userId);

    Calendar calendar = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userId))
        .setApplicationName("").build();

    // List the next 10 events from the primary calendar.
    DateTime now = new DateTime(System.currentTimeMillis());
    
    Events events = calendar.events().list("primary").setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
        .setSingleEvents(true).execute();

    Event event = null;

    if (events.getItems().size() != 0)
    {
      event = events.getItems().get(0);
    }

    logger.info("Fin fonction getUpcomingEvent pour l'utilisateur " + userId);
    return event;
  }
}
