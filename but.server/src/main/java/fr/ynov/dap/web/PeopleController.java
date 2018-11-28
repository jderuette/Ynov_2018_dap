package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.google.GooglePeopleService;
import fr.ynov.dap.microsoft.MicrosoftContactService;

/**
 * @author thibault
 *
 */
@RestController
public class PeopleController extends HandlerErrorController {
    /**
     * People Google service.
     */
    @Autowired
    private GooglePeopleService service;

    /**
     * Contact Microsoft service.
     */
    @Autowired
    private MicrosoftContactService microsoftContactService;

    /**
     * Repository of AppUser.
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**
     * Route to get the next event in calendar of client.
     * @param userKey ID of user
     * @return Event
     * @throws IOException google server response error HTTP
     * @throws GeneralSecurityException google server security problem
     */
    @RequestMapping("/contact/count")
    public int getMyEmails(@RequestParam("userKey") final String userKey)
            throws IOException, GeneralSecurityException {
        AppUser user = repositoryUser.findByUserKey(userKey);

        if (user == null) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "User '" + userKey + "' not found.");
        }

        return service.countContactsOfAllAccounts(user) + microsoftContactService.getContactCount(user);
    }
}
