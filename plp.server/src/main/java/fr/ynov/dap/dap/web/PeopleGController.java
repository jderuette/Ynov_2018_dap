package fr.ynov.dap.dap.web;

import fr.ynov.dap.dap.PeopleGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * @author Pierre Plessy
 */
@RestController
@RequestMapping("/people")
public class PeopleGController {
    /**
     * Instantiate instance of PeopleGService.
     */
    @Autowired
    private PeopleGService peopleGService;

    /**
     * get hte number of contact you have.
     *
     * @param userId : userkey param
     * @return Map
     * @throws IOException              : throw exception
     * @throws GeneralSecurityException : throw exception
     */
    @RequestMapping("/total")
    public final Map<String, Integer> getNbContacts(@RequestParam("userKey") final String userId)
            throws IOException, GeneralSecurityException {
        return peopleGService.getNbContacts(userId);
    }
}
