
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

  /**
   * Variable utilisée pour accéder aux services calendar de Google
   */
  public CalendarService instanceCalendarService;

  /**
   * JSON FACTORY
   */
  //TODO dea by Djer |POO| Devrait être final (pas besoin de 1 par instance) et pour être cohérent avec l'écriture en MAJUSCULE
  private final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  
  /**
   * Variable utilisée pour logger les informations
   */
  //TODO dea by Djer |Log4J| Devrait être final. Initialiser un Logger est couteux, et tu n'en a pas besoin de un par instance
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
            //TODO dea by Djer |API Google| utilise le nom d'application de la Config
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

    //TODO dea by Djer |Log4J| Tu peux profiter de cette "log de fin" pour donner des informations sur le "résultat" de l'appel de la fonction
    //TODO dea by Djer |Log4J| C'est hors de contexte du cours, mais ca peut être utile, pour des logs "générique" comme les "enter/exit" que tu as fait, tu peux utiliser AOP
    logger.info("Fin fonction getUpcomingEvent pour l'utilisateur " + userId);
    return event;
  }
}
