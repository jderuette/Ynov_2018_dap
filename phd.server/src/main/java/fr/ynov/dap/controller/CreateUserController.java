package fr.ynov.dap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.service.GoogleService;

/**
 * @author Dom
 *
 */
@RestController
public class CreateUserController extends GoogleService {
    /**
     * TODO phd by Djer |JavaDoc| . (point)
     * .
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * @param userId .
     */
    @RequestMapping("user/add")
    public void createUser(@RequestParam("userKey") final String userId) {
        AppUser appUser = new AppUser();
        appUser.setName(userId);
        appUserRepository.save(appUser);
        //TODO phd by Djer |MVC| Au moin un petit message "OK" (en appelant par le naviguateur on a une page blache, et via l'API on aurait juste "pas de réponse) ce qui est perturbant
    }

}
