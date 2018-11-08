package fr.ynov.dap.controllers;

import fr.ynov.dap.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * UserController
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Create a new user route.
     *
     * @param userName name of the new user.
     * @return response in JSON.
     */
    @RequestMapping(value = "/user/create/{userName}")
    public final Map<String, String> addUser(@PathVariable final String userName) {
        return userService.create(userName);
    }

}
