package fr.ynov.dap.googleService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;

@Service
public class ContactsService extends GoogleService {
    public ContactsService() throws UnsupportedOperationException, GeneralSecurityException, IOException {
        super();

    }

    public List<Person> connections(String userKey) throws IOException, GeneralSecurityException {

        PeopleService service;
        service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, userKey))
                .setApplicationName(configuration.getApplicationName()).build();

        //TODO bes by Djer |Google API| Tu devrais utilsier la pagination, comme dans fr.ynov.dap.googleService.GMailService.getListEmails(String)
        ListConnectionsResponse response = service.people().connections().list("people/me").setPageSize(1000)
                .setPersonFields("names,emailAddresses").execute();
        return response.getConnections();
    }

}
