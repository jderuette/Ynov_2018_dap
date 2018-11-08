
package fr.ynov.dap.google;


import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * Classe du service des mails gmail
 * 
 * @author antod
 *
 */
@Service
public class GMailService extends GoogleServices
{
  /**
   * Constructeur de la classe GMailService
   * 
   * @throws GeneralSecurityException
   * @throws IOException
   */
  public GMailService() throws GeneralSecurityException, IOException
  {
    super();
  }

  /**
   * Variable pour logger
   */
  private final Logger LOGGER = LogManager.getLogger();

  /**
   * Renvoie le service Gmail avec l'utilisateur souhaité
   * 
   * @param userId
   * @return
   * @throws GeneralSecurityException
   * @throws IOException
   */
  public Integer getNbUnreadEmail(String userId) throws GeneralSecurityException, IOException
  {
    LOGGER.info("Début du getService Gmail pour l'utilisateur " + userId);

    Gmail gmail = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userId))
        .setApplicationName(super.config.getApplicationName()).build();
    
    Label label = gmail.users().labels().get("me", "INBOX").execute();

    LOGGER.info("Fin du getService pour l'utilisateur " + userId);

    return label.getMessagesUnread();
  }
}
