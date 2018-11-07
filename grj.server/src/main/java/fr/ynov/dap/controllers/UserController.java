package fr.ynov.dap.controllers;

import fr.ynov.dap.data.User;
import fr.ynov.dap.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/user/add/{userName}", produces = "application/json", method = GET)
    public final Map<String, String> addAccount(@PathVariable final String userName) {

        User                user     = userRepository.findByName(userName);
        Map<String, String> response = new HashMap<>();

        if (user != null) {
            response.put("error", "User already exists");
        } else {
            User newUser = new User();
            newUser.setName(userName);
            userRepository.save(newUser);
            response.put("message", "User created");
        }

        return response;
    }

}
