
package fr.ynov.dap.google;


import java.io.IOException;
import java.security.GeneralSecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import fr.ynov.dap.Config;


@Service
//TODO dea by Djer JavaDoc de la classe ?
public class PeopleService extends Services
{
  /**
   * Constructeur du service PeopleService
   * 
   * @throws GeneralSecurityException
   * @throws IOException
   */
  PeopleService() throws GeneralSecurityException, IOException
  {
    super();
    // TODO Auto-generated constructor stub
  }

  //TODO deab by Djer JSON_FACTORY deja présent dans la calsse parente
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  private static Logger logger = LogManager.getLogger();

  /**
   * Appel du service des contacts
   * 
   * @param userId
   * @param config
   * @return
   * @throws IOException
   */
  //TODO dea by Djer ne passe pas Config dans chaque méthode, injecte la dans la classe (idéalement dans le parent)
  //TODO dea by Djer Pourquoi en "static" ?
  public static com.google.api.services.people.v1.PeopleService getService(String userId, Config config)
      throws IOException
  {
    logger.info("Début du getService People");

    com.google.api.services.people.v1.PeopleService people = new com.google.api.services.people.v1.PeopleService.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userId)).setApplicationName(config.getApplicationName()).build();

    logger.info("Fin du getService People");

    return people;
  }
}
