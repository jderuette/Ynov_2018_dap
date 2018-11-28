package fr.ynov.dap.google;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.google.GoogleAccount;

/**
 * Gmail service.
 * @author MBILLEMAZ
 *
 */
@Service
public final class GmailService extends CommonGoogleService {

    /**
     * Logger.
     */
    public static final Logger LOGGER = LogManager.getLogger();
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

    /**
     * get nb unread mails.
     * @param user applicative User
     * @param gUser gmail user
     * @return nb unread mails
     * @throws Exception if error
     */
    public int getNbUnreadMailForUser(final AppUser user, final String gUser) throws Exception {
        List<GoogleAccount> accountNames = user.getGoogleAccount();

        int unreadMessage = 0;
        for (int i = 0; i < accountNames.size(); i++) {
            String name = accountNames.get(i).getName();
            Gmail service = getService(name);
            Label label = service.users().labels().get(gUser, "INBOX").execute();
            unreadMessage += label.getMessagesUnread();
            LOGGER.info("{} mails non lu pour le compte {}", label.getMessagesUnread(), name);
        }

        return unreadMessage;

    }

}
