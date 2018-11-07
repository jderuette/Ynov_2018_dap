package fr.ynov.dap.controllers;

import fr.ynov.dap.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * PeopleController
 */
@RestController
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    /**
     * Return number of contact
     *
     * @param userKey userKey to log
     * @return String number of contact
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    @RequestMapping(value = "/people/{userKey}", produces = "application/json", method = GET)
    public final Map<String, Integer> getNumberContact(@PathVariable final String userKey) throws GeneralSecurityException, IOException {
        return peopleService.getNumberContact(userKey);
    }
}
