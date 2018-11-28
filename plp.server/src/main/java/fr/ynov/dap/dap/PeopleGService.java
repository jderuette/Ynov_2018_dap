package fr.ynov.dap.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Pierre Plessy
 */
@Service
public class PeopleGService extends GoogleService {
    /**
     * instantiate Logger.
     */
    private static final Logger log = LogManager.getLogger(PeopleGService.class);
    /**
     * instantiate userRepository
     */
    @Autowired
    AppUserRepository userRepository;

    /**
     * Get service.
     *
     * @param userId : default user
     * @return Gmail
     * @throws IOException              : throws exception
     * @throws GeneralSecurityException : throws exception
     */
    private PeopleService getService(final String userId) throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Exception e) {
            log.error("Error when trying to get Service for user : " + userId ,e);
            throw e;
        }
        return new PeopleService.Builder(httpTransport, getJsonFactory(), getCredentials(userId))
                .setApplicationName(getConfig().getApplicationName()).build();
    }

    /**
     * Find the number of contact you have.
     *
     * @param userKey : default user
     * @return Map : total connection
     * @throws IOException              : throws exception
     * @throws GeneralSecurityException : throws exception
     */
    public Integer getNbContacts(final String userKey) throws IOException, GeneralSecurityException {
        AppUser userApp = userRepository.findByName(userKey);
        Integer nbContacts = 0;
        for (GoogleAccount googleAccount: userApp.getGoogleAccount()) {
            ListConnectionsResponse connectionsResponse = getService(googleAccount.getName()).people()
                    .connections().list("people/me").setPersonFields("names,emailAddresses").execute();
            Map<String, Integer> response = new HashMap<>();
            nbContacts += connectionsResponse.getTotalPeople();
        }

        return nbContacts;
    }
}
