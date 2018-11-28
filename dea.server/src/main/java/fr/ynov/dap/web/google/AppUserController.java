
package fr.ynov.dap.web.google;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;


/**
 * Classe AppUserController pour enregistrer les infos des user dans la BDD
 * 
 * @author antod
 *
 */
@RestController
public class AppUserController
{
  /**
   * Variable appUserRepository
   */
  @Autowired
  AppUserRepository appUserRepository;

  /**
   * Ajoute un nouvel userKey dans la BDD
   * 
   * @param userKey
   * @return
   */
  @RequestMapping("/user/add/{userKey}")
  public String addUserKey(@PathVariable final String userKey)
  {
    if (appUserRepository.findByName(userKey) != null)
    {
      return "Le compte " + userKey + " existe déjà.";
    }

    AppUser appUser = new AppUser();
    appUser.setName(userKey);
    appUserRepository.save(appUser);

    return "Compte " + userKey + " ajouté.";
  }
}
