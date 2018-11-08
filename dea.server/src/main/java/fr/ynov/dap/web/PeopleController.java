
package fr.ynov.dap.web;


import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.Config;
import fr.ynov.dap.google.PeopleService;


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
   * Appel du service google des contacts pour renvoyer le nombre de contacts de
   * l'utilisateur
   * 
   * @param userKey
   * @param user
   * @return
   * @throws IOException
   * @throws GeneralSecurityException
   */
  @RequestMapping("/people/getNbContacts/{user}")
  public Integer getNbContacts(@RequestParam("userKey") final String userKey, @PathVariable String user, Config config)
      throws IOException, GeneralSecurityException
  {
    PeopleService peopleService = new PeopleService();

    return peopleService.getNbContacts(userKey);
  }

}
