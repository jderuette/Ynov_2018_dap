package fr.ynov.dap.services.google;

import fr.ynov.dap.helpers.GoogleHelper;
import fr.ynov.dap.models.common.User;
import fr.ynov.dap.models.google.GoogleAccount;
import fr.ynov.dap.repositories.UserRepository;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PeopleService
 */
@Service
public class GoogleContactService {

    /**
     * Autowired GoogleHelper
     */
    @Autowired
    private GoogleHelper googleHelper;

    /**
     * Autowired UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LogManager.getLogger(GoogleContactService.class);

    /**
     * Get total number of contact of all GoogleAccount
     *
     * @param userName userName
     * @return contacts total
     */
    public int getNumberContacts(String userName) {

        int contactCount = 0;

        try {
            User user = userRepository.findByName(userName);
            if (user == null) {
                //TODO grj by Djer |Gestion Exception| Contexte (for userName : +userName).
                //TODO grj by Djer |Log4J| une petite log ?
                throw new Exception("User does not exist");
            }

            List<GoogleAccount> userGoogleAccountList = user.getGoogleAccountList();
            for (GoogleAccount currentGoogleAccount : userGoogleAccountList) {
                Integer totalPeople = googleHelper.getPeopleService(currentGoogleAccount.getName()).people().connections().list("people/me").setPersonFields("names,emailAddresses").execute().getTotalPeople();
                if (totalPeople != null) {
                    contactCount += totalPeople;
                }
            }

        } catch (Exception e) {
          //TODO grj by Djer |Log4J| Contexte (for userName : +userName).
            LOGGER.error("Error when trying to count all GoogleAccount contacts", e);
            //TODO grj by Djer |Log4J| "e.printStackTrace()" affiche directement dans la console, la pile est déja présente dans ton message de log. Supprime la ligne ci-dessous
            e.printStackTrace();
        }

        return contactCount;
    }

}
