package fr.ynov.dap.google;

import java.io.IOException;

import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.GoogleAccount;

/**
 * The Class GMailService.
 */
@Service
public class GMailService extends GoogleService<Gmail> {
	
	@Override
	protected final String getClassName() {
	        return GMailService.class.getName();
	}
	 
    @Override
    protected final Gmail getGoogleClient(final Credential crendential, final NetHttpTransport httpTransport,final String appName) {
        return new Gmail.Builder(httpTransport, getJSON_FACTORY(), crendential).setApplicationName(appName).build();
    }
	
	 /**
     * Create new Gmail service for user.
     * @param accountName Current user
     * @return Instance of gmail services provided by Google API
     * @throws GeneralSecurityException Exception
     * @throws IOException Exception
     * @throws NoConfigurationException Thrown when no configuration found
     */
    @Override
    public Gmail getService(final String accountName)
            throws IOException, GeneralSecurityException {

        Credential cdt = getCredentials(accountName);

        final String appName = getConfiguration().getApplicationName();
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        return new Gmail.Builder(httpTransport, getJSON_FACTORY(), cdt).setApplicationName(appName).build();

    }

    /**
     * Get number of unread email for a user.
     * @param accountName id of the targeted user
     * @return Number of unread email for user linked userId
     * @throws GeneralSecurityException Thrown when a security exception occurred.
     * @throws IOException Exception
     * @throws NoConfigurationException Thrown when configuration is missing
     */
    private Integer getNbUnreadMail(final String accountName)
            throws IOException, GeneralSecurityException {

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
    public Integer getNbUnreadMailAllAccount(final AppUser user)
            throws IOException, GeneralSecurityException {

        Integer nbUnreadMail = 0;

        for (GoogleAccount googleAccount : user.getGoogleAccounts()) {
            nbUnreadMail += getNbUnreadMail(googleAccount.getAccountName());
        }

        return nbUnreadMail;

    }

}
