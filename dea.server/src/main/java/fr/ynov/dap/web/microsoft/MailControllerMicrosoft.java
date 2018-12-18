
package fr.ynov.dap.web.microsoft;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.microsoft.Message;
import fr.ynov.dap.web.microsoft.auth.AuthHelper;
import fr.ynov.dap.web.microsoft.auth.TokenResponse;
import fr.ynov.dap.web.microsoft.service.OutlookService;
import fr.ynov.dap.web.microsoft.service.OutlookServiceBuilder;
import fr.ynov.dap.web.microsoft.service.PagedResult;


/**
 * Classe MailControllerMicrosoft
 * 
 * @author antod
 *
 */
@Controller
public class MailControllerMicrosoft
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
   * Méthode pour récupérer les mails microsoft
   * 
   * @param model
   * @param request
   * @param redirectAttributes
   * @param userKey
   * @return
   */
  @RequestMapping("/mail")
  public String mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
      @RequestParam String userKey)
  {
    AppUser myUser = appUserRepository.findByName(userKey);
    Map<String, Message[]> totalMessages = new HashMap<String, Message[]>();

    Message[] allMessages = null;

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

        // Retrieve messages from the inbox
        String folder = "inbox";
        // Sort by time received in descending order
        String sort = "receivedDateTime DESC";
        // Only return the properties we care about
        String properties = "receivedDateTime,from,isRead,subject,bodyPreview";
        // Return at most 10 messages
        Integer maxResults = 10;

        try
        {
          PagedResult<Message> messages = outlookService.getMessages(folder, sort, properties, maxResults).execute()
              .body();

          allMessages = messages.getValue();

          totalMessages.put(account.getUserName(), allMessages);

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

    model.addAttribute("totalMessages", totalMessages);

    return "mail";
  }
}
