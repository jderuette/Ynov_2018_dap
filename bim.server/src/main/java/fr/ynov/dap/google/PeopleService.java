package fr.ynov.dap.google;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.google.GoogleAccount;

/**
 * People service.
 * @author MBILLEMAZ
 *
 */
@Service
public class PeopleService extends CommonGoogleService {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();

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
        LOGGER.info("Récupération du service People...");
        return new com.google.api.services.people.v1.PeopleService.Builder(getHttpTransport(), JSON_FACTORY,
                getCredentials(userKey)).setApplicationName(getConfig().getApplicationName()).build();

    }

    /**
     * get number of gmail contacts.
     * @param user applicative user
     * @return number of contacts
     */
    public final Integer getNbContact(final AppUser user) {

        Integer res = 0;
        try {
            List<GoogleAccount> accountNames = user.getGoogleAccount();
            for (int i = 0; i < accountNames.size(); i++) {
                String userKey = accountNames.get(i).getName();
                com.google.api.services.people.v1.PeopleService service = getService(userKey);
                res += (int) service.people().connections().list("people/me").setPersonFields("names").execute()
                        .getTotalPeople();
            }

        } catch (IOException e) {
            LOGGER.error("Erreur lors de la récupération du nombre de contact de " + user.getName(), e);
        } catch (Exception e) {
            LOGGER.error("Erreur lors de la récupération du service google pour l'utilisateur " + user.getName(), e);
        }
        //TODO return ?
        return res;

    }
}
