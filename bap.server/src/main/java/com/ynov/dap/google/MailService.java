package com.ynov.dap.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.ynov.dap.models.MailModel;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


/**
 * The Class MailService.
 */
@Service
//TODO bap by Djer Déja sur le parent,
@PropertySource("classpath:config.properties")
public class MailService extends GoogleService {


    /** The log. */
    private Logger log = LoggerFactory.getLogger(CalendarService.class);

    /** The env. */
    @Autowired
    private Environment env;


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

      //TODO bap by Djer Contextualise le message ("for user : " + user)
        log.info("MAIL : nb messages unread " + label.getMessagesUnread());
        return mail;
    }

}
