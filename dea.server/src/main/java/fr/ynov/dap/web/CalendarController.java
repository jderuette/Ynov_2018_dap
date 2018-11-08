
package fr.ynov.dap.web;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.services.calendar.model.Event;
import fr.ynov.dap.Config;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.GoogleAccount;
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
   * Variable appUserRepository
   */
  @Autowired
  private AppUserRepository appUserRepository;

  /**
   * Variable CalendarService
   */
  @Autowired
  private CalendarService calendarService;

  /**
   * Variable pour logger
   */
  private final Logger LOGGER = LogManager.getLogger();

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
    AppUser myUser = appUserRepository.findByName(userKey);

    Event event = null;
    Event userEvent = null;

    if (null != myUser)
    {
      List<GoogleAccount> allAccounts = myUser.getAccounts();

      // On boucle sur tous les comptes
      for (int i = 0; i < allAccounts.size(); i++)
      {
        userEvent = calendarService.getUpcomingEvent(allAccounts.get(i).getUserName());

        // On récupère le prochain évènement
        if (null == event || event.getStart().getDate().getValue() < userEvent.getStart().getDate().getValue())
        {
          event = userEvent;
        }
      }
    } else
    {
      LOGGER.warn("userKey '" + userKey + "' non trouvé dans getUpcomingEvent");
    }

    return event;
  }
}
