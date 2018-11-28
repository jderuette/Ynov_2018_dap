package fr.ynov.dap.dap.web;

import fr.ynov.dap.dap.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AppUserController {
    /**
     * instantiate AppUserService
     */
    @Autowired
    AppUserService appUserService;

    /**
     * Add new user
     * @param userKey :name user
     * @return name and id of user
     */
    @RequestMapping("/user/add/{userKey}")
    public Map<String, Object> addUser(@PathVariable final String userKey) {
        return appUserService.addUser(userKey);
    }
}
