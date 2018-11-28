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

    /**
     * Return current class name.
     * @return Class name
     */
    protected abstract String getClassName();

    protected AppUser GetUserById(final String userId) {

        AppUser user = appUserRepository.findByUserKey(userId);

        if (user == null) {
            LOGGER.error("NO USER:", "Aucun user avec cette identifiant");
        }

        return user;

    }

}
