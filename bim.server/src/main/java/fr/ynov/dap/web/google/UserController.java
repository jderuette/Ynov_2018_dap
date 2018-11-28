package fr.ynov.dap.web.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;

/**
 * Controller for manage applicative users.
 * @author MBILLEMAZ
 *
 */
@RestController
public class UserController {

    /**
     * Repository.
     */
    @Autowired
    private AppUserRepository userRepository;

    /**
     * Add applicative user.
     * @param userId id of new user
     * @return confirmation message
     */
    @RequestMapping("/user/add/{userId}")
    @ResponseBody
    public final String addUser(@PathVariable final String userId) {
        AppUser user = userRepository.findByName(userId);
        if (user == null) {
            userRepository.save(new AppUser(userId));
            return "user added";
        }
        return "User already created";

    }
}
