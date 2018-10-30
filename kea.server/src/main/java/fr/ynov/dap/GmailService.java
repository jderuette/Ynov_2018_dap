package fr.ynov.dap;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * A class that uses the Gmail service to display the amount of unread mails.
 * @author Antoine
 *
 */
@RestController
//FIXME séparation Controller/Service ??
public class GmailService extends GoogleService {
    /**
     * The gmail service to use the google API.
     */
    private Gmail gmailService = null;

    /**
     * Get all dependencies needed to create the service.
     * @throws IOException nothing special
     * @throws InstantiationException nothing special
     * @throws IllegalAccessException nothing special
     */
    public GmailService() throws IOException, InstantiationException, IllegalAccessException {
        init();
        setCustomConfig(customConfig);
    }

    /**
     * Creates or gets the googleService(Gmail).
     * @param user the current user that is connected
     * @return the gmail service
     * @throws IOException nothing special
     */
    //TODO kea by Djer Devrait être privée
    public Gmail getService(final String user) throws IOException {
        if (gmailService == null) {
            //TODO kea by Djer Attention ne multi-compte tu vas avoir des probleme,
            // car tu va "mettre en cache" le service du premier utilisateur.
            // Et tout les autre ne verront QUE les informations de ce premier utilisateur.
            gmailService = new Gmail.Builder(customConfig.getHttpTransport(), JSON_FACTORY,
                    //TODO kea by Djer format ton code ! (+remarque CheckStyle)
                    getCredentials(user)).setApplicationName(customConfig.getApplicationName()).build();
        }
        return gmailService;
    }

    /**
     * Counts all unread messages from primary category in Gmail.
     * @return an integer that tells you how many mails unread you have
     * @throws IOException nothing special
     */
    public int getUnreadMessageCount() throws IOException {
        //TODO kea by Djer "me" est un Id sur les Serveurs de Google, ici il faut uiliser un ID "de l'application"
        gmailService = getService(user);
        ListMessagesResponse listMessagesResponse = gmailService.users().messages().list(getUser())
                .setQ("in:unread -category:" + "{social promotions updates forums}" + "").execute();
        List<Message> messages = new ArrayList<Message>();
        while (listMessagesResponse.getMessages() != null) {
            messages.addAll(listMessagesResponse.getMessages());
            if (listMessagesResponse.getNextPageToken() != null) {
                String pageToken = listMessagesResponse.getNextPageToken();
                listMessagesResponse = gmailService.users().messages().list(getUser()).setPageToken(pageToken)
                        .execute();
            } else {
                break;
            }
        }
        int unreadMessageCount = 0;
        //TODO kea by Djer Il y a une methode "size" sur les liste !
        for (Message message : messages) {
            unreadMessageCount++;
        }
        return unreadMessageCount;
    }

    /**
     * returns a string with the amount of unread mails.
     * @param user the current user connected
     * @return a string that contains french words and the unread mails
     * @throws IOException nothing special
     */
    @RequestMapping("/email/nbUnread/{user}")
    public String unreadMessageCountToString(@PathVariable final String user) throws IOException {
        return "Vous avez " + getUnreadMessageCount() + " mails non lus !\n";
    }

    /**
     * gets all labels in Gmail Service.
     * @return a string list
     * @throws IOException nothing special
     */
    public List<String> getLabels() throws IOException {
        gmailService = getService(user);
        ListLabelsResponse listResponse = gmailService.users().labels().list(getUser()).execute();
        List<Label> labels = listResponse.getLabels();
        List<String> listeLabels = new ArrayList<String>();
        if (!labels.isEmpty()) {
            for (Label label : labels) {
                listeLabels.add(label.getName());
            }
        }
        return listeLabels;
    }

    /**
     * Browse all labels and concatenate them in one string to display.
     * @return a string with all labels
     * @throws IOException nothing special
     */
    public String listeLabelToString() throws IOException {
        List<String> listeLabels = getLabels();
        String labelsInString = "";
        if (!listeLabels.isEmpty()) {
            for (String label : listeLabels) {
                labelsInString = labelsInString + label + "\n";
            }
        } else {
            labelsInString = "Aucuns labels trouvés !";
        }
        return labelsInString;
    }
}