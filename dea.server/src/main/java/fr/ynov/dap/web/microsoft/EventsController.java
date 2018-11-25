
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
  private final Logger LOGGER = LogManager.getLogger();

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
          PagedResult<Event> events = outlookService.getEvents(sort, properties, maxResults, "").execute().body();

          allEvents = events.getValue();

          for (int iEvent = 0; iEvent < allEvents.length; iEvent++)
          {
            totalEvents.add(allEvents[iEvent]);
          }
        } catch (Exception e)
        {
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
