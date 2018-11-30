package fr.ynov.dap.dap.google.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.people.v1.PeopleService;

/**
 *
 * @author David_tepoche
 *
 */
@Service
public class ContactService extends GoogleBaseService {

    /**
     *
     * @param userId user key //TODO duv by Djer |JavaDoc| Renomer la variable ? (@param choux : C'est une Licorne !)
     * @return Gmail service.
     * @throws IOException              throw by the getCRedential from baseService
     * @throws GeneralSecurityException throw by the getCRedential from baseService
     */
    private PeopleService getService(final String userId) throws GeneralSecurityException, IOException {
        return new PeopleService.Builder(GoogleNetHttpTransport.newTrustedTransport(), JACKSON_FACTORY,
                getCredential(userId)).setApplicationName(getConfig().getApplicationName()).build();
    }

    /**
     * get the number total of contact.
     *
     * @param userId connected user
     * @return number of contact.
     * @throws IOException              when you retrieve your local token
     * @throws GeneralSecurityException if error in credential
     */
    public Integer getNbrContact(final String userId) throws IOException, GeneralSecurityException {
        return getService(userId).people().connections().list("people/me").setPersonFields("emailAddresses").execute()
                .getTotalPeople();
    }

    @Override
    protected final String getClassName() {

        return ContactService.class.getName();
    }
}
