package fr.ynov.dap.web;

import com.google.api.services.people.v1.model.ListConnectionsResponse;
import fr.ynov.dap.services.google.GPeopleService;
import fr.ynov.dap.services.google.responses.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * The GPeopleController handles all the client(s) requests related to people/contacts.
 */
@RestController
public class GPeopleController {

    /**
     * The people service attribute automatically wired by Spring.
     */
    @Autowired
    private GPeopleService peopleService;

    /**
     * This method handles the client's request on the endpoint people/nbContacts.
     * @param userKey This is the login of the user.
     * @return It returns the response of the request which contains the number of contact.
     * @throws IOException The GPeopleService can throw an IOException.
     * @throws GeneralSecurityException The GPeopleService can throw an GeneralSecurityException.
     */
    @RequestMapping("people/nbContacts")
    public final ServiceResponse<ListConnectionsResponse> getTotalNumberOfContacts(@RequestParam("userKey") final String userKey) throws IOException, GeneralSecurityException {
        return peopleService.getTotalNumberOfContacts(userKey);
    }
}
