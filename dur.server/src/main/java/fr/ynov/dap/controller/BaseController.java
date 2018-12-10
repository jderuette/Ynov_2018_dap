package fr.ynov.dap.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * BaseController.
 * @author Robin DUDEK
 *
 */
public abstract class BaseController {

    /**
     * Instance of AppUserRepository.
     * Auto resolved by Autowire.
     */
    @Autowired
    protected AppUserRepository appUserRepository;

    /**
     * Logger instance.
     */
    private Logger LOGGER = LogManager.getLogger(getClassName());

    /**
     * Return current instance of Logger.
     * @return Logger
     */
    public Logger getLogger() {
        return LOGGER;
    }

    //TODO dur by Djer |JavaDoc| Peut-être que si tu avais completé la JavaDoc pour le paramètre tu aurais constaté qu'il était "mal nomé" (il s'agit d'un userKey)
    /**
     * Return current class name.
     * @return Class name
     */
    protected abstract String getClassName();

    //TODO dur by Djer |Audit Code| Attention au nomage de tes méthodes ! En plus c'est signalé par ton outisl d'audit du code
    protected AppUser GetUserById(final String userId) {

        AppUser user = appUserRepository.findByUserKey(userId);

        if (user == null) {
            //TODO dur by Djer |log| Contextualise tes messages (" avec le userKey : " + userId)
            LOGGER.error("NO USER:", "Aucun user avec cette identifiant");
        }

        return user;

    }

}
