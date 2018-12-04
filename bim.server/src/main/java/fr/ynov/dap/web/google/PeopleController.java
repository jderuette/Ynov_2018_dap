package fr.ynov.dap.web.google;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param userKey applicative user
     * @return next event
     * @throws Exception  if user not found
     */
    @RequestMapping("/contact/google/number")
    public final int getNbContact(@RequestParam("userKey") final String userKey) throws Exception {
      //TODO bim by Djer |Log4J| pas mal comme contexte mais ajoute le "userKey" qui est quand même vachement utile aussi (100% de tes utilisateurs vont demander "me" vue que tu ne gère pas la délagation dans ton code)
        LOGGER.info("Récupération du nombre de contacts de l'utilisateur {}...", "me");

        AppUser user = repository.findByName(userKey);

        return peopleService.getNbContact(user);

    }
}
