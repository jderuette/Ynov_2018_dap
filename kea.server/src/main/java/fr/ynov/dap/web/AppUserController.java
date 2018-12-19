package fr.ynov.dap.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * This controller is used when AppUser has to be created, modified, or deleted.
 * @author Antoine
 *
 */
@Controller
public class AppUserController {

  /**
   * the appUserRepository manages all database accesses.
   */
  @Autowired
  private AppUserRepository appUserRepo;

  /**
   * Creates a user to identifiate who is using the application.
   * @param userKey the name for the account
   * @param model is used in templates
   * @return the name of the template wanted
   * @throws IOException nothing special
   */
  @RequestMapping("/user/add/{userKey}")
  public String addAppUser(@PathVariable final String userKey,
      final ModelMap model) throws IOException {
    AppUser newAppUser = new AppUser(userKey);
    if (userKey != null && appUserRepo.findByUserKey(userKey) == null) {
      appUserRepo.save(newAppUser);
    } else {
        //TODO kea by Djer |POO| Ce code est utile ? (hormis pou généré une excetion si j'essaye d'ajouter deux fois de suite un userKey qui existe deja)
      newAppUser = new AppUser("testuser");
      appUserRepo.save(newAppUser);
    }
    model.addAttribute("username", userKey);
    return "index";
  }

  /**
   * Opens the template to register an Ynov Dap account.
   * @param model contains the accountName
   * @return the name of the template wanted
   */
  @RequestMapping("/user/add/registerForm")
  public String register(final Model model) {
    model.addAttribute("AppUserAccount", new AppUser());
    return "registerForm";
  }

  /**
   * This method get the value in the username input.
   * @param appUser the accountName prompted
   * @param result the object to manage errors on client interface
   * @param model contains the account name
   * @return the name of the template wanted
   */
  /*
  @RequestMapping(value = "/testuser", method = RequestMethod.POST)
  public String submit(
      @Valid @ModelAttribute("AppUserAccount") final AppUser appUser,
      final BindingResult result, final ModelMap model) {
    if (result.hasErrors()) {
      return "error";
    }
    model.addAttribute("name", appUser.getUserKey());
    return "index";
  }*/

  /**
   * get the calendarService.
   * @return the calendarService
   */
  //TODO kea by Djer |POO| Pourquoi public ? Aucune autre classe n'utilise ce getter, tu peux le supprimer.
  public AppUserRepository getAppUserRepo() {
    return appUserRepo;
  }
}
