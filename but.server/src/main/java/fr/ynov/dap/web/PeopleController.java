package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.google.GooglePeopleService;

/**
 * @author thibault
 *
 */
@RestController
public class PeopleController extends GoogleController {
    /**
     * Calendar Google service.
     */
    @Autowired
    private GooglePeopleService service;

    /**
     * Route to get the next event in calendar of client.
     * @param userId ID of user (associate token)
     * @return Event
     * @throws IOException google server response error HTTP
     * @throws GeneralSecurityException google server security problem
     */
    @RequestMapping("/contact/count")
    public int getMyEmails(@RequestParam("userId") final String userId)
            throws IOException, GeneralSecurityException {
        return service.countContacts(userId);
    }
}
