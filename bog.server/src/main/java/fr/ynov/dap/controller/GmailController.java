package fr.ynov.dap.controller;

import java.io.IOException;

import javax.security.auth.callback.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.service.GmailService;
import fr.ynov.dap.service.GoogleService;

/**
 * @author Gaël BOSSER
 * Class GmailController
 * Manage every maps of Gmail
 */
@RestController
//TODO bog by Djer |POO| Pourquoi implementer ce Callback de javax.security ?
public class GmailController extends GoogleService implements Callback {

    /**.
     * gmailService is managed by Spring on the loadConfig()
     */
    @Autowired
    private GmailService gmailService;

    /**.
     * Constructor of GmailController
     * @throws Exception si un problème est survenu lors de la création de l'instance GmailController
     * @throws IOException si un problème est survenu lors de la création de l'instance GmailController
     */
    public GmailController() throws Exception, IOException {
        super();
    }

    /**
     * @param userKey nom de l'userKey attaché aux comptes
     * @return total de mails non lus
     */
    @RequestMapping("/google/mail/{userKey}")
    public int emailsUnreadsAllAccountUser(@PathVariable("userKey") final String userKey) {
        int unreadEmail = 0;
        try {
            unreadEmail = gmailService.getMsgsUnread(userKey);
        } catch (Exception e) {
            LOG.error("Un problème est survenu lors de l'appel du service gmail", "UserKey = " + userKey, e);
        }
        return unreadEmail;
    }
}
