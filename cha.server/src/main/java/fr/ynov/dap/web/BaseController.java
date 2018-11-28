package fr.ynov.dap.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.exception.UserException;
import fr.ynov.dap.repository.AppUserRepository;

public abstract class BaseController {

	/**
     * Instance of AppUserRepository.
     * Auto resolved by Autowire.
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Logger instance.
     */
    private Logger logger = LogManager.getLogger(getClassName());

    /**
     * Return current instance of Logger.
     * @return Logger
     */
    public Logger getLogger() {
        return logger;
    }

    public AppUserRepository getAppUserRepository() {
        return appUserRepository;
    }

    /**
     * Return current class name.
     * @return Class name
     */
    protected abstract String getClassName();

    /**
     * Find a user from database.
     * @param userId User id
     * @return User
     * @throws UserNotFoundException Thrown if no user found
     */
    protected AppUser getUserById(final String userKey) throws UserException {

        AppUser user = appUserRepository.findByUserKey(userKey);

        if (user == null) {

            getLogger().error("User with id : " + userKey + " is undifined");

        }

        return user;

    }

}
