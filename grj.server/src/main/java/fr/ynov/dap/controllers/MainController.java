package fr.ynov.dap.controllers;

import org.springframework.web.bind.annotation.*;

/**
 * MainController
 * Use for basics routes
 */
@RestController
public class MainController {

    /**
     * Return a String to confirm that server works
     *
     * @return String
     */
    @RequestMapping("/")
    public final String sayHello() {
        return "My API rocks";
    }

    /**
     * Return success message for user created
     *
     * @return String
     */
    @RequestMapping("/user-success")
    public final String saySuccess() {
        return "User successfully created";
    }

}
