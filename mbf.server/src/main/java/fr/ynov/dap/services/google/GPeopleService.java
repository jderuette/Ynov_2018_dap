package fr.ynov.dap.services.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import fr.ynov.dap.services.google.responses.ServiceResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * The GPeopleService is the service that communicates with the google People API.
 */
@Service
public class GPeopleService extends GoogleService {

    /**
     * This method returns the People service which allows the get the user contacts books' info.
     * @param userKey userKey This is the userKey of the currently authenticated user.
     * @return The People service which allows the get the user contacts books' info.
     * @throws GeneralSecurityException
     * @throws IOException
     */
    private PeopleService getService(final String userKey) throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new PeopleService.Builder(httpTransport, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName((getConfiguration().getApplicationName()))
                .build();
    }

    /**
     * This returns the response of the /event/upcomingEvent client's request.
     * @param userKey This is the login of the user.
     * @return It returns the response of the request.
     * @throws IOException This method can throw an IOException.
     * @throws GeneralSecurityException This method can throw an GeneralSecurityException.
     */
    public final ServiceResponse<ListConnectionsResponse> getTotalNumberOfContacts (final String userKey) throws GeneralSecurityException, IOException {
        ServiceResponse<ListConnectionsResponse> response = new ServiceResponse<>();

        ListConnectionsResponse connections =  getService(userKey).people().connections()
                .list("people/me")
                .setPersonFields("names,emailAddresses")
                .execute();
        if (connections.size() == 0) {
            response.setMessage("You have no contacts yet.");
        } else {
            response.setData(connections);
        }
        return response;
    }
}
