package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.service.PeopleGService;

/**
 *
 * @author Dom
 *This class return the number of contact with a string param "userId" in format String
 */
@RestController
public class PeopleGController {
    /**
     * Return the unique instance of PeopleService with annotation Autowired.
     */
    @Autowired
    private PeopleGService peopleService;

    /**.
     * This function return the number of contact unread with string param userId according to the annotated route
     * @param userKey .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    @RequestMapping("/google/getPeople")
    public String getNbContact(@RequestParam("userKey") final String userKey)
            throws IOException, GeneralSecurityException {
        //TODO phd by Djer |Rest API| Renvoie des données, et laisse le client effectué l'affichage
        return "Nb contact : " + peopleService.nbContact(userKey);
    }

}
