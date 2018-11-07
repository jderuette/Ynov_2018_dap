package com.ynov.dap.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.ynov.dap.models.MailModel;

import java.io.IOException;

import org.springframework.stereotype.Service;

/**
 * The Class MailService.
 */
@Service
public class MailService extends GoogleService {

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
        Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(user))
                .setApplicationName(env.getProperty("application_name"))
                .build();

        Label label = service.users().labels().get("me", "INBOX").execute();
        MailModel mail = new MailModel(label.getMessagesUnread());

        getLogger().error("nb messages unread " + label.getMessagesUnread() + " for user : " + user);
        return mail;
    }

    @Override
    public String getClassName() {
        return MailService.class.getName();
    }

}
