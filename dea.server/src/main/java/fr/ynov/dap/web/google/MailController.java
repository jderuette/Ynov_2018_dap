
package fr.ynov.dap.web.google;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.google.GMailService;
import fr.ynov.dap.microsoft.Folder;
import fr.ynov.dap.web.microsoft.auth.AuthHelper;
import fr.ynov.dap.web.microsoft.auth.TokenResponse;
import fr.ynov.dap.web.microsoft.service.OutlookService;
import fr.ynov.dap.web.microsoft.service.OutlookServiceBuilder;


/**
 * Controlleur des mails
 * 
 * @author antod
 *
 */
@RestController
public class MailController
{
  /**
   * Variable appUserRepository
   */
  @Autowired
  private AppUserRepository appUserRepository;

  /**
   * Variable GmailService
   */
  @Autowired
  private GMailService gMailService;

  /**
   * Variable pour logger
   */
  private final Logger LOGGER = LogManager.getLogger();

  /**
   * Appel le service gmail pour renvoyer le nombre d'emails non lu.
   * 
   * @param user Addresse pour laquelle on veut récupérer le nombre de mail
   * @return Le nombre de mails non lus
   * @throws IOException
   * @throws GeneralSecurityException
   */
  @RequestMapping("/email/getNbUnreadEmail")
  public Integer getNbUnreadEmail(@RequestParam String userKey) throws IOException, GeneralSecurityException
  {
    Integer unreadMessages = -1;
    AppUser myUser = appUserRepository.findByName(userKey);

    if (null != myUser)
    {
      // Récupère les mails non lus des comptes google
      unreadMessages = getNbUnreadGoogleMails(myUser);

      // Récupère les mails non lus des comptes microsoft
      unreadMessages += getNbUnreadMicrosoftMails(myUser);

    } else
    {
      LOGGER.warn("userKey '" + userKey + "' non trouvé dans getNbUnreadEmail");
      unreadMessages = -1;
    }

    return unreadMessages;
  }

  /**
   * Récupère le nombre de mails non lus pour les comptes google
   * 
   * @param userKey
   * @return
   * @throws IOException
   * @throws GeneralSecurityException
   */
  private Integer getNbUnreadGoogleMails(AppUser myUser)
  {
    Integer unreadMessages = 0;
    List<GoogleAccount> allAccounts = myUser.getGoogleAccounts();

    // On boucle sur tous les comptes
    for (int i = 0; i < allAccounts.size(); i++)
    {
      try
      {
        unreadMessages += gMailService.getNbUnreadEmail(allAccounts.get(i).getUserName());
      } catch (Exception e)
      {
        LOGGER.warn("probleme avec les messages google", e);
      }
    }

    return unreadMessages;
  }

  /**
   * Récupère le nombre de mails non lus pour les comptes microsoft
   * 
   * @param myUser
   * @return
   * @throws IOException
   */
  private Integer getNbUnreadMicrosoftMails(AppUser myUser) throws IOException
  {
    Integer nbUnreadMails = 0;

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

        Folder myFolder = outlookService.getInboxFolder().execute().body();

        nbUnreadMails += myFolder.getUnreadItemCount();
      }
    }

    return nbUnreadMails;
  }
}
