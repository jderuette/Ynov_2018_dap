package fr.ynov.dap.services.Google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * The GPeopleService is the service that communicates with the Google People API.
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

    //TODO mbf by Djer Ce COmmentaire semble inaproprié ....
    /**
     * This returns the response of the /event/upcomingEvent client's request.
     * @param userKey This is the userKey of the currently authenticated user.
     * @return It returns the response of the request.
     * @throws IOException This method can throw an IOException.
     * @throws GeneralSecurityException This method can throw an GeneralSecurityException.
     */
    public final Map<String, Object> getTotalNumberOfContacts (final String userKey) throws GeneralSecurityException, IOException {

        ListConnectionsResponse connections =  getService(userKey).people().connections()
                .list("people/me")
                .setPersonFields("names,emailAddresses")
                .execute();
        if (connections.size() == 0) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "You have no contacts yet.");
            return response;
        } else {
            return connections;
        }
    }
}
