package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.people.v1.PeopleService;

import fr.ynov.dap.dap.Config;

/**
 *
 * @author David_tepoche
 *
 */
@Service
public class ContactService extends BaseService {

    /**
     * link with config.
     */
    @Autowired
    // TODO duv by Djer Tu pourrais utiliser la config du parent (en ajoutant un
    // getter protected)
    private Config config;

    /**
     *
     * @param userId user key
     * @return Gmail service.
     * @throws IOException              throw by the getCRedential from baseService
     * @throws GeneralSecurityException throw by the getCRedential from baseService
     */
    private PeopleService getService(final String userId) throws GeneralSecurityException, IOException {
        return new PeopleService.Builder(GoogleNetHttpTransport.newTrustedTransport(), JACKSON_FACTORY,
                getCredential(userId)).setApplicationName(config.getApplicationName()).build();
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
        // TODO duv by Djer Conbien y-a-t-il délément dans uen page par défaut ?
        return getService(userId).people().connections().list("people/me").setPersonFields("emailAddresses").execute()
                .getTotalPeople();
    }

    @Override
    protected final String getClassName() {

        return ContactService.class.getName();
    }
}
