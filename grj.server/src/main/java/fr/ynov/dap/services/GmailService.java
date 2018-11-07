package fr.ynov.dap.services;

import com.google.api.services.gmail.model.Label;
import fr.ynov.dap.helpers.GoogleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;

@Service
public class GmailService {

    @Autowired
    private GoogleHelper googleHelper;

    /**
     * Return number of email unread in String
     *
     * @param userKey user to log
     * @return String email unread
     * @throws GeneralSecurityException Exception
     * @throws IOException              Exception
     */
    public final String getNumberUnreadEmails(String userKey) throws GeneralSecurityException, IOException {
        Label label = googleHelper.getGmailService(userKey).users().labels().get("me", "INBOX").execute();
        return label.getMessagesUnread().toString();
    }

}
