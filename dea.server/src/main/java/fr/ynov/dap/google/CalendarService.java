
package fr.ynov.dap.google;


import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


@Service
//TODO dea by Djer JavaDoc de la classe ?
public class CalendarService extends Services
{
  /**
   * Constructeur du CalendarService
   * 
   * @throws GeneralSecurityException
   * @throws IOException
   */
  CalendarService() throws GeneralSecurityException, IOException
  {
    super();
    // TODO Auto-generated constructor stub
  }

  public static CalendarService instanceCalendarService;

  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static Logger logger = LogManager.getLogger();

  /**
   * Récupère le calendrier en appelant le service google
   * 
   * @param userId
   * @return
   * @throws GeneralSecurityException
   * @throws IOException
   */
  public static Calendar getService(String userId) throws GeneralSecurityException, IOException
  {
    logger.info("Début fonction getService.");

    Calendar calendar = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userId))
        .setApplicationName("").build();

    logger.info("Fin fonction getService.");
    return calendar;
  }
}
