
package fr.ynov.dap.google;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import fr.ynov.dap.Config;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
//TODO dea by Djer JavaDoc de la classe ?
public class GMailService extends Services {
    /**
     * Constructeur de la classe GMailService
     * 
     * @throws GeneralSecurityException
     * @throws IOException
     */
    GMailService() throws GeneralSecurityException, IOException {
        super();
        // TODO Auto-generated constructor stub
    }

    //TODO dea by Djer : JSON_FACOTORY est deja existant dans le parent, inutile d'en re créer une ici !
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static Logger logger = LogManager.getLogger();

    /**
     * Renvoie le service Gmail avec l'utilisateur souhaité
     * 
     * @param userId
     * @return
     * @throws GeneralSecurityException
     * @throws IOException
     */
    //TODO dea by Djer "lourd" de passer la config à chaque fois, tu peux l'injecter.
    // deplus, elle déja injectée dans la méthode appelante. Tu peux remercier Spring d'injecter automatiquement dans les "mappingRequest" ! 
    public static Gmail getService(String userId, Config config) throws GeneralSecurityException, IOException {
        //TODO dea by Djer Contextualise avec l'utilisateur pour lequel le service est créé.
        logger.info("Début du getService Gmail");

        Gmail gmail = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(userId))
                .setApplicationName(config.getApplicationName()).build();

        //TODO dea by Djer Contextualise aussi la log de fin avec "du getService Gmail pour l'utilisateur xxxxx" 
        logger.info("Fin du getService");

        return gmail;
    }
}
