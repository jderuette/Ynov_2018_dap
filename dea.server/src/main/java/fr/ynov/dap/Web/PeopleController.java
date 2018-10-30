
package fr.ynov.dap.Web;


import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.Config;
import fr.ynov.dap.google.PeopleService;


@RestController
//TODO dea by Djer JavaDoc de la classe ?
public class PeopleController
{

  /**
   * Appel du service google des contacts pour renvoyer le nombre de contacts de
   * l'utilisateur
   * 
   * @param userKey
   * @param user
   * @param config
   * @return
   * @throws IOException
   * @throws GeneralSecurityException
   */
  @RequestMapping("/people/getNbContacts/{user}")
  public Integer getNbContacts(@RequestParam("userKey") final String userKey, @PathVariable String user, Config config)
      throws IOException, GeneralSecurityException
  {
    //TODO dea by Djer une bonne partie de ce code devrait etre dans le service (dans une methode "getNbContacts(....)") !
    com.google.api.services.people.v1.PeopleService people = PeopleService.getService(userKey, config);
    return people.people().connections().list("people/" + user).setPersonFields("names").execute().getTotalPeople();
  }

  
}
