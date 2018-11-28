package fr.ynov.dap.services.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import fr.ynov.dap.data.google.AppUser;
import fr.ynov.dap.data.google.GoogleAccount;
import fr.ynov.dap.repositories.AppUserRepository;
import fr.ynov.dap.services.google.responses.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Logger;

/**
 * The GMailService is the service that communicates with the GMail API.
 */
@Service
public class GMailService extends GoogleService {

    @Autowired
    private AppUserRepository appUserRepository;

    /**
     * The logger of the GMailService class.
     */
    private Logger logger  = Logger.getLogger(GMailService.class.getName());

    /**
     * This method returns the Gmail service that allows the app to access Gmail mailboxes including sending user email.
     * @param userKey This is the login of the user.
     * @return The Gmail service which allows the app to access Gmail mailboxes including sending user email.
     * @throws GeneralSecurityException The GMailService can throw an GeneralSecurityException.
     * @throws IOException The GMailService can throw an IOException.
     */
    private Gmail getService(final String userKey) throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        return new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(getConfiguration().getApplicationName())
                .build();
    }

    /**
     * This returns the response of the /email/nbUnread client's request.
     * @param userKey This is the login of the user.
     * @return It returns the response of the request.
     */
    public final ServiceResponse<Label> getUnreadEmail (final String userKey) {
        ServiceResponse<Label> response = new ServiceResponse<>();

        AppUser appUser = appUserRepository.findByName(userKey);

        try {
            Label label = getService(userKey).users().labels().get("me", "INBOX").execute();
            response.setData(label);
        } catch (IOException | GeneralSecurityException e) {
            logger.severe("Failed to get unread email from GMail service with the following error: " + e.getMessage());
        }
        return response;
    }
}
