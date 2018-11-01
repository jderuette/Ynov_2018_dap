package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

/**
 * Class to manage Contact API.
 * @author Kévin Sibué
 *
 */
@Service
public class ContactService extends GoogleAPIService {

    /**
     * Create new Calendar service for user.
     * @param userId Current user id
     * @return Instance of calendar services
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    public PeopleService getService(final String userId) throws GeneralSecurityException, IOException {

        Credential cdt = getCredential(userId);

        if (cdt != null) {

            final String appName = getConfig().getApplicationName();
            final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            return new PeopleService.Builder(httpTransport, getJsonFactory(), cdt).setApplicationName(appName).build();

        }

        return null;

    }

    /**
     * Get next user's event.
     * @param user Current user id
     * @return User's number of contacts linked by userId
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    public Integer getNumberOfContacts(final String user) throws GeneralSecurityException, IOException {

        PeopleService peopleSrv = getService(user);

        ListConnectionsResponse response = peopleSrv.people().connections().list("people/me").setPersonFields("names")
                .execute();

        return response.getTotalPeople();

    }

    @Override
    protected final String getClassName() {
        return ContactService.class.getName();
    }

}
