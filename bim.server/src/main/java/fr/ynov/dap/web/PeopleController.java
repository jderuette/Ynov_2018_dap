package fr.ynov.dap.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.google.PeopleService;

/**
 * People controller.
 * @author MBILLEMAZ
 *
 */
@RestController
public class PeopleController {

    /**
     * Logger.
     */
    public static final Logger LOGGER = LogManager.getLogger();

    /**
     * own people service.
     */
    @Autowired
    private PeopleService peopleService;

    /**
     * Repository.
     */
    @Autowired
    private AppUserRepository repository;

    /**
     * get next event.
     * @param gUser google user
     * @param userKey applicative user
     * @return next event
     * @throws Exception  if user not found
     */
    @RequestMapping("/contact/number/{gUser}")
    public final int getNbContact(@PathVariable final String gUser, @RequestParam("userKey") final String userKey)
            throws Exception {
        LOGGER.info("Récupération du nombre de contacts de l'utilisateur {}...", gUser);

        AppUser user = repository.findByName(userKey);
        
        return peopleService.getNbContact(user);

    }
}
