package fr.ynov.dap.google;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * People service.
 * @author MBILLEMAZ
 *
 */
@org.springframework.stereotype.Service
public class PeopleService extends Service {

    /**
     * Public constructor.
     */
    public PeopleService() {
        super();
    }

    /**
     * get calender service.
     * @param userKey applicative user
     * @return calendar service
     * @throws Exception  if user not found
     */

    public final com.google.api.services.people.v1.PeopleService getService(final String userKey) throws Exception {
        Logger logger = LogManager.getLogger();
        logger.info("Récupération du service People...");
        return new com.google.api.services.people.v1.PeopleService.Builder(getHttpTransport(), JSON_FACTORY,
                getCredentials(userKey)).setApplicationName(getConfig().getApplicationName()).build();

    }
}
