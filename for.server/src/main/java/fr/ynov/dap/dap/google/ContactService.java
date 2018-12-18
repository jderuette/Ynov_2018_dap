package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;

import fr.ynov.dap.dap.App;

@RestController
public class ContactService {
/**
 * Retourne le nombre de contacts
 * @param userKey
 * @return
 * @throws IOException
 * @throws GeneralSecurityException
 */
  @RequestMapping("/nbContact")
  public int GetNbContacts(@RequestParam("userKey") final String userKey) throws IOException, GeneralSecurityException {
    PeopleService service = getService(userKey);
    int nbcontacts = 0;

    ListConnectionsResponse response = service.people().connections()
         .list("people/me")
         .setPersonFields("names,emailAddresses")
         .execute();

    //TODO for by Djer |POO| Ce commentaire n'est plus juste
    // Print display name of connections if available.
    List<Person> connections = response.getConnections();
    if (connections != null && connections.size() > 0) {
    	nbcontacts = connections.size();
    }
    return nbcontacts;
  }

  /**
   * Créer le service permettant l'accès aux contacts
   * @param userKey
   * @return
   * @throws IOException
   * @throws GeneralSecurityException
   */
  private PeopleService getService(String userKey) throws IOException, GeneralSecurityException {
    // Build a new authorized API client service.
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, App.config.GetJsonFactory(), 
        GoogleService.getCredentials(HTTP_TRANSPORT, userKey))
        .setApplicationName(App.config.GetApplicationName())
        .build();

    return service;
  }
}
