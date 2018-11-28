package fr.ynov.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.GoogleAccountData;

/**.
 * This class extends GoogleService and provides Gmail service features
 * @author Dom
 *
 */
@Service
public class GmailService extends GoogleService {

    /**
     * @param userRepository .
     */
    @Autowired
    private AppUserRepository userRepository;

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
     * @param accountName .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    public int nbMessageUnread(final String accountName) throws IOException, GeneralSecurityException {
        return this.getService(accountName).users().labels().get("me", "INBOX").execute().getMessagesUnread();
    }

    /**
     *
     * @param userId .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    public int nbMessageUnreadAll(final String userId) throws IOException, GeneralSecurityException {
        int nbMessageUnreadForAll = 0;
        AppUser user = userRepository.findByName(userId);
        List<GoogleAccountData> accounts = user.getGoogleAccounts();

        for (GoogleAccountData accountData : accounts) {
            nbMessageUnreadForAll += nbMessageUnread(accountData.getAccountName());
        }
        return nbMessageUnreadForAll;
    }

}
