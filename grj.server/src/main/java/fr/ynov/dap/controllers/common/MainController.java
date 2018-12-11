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
        //TODO grj by Djer |Spring| Elle rokerait plus en multi-langue. Ca n'était pas demandé pour l'API, mais tu pourrais traduire en cobinant un "java.util.Locale" en apramètre (loale corrante de l'utilsiateur) et uen injectant un "MessageSource" (cf : https://stackoverflow.com/questions/29747886/spring-rest-internationalization)
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
