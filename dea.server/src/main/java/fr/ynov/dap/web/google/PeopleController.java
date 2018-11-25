
package fr.ynov.dap.web.google;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.Config;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.google.PeopleService;
import fr.ynov.dap.microsoft.Contact;
import fr.ynov.dap.web.microsoft.auth.AuthHelper;
import fr.ynov.dap.web.microsoft.auth.TokenResponse;
import fr.ynov.dap.web.microsoft.service.OutlookService;
import fr.ynov.dap.web.microsoft.service.OutlookServiceBuilder;
import fr.ynov.dap.web.microsoft.service.PagedResult;


/**
 * Controller de la partie People (nb de contacts)
 * 
 * @author antod
 *
 */
@RestController
public class PeopleController
{
  /**
   * Variable appUserRepository
   */
  @Autowired
  private AppUserRepository appUserRepository;

  /**
   * variable peopleService
   */
  @Autowired
  PeopleService peopleService;

  /**
   * Appel du service google des contacts pour renvoyer le nombre de contacts de
   * l'utilisateur
   * 
   * @param userKey
   * @param user
   * @return
   * @throws IOException
   * @throws GeneralSecurityException
   */
  @RequestMapping("/people/getNbContacts")
  public Integer getNbContacts(@RequestParam("userKey") final String userKey, Config config)
      throws IOException, GeneralSecurityException
  {
    Integer nbContacts = getNbGoogleContacts(userKey);
    nbContacts += getNbMicrosoftContacts(userKey);

    return nbContacts;
  }

  /**
   * Renvoie le nombre de contacts du compte google
   * 
   * @param userKey
   * @return
   * @throws GeneralSecurityException
   * @throws IOException
   */
  private Integer getNbGoogleContacts(String userKey) throws GeneralSecurityException, IOException
  {
    Integer nbContacts = 0;
    AppUser appUser = appUserRepository.findByName(userKey);

    List<GoogleAccount> googleAccountList = appUser.getGoogleAccounts();

    for (int i = 0; i < googleAccountList.size(); i++)
    {
      try
      {
        nbContacts += peopleService.getNbContacts(googleAccountList.get(i).getUserName());
      } catch (Exception e)
      {
      }
    }

    return nbContacts;
  }

  /**
   * Renvoie le nombre de contacts du compte microsoft
   * 
   * @param userKey
   * @return
   * @throws IOException
   */
  private Integer getNbMicrosoftContacts(String userKey) throws IOException
  {
    Integer nbContacts = 0;
    AppUser myUser = appUserRepository.findByName(userKey);
//    List<Contact> totalContacts = new ArrayList<Contact>();
//    Contact[] allContacts = null;

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

        nbContacts += outlookService.getContacts().execute().body().getCount();
      }
    }

    return nbContacts;
  }
}
