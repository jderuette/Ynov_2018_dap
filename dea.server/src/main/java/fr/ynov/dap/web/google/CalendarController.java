
package fr.ynov.dap.web.google;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.services.calendar.model.Event;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.google.CalendarService;
import fr.ynov.dap.web.UpcomingEvent;
import fr.ynov.dap.web.microsoft.auth.AuthHelper;
import fr.ynov.dap.web.microsoft.auth.TokenResponse;
import fr.ynov.dap.web.microsoft.service.OutlookService;
import fr.ynov.dap.web.microsoft.service.OutlookServiceBuilder;
import fr.ynov.dap.web.microsoft.service.PagedResult;


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
   * @return Les 10 prochains évènements
   * @throws GeneralSecurityException
   * @throws IOException
   */
  @RequestMapping("/calendar/getUpcomingEvent")
  public UpcomingEvent getUpcomingEvent(@RequestParam String userKey) throws GeneralSecurityException, IOException
  {
    AppUser myUser = appUserRepository.findByName(userKey);
    UpcomingEvent upcomingEvent = new UpcomingEvent();

    Event googleEvent = null;
    fr.ynov.dap.microsoft.Event microsoftEvent = null;

    if (null != myUser)
    {
      googleEvent = getUpcomingGoogleEvent(myUser);
      microsoftEvent = getUpcomingMicrosoftEvent(myUser);

      try
      {
        if (googleEvent.getStart().getDateTime().getValue() < microsoftEvent.getStart().getDateTime().getTime())
        {
          upcomingEvent.setEventName(googleEvent.getSummary());
          upcomingEvent.setEventStart(googleEvent.getStart().getDate().toString());
          upcomingEvent.setEventEnd(googleEvent.getEnd().getDate().toString());
        } else
        {
          upcomingEvent.setEventName(microsoftEvent.getSubject());
          upcomingEvent.setEventStart(microsoftEvent.getStart().getDateTime().toString());
          upcomingEvent.setEventEnd(microsoftEvent.getEnd().getDateTime().toString());
        }
      } catch (Exception e)
      {
        if (googleEvent.getStart().getDate().getValue() < microsoftEvent.getStart().getDateTime().getTime())
        {
          upcomingEvent.setEventName(googleEvent.getSummary());
          upcomingEvent.setEventStart(googleEvent.getStart().getDate().toString());
          upcomingEvent.setEventEnd(googleEvent.getEnd().getDate().toString());
        } else
        {
          upcomingEvent.setEventName(microsoftEvent.getSubject());
          upcomingEvent.setEventStart(microsoftEvent.getStart().getDateTime().toString());
          upcomingEvent.setEventEnd(microsoftEvent.getEnd().getDateTime().toString());
        }
      }
    } else
    {
      LOGGER.warn("userKey '" + userKey + "' non trouvé dans getUpcomingEvent");
    }

    return upcomingEvent;
  }

  /**
   * Récupère le prochain évènement dans tous les comptes google
   * 
   * @param myUser
   * @return
   * @throws IOException
   * @throws GeneralSecurityException
   */
  private Event getUpcomingGoogleEvent(AppUser myUser)
  {
    Event nearestEvent = null;
    Event userEvent = null;
    List<GoogleAccount> allAccounts = myUser.getGoogleAccounts();

    // On boucle sur tous les comptes
    for (int i = 0; i < allAccounts.size(); i++)
    {
      try
      {
        userEvent = calendarService.getUpcomingEvent(allAccounts.get(i).getUserName());
      } catch (Exception e)
      {
      }

      // On récupère le prochain évènement
      if (null == nearestEvent
          || nearestEvent.getStart().getDate().getValue() < userEvent.getStart().getDate().getValue())
      {
        nearestEvent = userEvent;
      }
    }

    return nearestEvent;
  }

  /**
   * Récupère le prochain évènement dans tous les comptes microsoft
   * 
   * @param myUser
   * @return
   * @throws IOException
   */
  private fr.ynov.dap.microsoft.Event getUpcomingMicrosoftEvent(AppUser myUser) throws IOException
  {
    fr.ynov.dap.microsoft.Event nearestEvent = null;
    fr.ynov.dap.microsoft.Event[] allEvents = null;

    List<MicrosoftAccount> allAccounts = myUser.getMicrosoftAccounts();
    MicrosoftAccount account = null;

    for (int i = 0; i < allAccounts.size(); i++)
    {
      account = allAccounts.get(i);
      TokenResponse tokens = account.getTokenResponse();

      if (tokens != null)
      {
        String tenantId = account.getTenantId();
        tokens = AuthHelper.ensureTokens(tokens, tenantId);
        OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokens.getAccessToken());

        // Sort by start time in descending order
        String sort = "start/dateTime ASC";
        // Only return the properties we care about
        String properties = "organizer,subject,start,end";
        // Return at most 1 event
        Integer maxResults = 1;

        String filter = "start/dateTime gt '" + Instant.now().toString() + "'";

        PagedResult<fr.ynov.dap.microsoft.Event> events = outlookService.getEvents(sort, properties, maxResults, filter)
            .execute().body();

        try
        {
          allEvents = events.getValue();
          nearestEvent = getNearestUpcomingEvent(nearestEvent, allEvents[0]);
        } catch (Exception e)
        {
          LOGGER.warn("Erreur sur l'évènement microsoft", e);
        }
      }
    }

    return nearestEvent;
  }

  /**
   * Retourne l'évènement à venir le plus proche
   * 
   * @param event1
   * @param event2
   * @return
   */
  private fr.ynov.dap.microsoft.Event getNearestUpcomingEvent(fr.ynov.dap.microsoft.Event event1,
      fr.ynov.dap.microsoft.Event event2)
  {
    if (event1 == null)
    {
      return event2;
    }

    Date dateTime1 = event1.getStart().getDateTime();
    Date dateTime2 = event2.getStart().getDateTime();

    if (dateTime1.getTime() < dateTime2.getTime())
    {
      return event1;
    } else
    {
      return event2;
    }
  }
}
