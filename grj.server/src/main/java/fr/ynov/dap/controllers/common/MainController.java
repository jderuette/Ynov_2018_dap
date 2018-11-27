package fr.ynov.dap.controllers.common;

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
    public String sayHello() {
        return "My API rocks";
    }

    /**
     * Return success message for user created
     *
     * @return String
     */
    @RequestMapping("/user-success")
    public String saySuccess() {
        return "User successfully created";
    }

    /**
     * Return success message for user created
     *
     * @return String
     */
    @RequestMapping("/user-does-not-exist")
    public String sayUserDoesNotExist() {
        return "User does not exist, please create a user";
    }

}
