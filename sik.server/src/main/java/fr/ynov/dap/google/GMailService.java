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

/**
 * Class to manage Gmail API.
 * @author Kévin Sibué
 *
 */
@Service
public class GMailService extends GoogleAPIService {

    /**
     * Create new Gmail service for user.
     * @param userId Current user
     * @return Instance of gmail services provided by Google API
     * @throws GeneralSecurityException Exception
     * @throws IOException Exception
     * @throws NoConfigurationException Thrown when no configuration found
     */
    public Gmail getService(final String userId)
            throws NoConfigurationException, IOException, GeneralSecurityException {

        Credential cdt = getCredential(userId);
        //TODO sik by Djer Pas de check de "nullité comme dans les autres Services ?
        //TODO sik by Djer Essaye de trovuer un "patern" pour "mutualiser" un maximum de ce code des "getService"

        final String appName = getConfig().getApplicationName();
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        return new Gmail.Builder(httpTransport, getJsonFactory(), cdt).setApplicationName(appName).build();

    }

    /**
     * Get number of unread email for a user.
     * @param user id of the targeted user
     * @return Number of unread email for user linked userId
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     * @throws NoConfigurationException Thrown when configuration is missing
     */
    public Integer getNbUnreadEmails(final String user)
            throws NoConfigurationException, IOException, GeneralSecurityException {

        Gmail gmailService = getService(user);

        Label inboxLabel = gmailService.users().labels().get("me", "INBOX").execute();

        return inboxLabel.getMessagesUnread();

    }

    @Override
    protected final String getClassName() {
        return GMailService.class.getName();
    }

}
