
package fr.ynov.dap.Web;


import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import fr.ynov.dap.Config;
import fr.ynov.dap.google.GMailService;


@RestController
//TODO dea by Djer JavaDoc de la classe ?
public class MailController
{

  /**
   * Appel le service gmail pour renvoyer le nombre d'emails non lu.
   * 
   * @param user Addresse pour laquelle on veut récupérer le nombre de mail
   * @return Le nombre de mails non lus
   * @throws IOException
   * @throws GeneralSecurityException
   */
  @RequestMapping("/gmail/getNbUnreadEmail/{user}")
  public Integer getNbUnreadEmail(@RequestParam("userKey") final String userKey, @PathVariable String user,
      Config config) throws IOException, GeneralSecurityException
  {
    Gmail gmail = GMailService.getService(userKey, config);
    
  //TODO dea by Djer une bonne partie de ce code devrait etre dans le service !
    Label label = gmail.users().labels().get(user, "INBOX").execute();

    return label.getMessagesUnread();
  }

}
