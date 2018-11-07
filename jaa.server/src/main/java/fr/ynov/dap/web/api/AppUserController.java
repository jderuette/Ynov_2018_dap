package fr.ynov.dap.web.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;

/**
 * @author adrij
 *
 */
@RestController
@RequestMapping("/user")
public class AppUserController {

    /**
     * AppUserRepository.
     */
    @Autowired
    private AppUserRepository repository;

    /**
     * Default controller.
     */
    public AppUserController() {
    }


    /**
     * Add a new UserApp account.
     * @param userKey userKey.
     * @return if the creation succeeded.
     * @throws Exception exception.
     */
    @RequestMapping(value = "/add/{userKey}", method = RequestMethod.GET)
    public String addUserAppAccount(
            @PathVariable("userKey") final String userKey) throws Exception {

        AppUser appUser = new AppUser();
        appUser.setUserKey(userKey);
        repository.save(appUser);

        return "User " + userKey + " added."; //TODO return 201
    }

}
