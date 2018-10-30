package fr.ynov.dap.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;

/**
 * @author adrij
 *
 */
@Service
public final class GMailService extends GoogleService {
    /**
     * Logger used for logs.
     */
    private static Logger log = LogManager.getLogger();

    /**
     * get gmail service.
     * @param userKey user key for authentication
     * @return Gmail.
     * @throws Exception exception
     */
    public Gmail getService(final String userKey) throws Exception {
        log.info("getGmailService called with userKey=" + userKey);
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(userKey))
                .setApplicationName(getConfig().getApplicationName()).build();
        return service;
    }

    /**
     * Get number of unread email.
     * @param user The user's email address. The special value me can be used to indicate the authenticated user.
     * @param userKey user key used to authenticate the user
     * @return The number of unread email.
     * @throws Exception exception
     */
    public Integer getUnreadEmailsNumber(final String user, final String userKey) throws Exception {
        log.info("getUnreadEmailNumber called with userKey=" + userKey + "; user=" + user);
        String query = "category:primary is:unread";
        Gmail.Users.Messages.List request = getService(userKey).users().messages().list(user).setQ(query);

        int numberOfUnreadEmail = 0;

        do {
            ListMessagesResponse response = request.execute();
            if (response.getMessages() != null) {
                numberOfUnreadEmail += response.getMessages().size();
            }

            request.setPageToken(response.getNextPageToken());

        } while (request.getPageToken() != null && !request.getPageToken().equals(""));

        return numberOfUnreadEmail;
    }

    /**
     * This function is not used. It's comment out to avoid checkStyle alert.
     * It displays the number of mail in each categories.
     * @param user username.
     * @param userKey user key
     * @throws IOException exception
     * @throws Exception exception
     */
    /*private void displayEmailNumberForEachCategory(final String user, final String userKey)
            throws IOException, Exception {
        ListLabelsResponse listResponse = getService(userKey).users().labels().list(user).execute();
        List<Label> labels = listResponse.getLabels();
        if (labels.isEmpty()) {
            System.out.println("No labels found.");
        } else {
            System.out.println("Labels:");
            for (Label label : labels) {
                Label unreads = getService(userKey).users().labels().get(user, label.getName()).execute();
                Integer num = unreads.getMessagesUnread();
                System.out.printf(label.getName() + " - " + num + "\n");
            }
        }
    }*/
}
