package fr.ynov.dap.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.exception.UserException;
import fr.ynov.dap.repository.AppUserRepository;

public abstract class BaseController {


    @Autowired
    private AppUserRepository appUserRepository;
    private Logger logger = LogManager.getLogger(getClassName());

	/**
	 * Gets the logger.
	 *
	 * @return the logger
	 */
    public Logger getLogger() {
        return logger;
    }
    public AppUserRepository getAppUserRepository() {
        return appUserRepository;
    }
    protected abstract String getClassName();
	protected AppUser getUserById(final String userKey) throws UserException {
        AppUser user = appUserRepository.findByUserKey(userKey);
        if (user == null) {
			getLogger().error("Utilisateur avec : " + userKey + " pas définie");
        }
        return user;
    }
}
