package com.ynov.dap.service.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.ynov.dap.domain.AppUser;
import com.ynov.dap.domain.google.GoogleAccount;
import com.ynov.dap.model.MailModel;
import com.ynov.dap.repository.AppUserRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class GoogleMailService.
 */
@Service
public class GoogleMailService extends GoogleService {

    /** The app user repository. */
    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * Gets the nb unread emails.
     *
     * @param account the account
     * @return the nb unread emails
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    private Integer getNbUnreadEmails(GoogleAccount account) throws IOException, GeneralSecurityException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(account.getName()))
                .setApplicationName(getConfig().getApplicationName()).build();

        Label label = service.users().labels().get("me", "INBOX").execute();

        if (label == null) {
            getLogger().info("no message unread for account : " + account.getName());
            return 0;
        }
        
        getLogger().info("nb messages unread " + label.getMessagesUnread() + " for account : " + account.getName());
        return label.getMessagesUnread();
    }

    /**
     * Gets the nb unread emails.
     *
     * @param userKey the user key
     * @return the nb unread emails
     * @throws Exception   the exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public MailModel getNbUnreadEmails(final String userKey) throws Exception, IOException {
        AppUser appUser = appUserRepository.findByName(userKey);

        if (appUser == null) {
            getLogger().error("userKey '" + userKey + "' not found");
            return new MailModel(0);
        }

        List<GoogleAccount> accounts = appUser.getGoogleAccounts();
        //TODO bap by Djer |POO| Nom de méthode pas très bien choisi ("nbEmail" ?)
        MailModel mail = new MailModel();
        Integer nbUnreadMails = 0;

        for (GoogleAccount account : accounts) {
            nbUnreadMails += getNbUnreadEmails(account);
        }
        mail.setUnRead(nbUnreadMails);
        getLogger().info("nb messages unread " + mail.getUnRead() + " for user : " + userKey);

        return mail;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.ynov.dap.service.BaseService#getClassName()
     */
    @Override
    public String getClassName() {
        return GoogleMailService.class.getName();
    }

}
