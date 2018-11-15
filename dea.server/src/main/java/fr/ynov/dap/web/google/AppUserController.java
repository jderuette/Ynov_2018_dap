
package fr.ynov.dap.web.google;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;


@RestController
public class AppUserController
{
  @Autowired
  AppUserRepository appUserRepository;

  @RequestMapping("/user/add/{userKey}")
  public String addUserKey(@PathVariable final String userKey)
  {
    if (appUserRepository.findByName(userKey) != null)
    {
      return "Le compte " + userKey + " existe déjà.";
    }

    AppUser appUser = new AppUser();
    appUser.setName(userKey);

//    GoogleAccount gAccount = new GoogleAccount();
//    gAccount.setOwner(appUser);

//    appUser.addGoogleAccount(gAccount);
    appUserRepository.save(appUser);

    return "Compte " + userKey + " ajouté.";
  }
}
