
package fr.ynov.dap.web.microsoft;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import fr.ynov.dap.microsoft.Contact;
import fr.ynov.dap.web.microsoft.service.OutlookService;
import fr.ynov.dap.web.microsoft.service.OutlookServiceBuilder;
import fr.ynov.dap.web.microsoft.service.PagedResult;


@Controller
public class ContactsController
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

  @RequestMapping("/contacts")
  public String contacts(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes,
      @RequestParam String userKey)
  {
    AppUser myUser = appUserRepository.findByName(userKey);
    List<Contact> totalContacts = new ArrayList<Contact>();
    Contact[] allContacts = null;

    // On filtre sur les comptes microsoft
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

        // Sort by given name in ascending order (A-Z)
        String sort = "GivenName ASC";
        // Only return the properties we care about
        String properties = "GivenName,Surname,CompanyName,EmailAddresses";
        // Return at most 10 contacts
        Integer maxResults = 10;

        try
        {
          PagedResult<Contact> contacts = outlookService.getContacts(sort, properties, maxResults).execute().body();

          allContacts = contacts.getValue();

          for (int iContact = 0; iContact < allContacts.length; iContact++)
          {
            totalContacts.add(allContacts[iContact]);
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

    model.addAttribute("contacts", totalContacts);

    return "contacts";
  }
}
