package fr.ynov.dap.web;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.google.PeopleService;

/**
 * People controller.
 * @author MBILLEMAZ
 *
 */
@RestController
public class PeopleController {

    /**
     * own people service.
     */
    @Autowired
    private PeopleService peopleService;

    /**
     * get next event.
     * @param user google user
     * @param userKey applicative user
     * @return next event
     * @throws Exception  if user not found
     */
    @RequestMapping("/contact/number/{user}")
    public final int getNbContact(@PathVariable final String user, @RequestParam("userKey") final String userKey)
            throws Exception {
      //TODO bim by Djer La majorité de ce code devrait être dans le service (peopleService.getNbContact()).
        Logger logger = LogManager.getLogger();
        logger.info("Récupération du nombre de contacts de l'utilisateur {}...", user);
        com.google.api.services.people.v1.PeopleService service = peopleService.getService(userKey);

        int nbContact = (int) service.people().connections().list("people/" + user).setPersonFields("names").execute()
                .getTotalPeople();

        return nbContact;

    }
}
