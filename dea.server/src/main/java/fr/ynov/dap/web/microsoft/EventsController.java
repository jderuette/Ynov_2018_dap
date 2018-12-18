
package fr.ynov.dap.web.microsoft;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import fr.ynov.dap.web.microsoft.auth.AuthHelper;
import fr.ynov.dap.web.microsoft.auth.TokenResponse;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.microsoft.Event;
import fr.ynov.dap.web.microsoft.service.OutlookService;
import fr.ynov.dap.web.microsoft.service.OutlookServiceBuilder;
import fr.ynov.dap.web.microsoft.service.PagedResult;


/**
 * Classe EventsController
 * 
 * @author antod
 *
 */
@Controller
public class EventsController
{
  /**
   * Variable appUserRepository
   */
  @Autowired
  private AppUserRepository appUserRepository;

  /**
   * Variable pour logger
   */
  //TODO dea by Djer |log4J| Devrait être final
  private final Logger LOGGER = LogManager.getLogger();

  /**
   * Méthode pour récupérer les events microsoft
   * 
   * @param model
   * @param request
   * @param redirectAttributes
   * @param userKey
   * @return
   */
  //TODO dea by Djer |Spring| Comme tu n'utilises pas redirectAttributes, ne l'ajoutep as dans la signature de ta méthode
  //TODO dea by Djer |POO| nom de méthde pas très claire "nextEvent" serait plus adapté (idem pour l'URI). Pour URI éventuellement un "/events/next"
  @RequestMapping("/events")
  public String events(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
      @RequestParam String userKey)
  {
    AppUser myUser = appUserRepository.findByName(userKey);
    List<Event> totalEvents = new ArrayList<Event>();
    Event[] allEvents = null;

    // On filtre sur tous les comptes microsofts
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
        String sort = "start/dateTime DESC";
        // Only return the properties we care about
        String properties = "organizer,subject,start,end";
        // Return at most 1 event
        Integer maxResults = 1;

        try
        {
            //TODO dea by Djer |API Microsoft| Je ne pense pas que Microsofot te renvoie les "à partir de now" par defaut (utilsie le paramètre "filter")
          PagedResult<Event> events = outlookService.getEvents(sort, properties, maxResults, "").execute().body();

          allEvents = events.getValue();

          for (int iEvent = 0; iEvent < allEvents.length; iEvent++)
          {
            totalEvents.add(allEvents[iEvent]);
          }
        } catch (Exception e)
        {
          //TODO dea by Djer |Log4J| Utilise "e" comme deuxième argument, ainsi Log4J pourra gérer le message ET la pile
          LOGGER.error("error", e.getMessage());
        }
      } else
      {
        LOGGER.warn("account " + account.getUserName() + " has no tokens");
      }
    }

    model.addAttribute("events", totalEvents);

    return "events";
  }
}
