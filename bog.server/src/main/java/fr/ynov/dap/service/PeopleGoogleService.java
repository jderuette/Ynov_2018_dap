package fr.ynov.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.services.people.v1.PeopleService;

/**
 * @author Mon_PC
 * Manage the redirection to the Controller
 * Extends MainService
 */
@Service
public class PeopleGoogleService extends GoogleService {
    /**.
     * Constructor PeopleGoogleService
     * @throws Exception si un problème est survenu lors de la création de l'instance PeopleGoogleService
     * @throws IOException si un problème est survenu lors de la création de l'instance PeopleGoogleService
     */
    public PeopleGoogleService() throws Exception, IOException {
        super();
    }

    /**
     * @return PeopleService correspondant à l'utilisateur
     * @param userId
     * userId parameter put by client
     * @throws IOException si un problème est survenu lors de l'appel à cette fonction
     * @throws GeneralSecurityException si un problème est survenu lors de l'appel à cette fonction
     */
    private PeopleService buildPeopleService(final String userId) throws IOException, GeneralSecurityException {
        PeopleService service = new PeopleService.Builder(getHttpTransport(), getJsonFactory(), getCredentials(userId))
                .setApplicationName(getConfig().getApplicationName()).build();
        return service;
    }

    /**
     * @param userId
     * userId parameter put by client
     * @return numbers of contact of this user
     * @throws IOException si un problème est survenu lors de l'appel à cette fonction
     * @throws GeneralSecurityException si un problème est survenu lors de l'appel à cette fonction
     */
    //TODO bog by Djer |JavaDoc| Pour pouvoir fonctionner le "userId" DOIT être un accountName
    public int getNbContact(final String userId) throws IOException, GeneralSecurityException {
        int nbContact = 0;
        nbContact = this.buildPeopleService(userId).people().connections().list("people/me")
                .setPersonFields("names,emailAddresses").execute().getTotalItems();
        return nbContact;
    }
}
