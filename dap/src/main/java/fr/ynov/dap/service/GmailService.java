package fr.ynov.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;

/**.
 * This class extends GoogleService and provides Gmail service features
 * @author Dom
 *
 */
@Service
public class GmailService extends GoogleService {
    /**.
     * Return the service of gmail according userId param
     * @param userId .
     * @return .
     * @throws GeneralSecurityException .
     * @throws IOException .
     */
    public Gmail getService(final String userId) throws GeneralSecurityException, IOException {
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(userId))
                .setApplicationName(getConfig().getApplicationName()).build();
        return service;
    }

    /**
     * Return the number of message unread of gmail according the userId param in the format Int.
     * @param userId .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    public int nbMessageUnread(final String userId) throws IOException, GeneralSecurityException {
        return this.getService(userId).users().labels().get("me", "INBOX").execute().getMessagesUnread();
    }
}
