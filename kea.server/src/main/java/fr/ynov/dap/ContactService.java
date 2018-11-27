package fr.ynov.dap;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;

/**
 * This service has access to the GoogleContact API.
 * @author Antoine
 *
 */
@Service
public class ContactService extends GoogleService {
  /**
   * The constructor to use the service.
   * @throws InstantiationException nothing special
   * @throws IllegalAccessException nothing special
   */
  public ContactService()
      throws InstantiationException, IllegalAccessException {
    super();
  }

  /**
   * the GoogleContact service from google.
   */
  private PeopleService contactsService = null;
  /**
   * the object used to write logs.
   */
  private static final Logger LOGGER = LogManager
      .getLogger(GoogleService.class);

  /**
   * Creates a new service if it has never been created
   * else it returns the service instantiated previously.
   * @param userKey the user that wants to access his account datas
   * @return the Calendar service
   * @throws IOException nothing special
   */
  public PeopleService getService(final String userKey) throws IOException {
    if (contactsService == null) {
      contactsService = new PeopleService.Builder(
          getCustomConfig().getHttpTransport(), JSON_FACTORY,
          getCredentials(userKey))
              .setApplicationName(getCustomConfig().getApplicationName())
              .build();
    }
    return contactsService;
  }

  /**
   * Uses the People Service to get the contacts.
   * @param userKey the user that wants to access his account datas
   * @return an Person list
   * @throws IOException nothing special
   */
  public List<Person> getContacts(final String userKey) throws IOException {
    contactsService = getService(userKey);
    ListConnectionsResponse response = null;
    response = contactsService.people().connections().list("people/me")
        .setPersonFields("names,emailAddresses").execute();
    List<Person> connections = response.getConnections();
    if (connections == null) {
        LOGGER.info("Aucun contacts trouves pour le userKey:" + userKey);
    }
    return connections;
  }

  /**
   * Uses the People Service to get the profile info.
   * @param userKey the user that wants to access his account datas
   * @return a person
   * @throws IOException nothing special
   */
  public Person getprofile(final String userKey) throws IOException {
    contactsService = getService(userKey);
    Person profile = null;
    profile = contactsService.people().get("people/me")
        .setPersonFields("names,emailAddresses").execute();
    return profile;
  }

  /**
   * get the singleton logger.
   * @return the logger
   */
  public Logger getLogger() {
    return LOGGER;
  }
}
