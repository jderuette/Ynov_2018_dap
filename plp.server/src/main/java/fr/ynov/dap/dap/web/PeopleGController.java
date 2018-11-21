package fr.ynov.dap.dap.web;

import fr.ynov.dap.dap.PeopleGService;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
     * instantiate userRepository
     */
    @Autowired
    AppUserRepository userRepository;

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
        AppUser user = userRepository.findByName(userId);
        List<GoogleAccount> listGoogleAccount = user.getGoogleAccount();
        int nbContacts = 0;

        for (GoogleAccount currentAccount : listGoogleAccount) {
            nbContacts += peopleGService.getNbContacts(currentAccount.getName()).get("Total connection");
        }

        Map<String, Integer> response = new HashMap<>();
        response.put("nbContacts", nbContacts);
        return response;
    }
}
