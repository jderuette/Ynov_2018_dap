package fr.ynov.dap.controllers;

import fr.ynov.dap.models.*;
import fr.ynov.dap.repositories.UserRepository;
import fr.ynov.dap.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


/**
 * PeopleController
 */
@RestController
public class PeopleController {

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Return number of contact
     *
     * @param userName userKey to log
     * @return String number of contact
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    @RequestMapping(value = "/people/{userName}", produces = "application/json", method = GET)
    public final Map<String, Integer> getNumberContact(@PathVariable final String userName) throws GeneralSecurityException, IOException {

        Map<String, Integer> response = new HashMap<>();

        User                user                  = userRepository.findByName(userName);
        List<GoogleAccount> userGoogleAccountList = user.getAccounts();
        int                 contactNumber         = 0;

        for (GoogleAccount currentGoogleAccount : userGoogleAccountList) {
            contactNumber += peopleService.getNumberContact(currentGoogleAccount.getName());
        }

        response.put("email_unread", contactNumber);

        return response;
    }
}
