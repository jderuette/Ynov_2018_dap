package fr.ynov.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;

/**
 * . This class extends GoogleService and provides people service features
 *
 * @author Dom
 *
 */
@Service
public class PeopleGService extends GoogleService {

    /**
     * Return the service of people according userId param.
     *
     * @param userId .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    public PeopleService getServices(final String userId) throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport = new NetHttpTransport();

        PeopleService services = new PeopleService.Builder(httpTransport, JSON_FACTORY, getCredentials(userId))
                .setApplicationName(getConfig().getApplicationName()).build();
        return services;
    }

    /**
     * . Return the number of contact according the userId param in the format Int
     *
     * @param userId .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    //TODO phd by Djer |POO| Ton "userId" est en faite un accountName
    public int nbContact(final String userId) throws IOException, GeneralSecurityException {
        return this.getServices(userId).people().connections().list("people/me").setPersonFields("names,emailAddresses")
                .execute().getTotalPeople();
    }

    //TODO phd by Djer |API Google| Nombre de contact sur TOUS les comtpes Google de l'utilisateur ?
}
