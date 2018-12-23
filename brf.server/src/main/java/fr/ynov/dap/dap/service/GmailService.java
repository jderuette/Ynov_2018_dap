package fr.ynov.dap.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.services.gmail.Gmail;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccountData;

/**
 * @author Florian BRANCHEREAU
 * Extends MainService
 */
@Service
public class GmailService extends GoogleService {

    /**.
     * LOG
     */
    protected static final Logger LOG = LogManager.getLogger();
    /**.
     * Declaration de AppUserRepository
     */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * @throws Exception constructeur
     * @throws IOException constructeur
     */
    public GmailService() throws Exception, IOException {

    }

    /**
     * @param userKey nom du compte
     * @return gmailservice
     * @throws IOException fonction
     * @throws GeneralSecurityException fonction
     */
    //TODO brf by Djer |POO| "userKey" est en fait un "accountName"
    private Gmail buildGmailService(final String userKey) throws IOException, GeneralSecurityException {
        Gmail gmailservice = new Gmail.Builder(getHttpTransport(), getJsonFactory(), getCredentials(userKey))
                .setApplicationName(getConfiguration().getApplicationName())
                .build();
        return gmailservice;
    }

    /**
     * @param userKey nom du compte
     * @return GetServiceGmail : Le nombre de mail non lu
     * @throws Exception fonction
     */
  //TODO brf by Djer |POO| "userKey" est en fait un "accountName"
    public int getMsgsUnread(final String userKey) throws Exception {
        return this.buildGmailService(userKey).users().labels().get("me", "INBOX").execute().getMessagesUnread();
    }

    /**.
     * @param userKey Nom de l'utilisateur
     * @return le nombre total de mail pour un compte
     * @throws IOException fonction
     * @throws GeneralSecurityException fonction
     */
    public int getMsgsUnreadORM(final String userKey) throws IOException, GeneralSecurityException {
        int nbMessageUnreadForAll = 0;
        AppUser user = appUserRepository.findByName(userKey);
        List<GoogleAccountData> accounts = user.getAccounts();

        for (GoogleAccountData accountData : accounts) {
            try {
                nbMessageUnreadForAll += getMsgsUnread(accountData.getAccountName());
              //TODO brf by Djer |Log4J| Contextualise tes messages
                LOG.debug("GmailService nbMessageUnreadForAll : " + nbMessageUnreadForAll);
            } catch (Exception e) {
                //TODO brf by Djer |Log4J| "e.printStackTrace()" affiche directement dnas la console. Utilise une LOG (avec le deuxième apramètre en "cause")
                e.printStackTrace();
            }
        }
        return nbMessageUnreadForAll;
    }
}
