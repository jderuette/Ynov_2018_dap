
package fr.ynov.dap.Web;


import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.services.calendar.model.Event;
import fr.ynov.dap.Config;
import fr.ynov.dap.google.CalendarService;


/**
 * Controller du calendrier
 * 
 * @author antod
 *
 */
@RestController
public class CalendarController
{

  /**
   * Appelle le service calendar pour renvoyer les 10 prochains évènements
   * 
   * @param user Addresse pour laquelle on veut récupérer le nombre de mail
   * @return Les 10 prochains évènements
   * @throws GeneralSecurityException
   * @throws IOException
   */
  @RequestMapping("/calendar/getUpcomingEvent/{user}")
  public Event getUpcomingEvent(@RequestParam("userKey") final String userKey, @PathVariable String user, Config config)
      throws GeneralSecurityException, IOException
  {
    CalendarService calendarService = new CalendarService();

    Event event = calendarService.getUpcomingEvent(userKey);

    return event;
  }
}
