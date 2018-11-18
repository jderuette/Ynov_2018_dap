package com.ynov.dap.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.ynov.dap.data.AppUser;
import com.ynov.dap.data.GoogleAccount;
import com.ynov.dap.models.MailModel;
import com.ynov.dap.repository.AppUserRepository;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Class MailService.
 */
@Service
public class MailService extends GoogleService {

    @Autowired
    private AppUserRepository appUserRepository;
	
    /**
     * Instantiates a new mail service.
     */
    public MailService() {
        super();
    }

    /**
     * Gets the nb unread emails.
     *
     * @param user the user
     * @return the nb unread emails
     * @throws Exception the exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public MailModel getNbUnreadEmails(final String user) throws Exception, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        AppUser appUser = appUserRepository.findByName(user);
        List<GoogleAccount> accounts = appUser.getGoogleAccounts();

        MailModel mail = new MailModel();
        mail.setUnRead(0);

        for (GoogleAccount account: accounts) {
            Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(account.getName()))
                    .setApplicationName(env.getProperty("application_name"))
                    .build();
            
            Label label = service.users().labels().get("me", "INBOX").execute();
            mail.setUnRead(mail.getUnRead() + label.getMessagesUnread());
        }

        getLogger().info("nb messages unread " + mail.getUnRead() + " for user : " + user);
        return mail;
    }

    @Override
    public String getClassName() {
        return MailService.class.getName();
    }

}
