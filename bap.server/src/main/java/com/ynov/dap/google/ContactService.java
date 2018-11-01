package com.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;
import com.ynov.dap.models.ContactModel;

/**
 * SERVICE FOR CONTACT.
 * @author POL
 */
@Service
@PropertySource("classpath:config.properties")
//TODO bap by Djer Déja sur le parent,
public class ContactService extends GoogleService {

    /** The log. */
    //TODO bap by Djer Pourquoi le LogggerFactory (de SL4J) plutot que le LogManager de Log4J ?
    private Logger log = LoggerFactory.getLogger(CalendarService.class);


    /** The env. */
    @Autowired
    private Environment env;


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
                .setPersonFields("names,emailAddresses")
                .execute();

        List<Person> connections = response.getConnections();
        if (connections != null && connections.size() > 0) {

            ContactModel contact = new ContactModel(connections.size());

            return contact;
        } else {
            //TODO bap by Djer Contextualise le message ("for user : " + user)
            log.info("CONTACT : No contacts found.");
        }
        //TODO bap by Djer retourner ton model avec "0" comme nombre de contacts ?
        return null;
    }
}
