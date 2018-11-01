package fr.ynov.dap.google;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.api.services.gmail.Gmail;

/**
 * Gmail service.
 * @author MBILLEMAZ
 *
 */
@org.springframework.stereotype.Service
public final class GmailService extends Service {

    /**
     * Private constructor.
     */
    public GmailService() {
        super();
    }

    /**
     * get gmail service.
     * @param userKey applicative user
     * @return gmail service
     * @throws Exception  if user not found
     */
    public Gmail getService(final String userKey) throws Exception {
        Logger logger = LogManager.getLogger();
        logger.info("Récupération du service Gmail...");
        return new Gmail.Builder(GmailService.getHttpTransport(), GmailService.JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(getConfig().getApplicationName()).build();
    }

}
