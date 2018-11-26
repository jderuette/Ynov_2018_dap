package fr.ynov.dap.services.google;

import fr.ynov.dap.helpers.GoogleHelper;
import fr.ynov.dap.models.*;
import fr.ynov.dap.repositories.UserRepository;
import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * GmailService
 */
@Service
public class GoogleMailService {

    /**
     * Autowired GoogleHelper
     */
    @Autowired
    private GoogleHelper googleHelper;

    /**
     * Autowired UserRepository
     */
    @Autowired
    private UserRepository userRepository;

    private static final Logger LOGGER = LogManager.getLogger(GoogleMailService.class);

    /**
     * Get number of unread email of all GoogleAccount
     *
     * @param userName userName
     * @return total mail unread
     */
    public int getNumberUnreadEmails(String userName) {

        int emailCount = 0;

        try {
            User user = userRepository.findByName(userName);
            if (user == null) {
                throw new Exception("User does not exist");
            }

            List<GoogleAccount> userGoogleAccountList = user.getGoogleAccountList();
            for (GoogleAccount currentGoogleAccount : userGoogleAccountList) {
                emailCount += googleHelper.getGmailService(currentGoogleAccount.getName()).users().labels().get("me", "INBOX").execute().getMessagesUnread();
            }

        } catch (Exception e) {
            LOGGER.error("Error when trying to count all GoogleAccount unread mails", e);
            e.printStackTrace();
        }

        return emailCount;
    }

}
