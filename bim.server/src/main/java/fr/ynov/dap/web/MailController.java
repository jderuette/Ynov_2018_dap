package fr.ynov.dap.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.google.GmailService;

/**
 * Mail controller.
 * @author MBILLEMAZ
 *
 */
@RestController
public class MailController {

    /**
     * Gmail service.
     */
    @Autowired
    private GmailService gmailService;

    /**
     * get unread messages.
     * @param user Google user
     * @param userKey Applicative user
     * @return number of unread messages
     * @throws Exception if user not found
     */
    @RequestMapping("/email/nbUnread/{user}")
    public final Integer getNbUnreadEmails(@PathVariable final String user,
            @RequestParam("userKey") final String userKey) throws Exception {
      //TODO bim by Djer La majorité de ce code devrait être dans le service (gmailService.getNbUnreadEmails()).
        Logger logger = LogManager.getLogger();
        logger.info("Récupération du nombre d'emails non lu pour l'utilisateur {}...", user);
        Gmail service = gmailService.getService(userKey);
        Label label = service.users().labels().get(user, "INBOX").execute();

        int unreadMessage = label.getMessagesUnread();
        return unreadMessage;

    }
}
