package fr.ynov.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccountData;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * @author Gaël BOSSER
 * Manage the redirection to the Controller
 * Extends MainService
 */
@Service
public class GmailService extends GoogleService {

    /**.
     * repositoryUser is managed by Spring on the loadConfig()
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**.
     * Constructor GmailService
     * @throws Exception si un problème est survenu lors de la création de l'instance GmailService
     * @throws IOException si un problème est survenu lors de la création de l'instance GmailService
     */
    public GmailService() throws Exception, IOException {
        super();
    }

    /**
     * @param accountName
     * accountName parameter put by client
     * @return le service Gmail de l'utilisateur après l'avoir build
     * @throws IOException si un problème est survenu lors de l'appel à cette fonction
     * @throws GeneralSecurityException si un problème est survenu lors de l'appel à cette fonction
     */
    private Gmail buildGmailService(final String accountName) throws IOException, GeneralSecurityException {
        Gmail service = new Gmail.Builder(getHttpTransport(), getJsonFactory(), getCredentials(accountName))
                .setApplicationName(getConfig().getApplicationName()).build();
        return service;
    }

    /**
     * @param userKey userKey à l'utilisateur dont il faut récupérer les mails non lus
     * @return unread emails
     * @throws Exception si un problème est survenu lors de l'appel à cette fonction
     */
    public int getMsgsUnread(final String userKey) throws Exception {
        int mailsUnreads = 0;
        AppUser user = repositoryUser.findByName(userKey);
        List<GoogleAccountData> accounts = user.getAccounts();
        for (GoogleAccountData accountData : accounts) {
            mailsUnreads += this.buildGmailService(accountData.getAccountName()).users().labels().get("me", "INBOX")
                    .execute().getMessagesUnread();
        }
        return mailsUnreads;
    }

    /**
     * @param userId
     * userId parameter put by client
     * @return Liste des labels de l'utilisateur
     * @throws IOException si un problème est survenu lors de l'appel à cette fonction
     * @throws GeneralSecurityException si un problème est survenu lors de l'appel à cette fonction
     */
    public List<Label> getListLabelsGmail(final String userId) throws IOException, GeneralSecurityException {
        return this.buildGmailService(userId).users().labels().list(userId).execute().getLabels();
    }
}
