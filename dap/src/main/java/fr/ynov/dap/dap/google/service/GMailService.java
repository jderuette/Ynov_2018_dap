package fr.ynov.dap.dap.google.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

/**
 * @author David_tepoche
 *
 */
@Service
public class GMailService extends GoogleBaseService {

    /**
     *
     * @param user the client email
     * @return Gmail service.
     * @throws IOException              When the Gmail builder fail
     * @throws GeneralSecurityException When you create the GoogleNetHttpTransport
     */
    private Gmail getService(final String user) throws GeneralSecurityException, IOException {
        return new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), JACKSON_FACTORY, getCredential(user))
                .setApplicationName(getConfig().getApplicationName()).build();
    }

    @Override
    protected final String getClassName() {
        return GMailService.class.getName();
    }

    /**
     * get the number of the email unread of the user.
     *
     * @param user user key
     * @return number of unread mail
     * @throws GeneralSecurityException throw by the getService
     * @throws IOException              throw by the getService
     */
    public Integer nbrEmailUnread(final String user) throws GeneralSecurityException, IOException {

        Gmail gmail = getService(user);
        Label label = gmail.users().labels().get("me", "INBOX").execute();

        return label.getMessagesUnread();

    }
}
