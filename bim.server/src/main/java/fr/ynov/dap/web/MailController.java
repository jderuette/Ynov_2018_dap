package fr.ynov.dap.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
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
     * Repository.
     */
    @Autowired
    private AppUserRepository repository;

    /**
     * get unread messages.
     * @param gUser Google user
     * @param userKey Applicative user
     * @return number of unread messages
     * @throws Exception if user not found
     */
    @RequestMapping("/email/nbUnread/{gUser}")
    public final Integer getNbUnreadEmails(@PathVariable final String gUser,
            @RequestParam("userKey") final String userKey) throws Exception {
        Logger logger = LogManager.getLogger();
        logger.info("Récupération du nombre d'emails non lu pour l'utilisateur {}...", gUser);

        AppUser user = repository.findByName(userKey);
        return gmailService.getNbUnreadMailForUser(user, gUser);
    }

}
