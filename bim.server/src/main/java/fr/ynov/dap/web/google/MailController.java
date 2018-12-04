package fr.ynov.dap.web.google;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param userKey Applicative user
     * @return number of unread messages
     * @throws Exception if user not found
     */
    @RequestMapping("/email/google/nbUnread")
    public final Integer getNbUnreadEmails(@RequestParam("userKey") final String userKey) throws Exception {
        Logger logger = LogManager.getLogger();
      //TODO bim by Djer |Log4J| pas mal comme contexte mais ajoute le "userKey" qui est quand même vachement utile aussi (100% de tes utilisateurs vont demander "me" vue que tu ne gère pas la délagation dans ton code)
        logger.info("Récupération du nombre d'emails non lu pour l'utilisateur {}...", "me");

        AppUser user = repository.findByName(userKey);
        return gmailService.getNbUnreadMailForUser(user, "me");
    }

}
