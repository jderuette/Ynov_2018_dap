package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ContactGroup;

/**
 * Manage Google Calendar Service.
 * @author thibault
 *
 */
@Service
public class GooglePeopleService extends GoogleService {
    /**
     * Logger for the class.
     */
    //TODO but by Djer Logger generalement en static (pas la peine d'en avoir un par instance)
    // final (pseudo-référence non modifiable)
    private Logger logger = LogManager.getLogger();

    /**
     * Connect to Google People Service.
     * @param userId ID of user (associate token)
     * @return Google People service
     * @throws IOException Exception produced by failed interrupted I/O operations
     * @throws GeneralSecurityException Google security exception
     */
    public PeopleService getService(final String userId) throws IOException, GeneralSecurityException {
        this.logger.info("Generate service People for user '" + userId + "'");
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential cred = getCredentials(userId);
        PeopleService service = new PeopleService.Builder(httpTransport, this.getJsonFactory(), cred)
                .setApplicationName(this.getConfig().getApplicationName()).build();

        return service;
    }

    /**
     * Get number of contacts.
     * @param userId ID of user (associate token)
     * @return int count of contacts
     * @throws GeneralSecurityException problem security with google server
     * @throws IOException error server response google
     */
    public int countContacts(final String userId) throws IOException, GeneralSecurityException {
        this.logger.info("Get number of contacts for user '" + userId + "'");
        //TODO but by Djer Evite se genre de log sans contexte !
        this.logger.info(this.getConfig().getCredentialsFilePath());
        PeopleService service = this.getService(userId);
        ContactGroup allContacts = service.contactGroups().get("contactGroups/all").execute();

        if (allContacts.getMemberCount() != null) {
            //TODO but by Djer Evite de multiple return dans une même méthode
            return allContacts.getMemberCount();
        }
        return 0;
    }
}
