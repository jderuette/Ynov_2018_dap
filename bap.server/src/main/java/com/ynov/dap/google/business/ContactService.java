package com.ynov.dap.google.business;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;
import com.ynov.dap.google.models.ContactModel;

/**
 * Service for contact.
 * @author POL
 */
@Service
public class ContactService extends GoogleService {

    /**
     * Instantiates a new contact service.
     */
    public ContactService() {
        super();
    }

    /**
     * Gets the nb contacts.
     *
     * @param user the user
     * @return the nb contacts
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    public ContactModel getNbContacts(final String user) throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        PeopleService service = new PeopleService.Builder(httpTransport, JSON_FACTORY, getCredentials(user))
                .setApplicationName(env.getProperty("application_name"))
                .build();

        //TODO bap by Djer Attention par defaut limiter à 100 resultats par page.
        ListConnectionsResponse response = service.people().connections()
                .list("people/me")
                .setPageSize(100)
                .setPersonFields("names,emailAddresses")
                .execute();

        List<Person> connections = response.getConnections();
        if (connections != null && connections.size() > 0) {

            ContactModel contact = new ContactModel(connections.size());

            return contact;
        } else {
            getLogger().error("No contacts found for user : " + user);
        }
        return new ContactModel(0);
    }

    @Override
    public String getClassName() {
        return ContactService.class.getName();
    }
}
