package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;

/**
 * Class to manage Contact API.
 * @author Robin DUDEK
 *
 */
@Service
public class ContactService extends GoogleService {

    /**
     * Get next user's event.
     * @param accountName Current user id
     * @return User's number of contacts linked by userId
     * @throws IOException Exception
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     */
    private Integer getUserContacts(final String accountName) throws GeneralSecurityException, IOException {

        PeopleService peopleService = getService(accountName);

        ListConnectionsResponse response = peopleService.people().connections().list("people/me")
                .setPersonFields("names, emailAdresses")
                .execute();

        Integer userContacts = response.getTotalPeople();

        if (userContacts == null) {
            userContacts = 0;
        }
        return userContacts;
    }

    public Integer getUserContacts(AppUser user)
            throws GeneralSecurityException, IOException {

        if (user.getGoogleAccounts().size() == 0) {
            //TODOD dur by Djer |Audit Code| Ton outils d'audit de code d'indique que c'est étrange de faire un "if" et de ne pas y insérer du code...
        }

        Integer nbContacts = 0;

        for (GoogleAccount gAcc : user.getGoogleAccounts()) {
            String accountName = gAcc.getAccountName();
            nbContacts += getUserContacts(accountName);
        }

        return nbContacts;
    }

    @Override
    protected final String getClassName() {
        return GmailService.class.getName();
    }

    PeopleService getService(String user) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

        PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, this.GetCredentials(user))
                .setApplicationName(config.getApplicationName()).build();
        return service;
    }

}
