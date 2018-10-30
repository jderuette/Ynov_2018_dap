//TODO dea by Djer Eviter les majuscule dans les nom de package, à la limite du CamelCase ?
package fr.ynov.dap.Web;


import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import fr.ynov.dap.Config;
import fr.ynov.dap.google.CalendarService;


@RestController
//TODO dea by Djer JavaDoc de la classe ?
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
    Calendar calendar = CalendarService.getService(userKey);
    
    //TODO dea by Djer une bonne partie de ce code devrait etre dans le service !

    // List the next 10 events from the primary calendar.
    DateTime now = new DateTime(System.currentTimeMillis());
    Events events = calendar.events().list("primary").setMaxResults(1).setTimeMin(now).setOrderBy("startTime")
        .setSingleEvents(true).execute();

    Event event = null;
//    JsonObject jsonObject = new JsonObject();

    if (events.getItems().size() != 0)
    {
      event = events.getItems().get(0);
    }

    return event;
  }
}
