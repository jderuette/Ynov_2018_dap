package fr.ynov.dap.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;

/**
 * @author alex
 *
 */
@Controller
public class AppUserController {
  /**
   * accès au repository.
   */
  @Autowired
  private AppUserRepository repository;
  /**
   * @param userKey nom utilisateur
   * @return user
   */
  @RequestMapping("/user/add/{userKey}")
  public final String userkey(@PathVariable final String userKey) {
    AppUser user = new AppUser();
    user.setUserKey(userKey);
    repository.save(user);
    return "userAdd";
  }
}
