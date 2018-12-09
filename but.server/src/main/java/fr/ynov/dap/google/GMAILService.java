package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.google.GoogleAccount;

/**
 * Manage Google GMAIL Service.
 * @author thibault
 *
 */
@Service
//TODO but by Djer |POO| Pourquoi toutes ces majuscules ? 
public class GMAILService extends GoogleService {

    /**
     * Logger for the class.
     */
    //TODO but by Djer |Log4J| Devrait être static (par "principe" car ici tu as un singleton (@Service) tu ne devrais donc pas avoir de "surcout" de création de logger)
    private static Logger logger = LogManager.getLogger();

    /**
     * Connect to google Calendar service.
     * @param accountName ID of user (associate token)
     * @param owner Owner of credential (account name)
     * @return calendar service connected
     * @throws IOException Exception produced by failed interrupted I/O operations
     * @throws GeneralSecurityException Google security exception
     */
    public Gmail getService(final String accountName, final AppUser owner)
            throws IOException, GeneralSecurityException {
        //TODO but by Djer |Log4J| Comme tu autorises la création du même "accountName" pour deux "owner" différent, owner devient une information de contexte potentiellement utile (soit l'ID, soit le userKey)
        logger.info("Generate service GMAIL for user '" + accountName + "'");
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(httpTransport, this.getJsonFactory(), this.getCredentials(accountName, owner))
                .setApplicationName(this.getConfig().getApplicationName()).build();
        return service;
    }
    /**
     * Get number of email unread.
     * @param accountName id of google user
     * @param owner Owner of credential (account name)
     * @return number of email unread
     * @throws IOException google server response error HTTP
     */
    public int getUnreadEmailsNumber(final String accountName, final AppUser owner) throws IOException {
        int numberUnread = 0;
        try {
            Gmail service = this.getService(accountName, owner);
            Gmail.Users.Messages.List requestMessages = service.users().messages().list("me")
                    .setQ("category:primary is:unread");

            do {
                ListMessagesResponse response = requestMessages.execute();
                if (response.getMessages() != null) {
                    numberUnread += response.getMessages().size();
                }
                requestMessages.setPageToken(response.getNextPageToken());
            } while (requestMessages.getPageToken() != null);
        } catch (GeneralSecurityException e) {
            logger.error("Error when fetching email for account '" + accountName + "'.", e);
        }

        return numberUnread;
    }

    /**
     * Get number of email unread for all emails of one user.
     * @param user User of application
     * @return number of email unread
     * @throws IOException google server response error HTTP
     */
    public int getUnreadEmailsNumberOfAllEmails(final AppUser user) throws IOException {
        int numberUnread = 0;

        //TODO but by Djer |POO| (par rapport à mes remarques sur "getService(String AppUser)") Ici on sent bien que GoogleAccount et AppUser n,e sont pas si indépendant que cela (ce qui est une bonne chose, le seul "défaut" c'est que certaines méthodes de ton API laisse croire que ca n'est pas le cas)
        for (GoogleAccount gAccount : user.getGoogleAccounts()) {
            numberUnread += getUnreadEmailsNumber(gAccount.getAccountName(), user);
        }

        return numberUnread;
    }
}
