package fr.ynov.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.exception.NoConfigurationException;
import fr.ynov.dap.model.AppUser;
import fr.ynov.dap.model.google.GoogleAccount;

/**
 * Class to manage Gmail API.
 * @author Kévin Sibué
 *
 */
@Service
public class GMailService extends GoogleAPIService<Gmail> {

    /**
     * Create new Gmail service for user.
     * @param accountName Current user
     * @return Instance of gmail services provided by Google API
     * @throws GeneralSecurityException Exception
     * @throws IOException Exception
     * @throws NoConfigurationException Thrown when no configuration found
     */
    //TODO sik by Djer |POO| Pourquoi surcharger la "getService()", implementer la méthode "getGoogleClient" semble suffisant ?
    @Override
    public Gmail getService(final String accountName)
            throws NoConfigurationException, IOException, GeneralSecurityException {

        Credential cdt = getCredential(accountName);

        final String appName = getConfig().getApplicationName();
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        return new Gmail.Builder(httpTransport, getJsonFactory(), cdt).setApplicationName(appName).build();

    }

    /**
     * Get number of unread email for a user.
     * @param accountName id of the targeted user
     * @return Number of unread email for user linked userId
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     * @throws NoConfigurationException Thrown when configuration is missing
     */
    private Integer getNbUnreadEmails(final String accountName)
            throws NoConfigurationException, IOException, GeneralSecurityException {

        Gmail gmailService = getService(accountName);

        Label inboxLabel = gmailService.users().labels().get("me", "INBOX").execute();

        return inboxLabel.getMessagesUnread();

    }

    /**
     * Number of unread email for a specifc user.
     * @param user Dap User
     * @return Number of unread email
     * @throws NoConfigurationException No configuration found
     * @throws IOException Exception
     * @throws GeneralSecurityException Security exception
     */
    public Integer getNbUnreadEmails(final AppUser user)
            throws NoConfigurationException, IOException, GeneralSecurityException {

        if (user.getGoogleAccounts().size() == 0) {
            return 0;
        }

        Integer nbUnreadMail = 0;

        for (GoogleAccount gAcc : user.getGoogleAccounts()) {
            nbUnreadMail += getNbUnreadEmails(gAcc.getAccountName());
        }

        return nbUnreadMail;

    }

    @Override
    protected final String getClassName() {
        return GMailService.class.getName();
    }

    @Override
    protected final Gmail getGoogleClient(final NetHttpTransport httpTransport, final Credential cdt,
            final String appName) {
        return new Gmail.Builder(httpTransport, getJsonFactory(), cdt).setApplicationName(appName).build();
    }

}
