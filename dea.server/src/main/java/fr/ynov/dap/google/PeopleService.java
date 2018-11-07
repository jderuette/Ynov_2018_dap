
package fr.ynov.dap.google;


import java.io.IOException;
import java.security.GeneralSecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * Service pour la gestion des contacts
 * 
 * @author antod
 *
 */
@Service
public class PeopleService extends GoogleServices
{
  /**
   * Constructeur du service PeopleService
   * 
   * @throws GeneralSecurityException
   * @throws IOException
   */
  public PeopleService() throws GeneralSecurityException, IOException
  {
    super();
  }

  /**
   * Logger pour afficher des infos
   */
  private final Logger LOGGER = LogManager.getLogger();

  /**
   * Appel du service des contacts
   * 
   * @param userId
   * @param config
   * @return
   * @throws IOException
   */
  public Integer getNbContacts(String userId) throws IOException
  {
    LOGGER.info("Début du getService People pour l'utilisateur " + userId);

    com.google.api.services.people.v1.PeopleService people = new com.google.api.services.people.v1.PeopleService.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userId)).setApplicationName(config.getApplicationName()).build();
    Integer nbContacts = people.people().connections().list("people/" + userId).setPersonFields("names").execute()
        .getTotalPeople();

    LOGGER.info("Fin du getService People pour l'utilisateur " + userId);

    return nbContacts;
  }
}
