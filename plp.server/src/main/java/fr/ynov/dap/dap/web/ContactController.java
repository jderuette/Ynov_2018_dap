package fr.ynov.dap.dap.web;

import fr.ynov.dap.dap.PeopleGService;
import fr.ynov.dap.dap.microsoft.OutlookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/contact")
public class ContactController {
  //TODO plp by Djer |POO| Attention si tu ne précise pas, par défaut cet attribut est public (comme la classe) !
    /**
     * instantiate a peopleGService
     */
    @Autowired
    PeopleGService peopleGService;

  //TODO plp by Djer |POO| Attention si tu ne précise pas, par défaut cet attribut est public (comme la classe) !
    /**
     * instantiate a OutlookService
     */
    @Autowired
    OutlookService outlookService;

    /**
     * Return all contacts of one user
     *
     * @param userKey : name of user
     * @return return the nbContacts of all contacts
     * @throws IOException
     * @throws GeneralSecurityException
     */
    @RequestMapping("/total")
    public final Map<String, Integer> getNbContacts(@RequestParam("userKey") final String userKey)
            throws IOException, GeneralSecurityException {
        Integer nbContacts = 0;

        Map<String, Integer> response = new HashMap<>();
        nbContacts += peopleGService.getNbContacts(userKey);
        nbContacts += outlookService.contacts(userKey);
        response.put("nbContacts", nbContacts);
        return response;
    }
}
