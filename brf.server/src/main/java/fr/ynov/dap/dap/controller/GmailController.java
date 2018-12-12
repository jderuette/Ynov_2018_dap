package fr.ynov.dap.dap.controller;

import java.io.IOException;

import javax.security.auth.callback.Callback;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.service.GmailService;
import fr.ynov.dap.dap.service.GoogleService;

/**
 * @author Florian BRANCHEREAU
 *
 */
@RestController
public class GmailController extends GoogleService implements Callback {

    /**.
     * LOG
     */
    protected static final Logger LOG = LogManager.getLogger();
    /**.
     * Declaration de gmailservice
     */
    @Autowired
    private GmailService gmailservice;

    /**
     * @throws Exception constructeur
     * @throws IOException constructeur
     */
    public GmailController() throws Exception, IOException {
        super();
    }

    /**
     * @param userKey nom du compte
     * @return Le nombre de mail non lu
     * @throws Exception fonction
     */
    @RequestMapping("/emailNonLu")
    public String emailUnreads(@RequestParam("userKey") final String userKey) throws Exception {
        int messageUnread = 0;
        messageUnread = gmailservice.getMsgsUnread(userKey);
        LOG.debug(messageUnread);
        String response = "Nombre de mails non lus : " + messageUnread;
        return response;
    }

    /**
     * @param userKey nom utilisateur
     * @return nombre de mail non lu
     * @throws Exception controle des erreur
     */
    @RequestMapping("/emailNonLuORM")
    public String emailUnreadsORM(@RequestParam("userKey") final String userKey) throws Exception {
        int messageUnread = 0;
        messageUnread = gmailservice.getMsgsUnreadORM(userKey);
        LOG.debug(messageUnread);
        String response = "Nombre de mails non lus : " + messageUnread;
        return response;
    }

}
