
package fr.ynov.dap.Web;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.google.GMailService;

/**
 * Controlleur des mails 
 * @author antod
 *
 */
@RestController
public class MailController
{
  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private GMailService gMailService;

  /**
   * Appel le service gmail pour renvoyer le nombre d'emails non lu.
   * 
   * @param user Addresse pour laquelle on veut récupérer le nombre de mail
   * @return Le nombre de mails non lus
   * @throws IOException
   * @throws GeneralSecurityException
   */
  @RequestMapping("/gmail/getNbUnreadEmail")
  public Integer getNbUnreadEmail(@RequestParam String userKey) throws IOException, GeneralSecurityException
  {
    Integer unreadMessages = -1;
    AppUser myUser = appUserRepository.findByName(userKey);

    if (null != myUser)
    {
      unreadMessages = 0;
      List<GoogleAccount> allAccounts = myUser.getAccounts();

      // On boucle sur tous les comptes
      for (int i = 0; i < allAccounts.size(); i++)
      {
        Integer accMessagesUnread = gMailService.getNbUnreadEmail(allAccounts.get(i).getUserName());
        
        unreadMessages += accMessagesUnread;
      }
    }

    return unreadMessages;
  }

}
