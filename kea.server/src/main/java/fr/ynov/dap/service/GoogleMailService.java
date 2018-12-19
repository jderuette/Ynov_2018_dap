package fr.ynov.dap.service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * A class that uses the Gmail service to display the amount of unread mails.
 * @author Antoine
 *
 */
@Service
public class GoogleMailService extends GoogleService {
  /**
   * The gmail service to use the google API.
   */
  private Gmail gmailService = null;
  /**
   * the object used to report in log.
   */
  private static final Logger LOGGER = LogManager
      .getLogger(GoogleService.class);

  /**
   * Get all dependencies needed to create the service.
   * @throws IOException nothing special
   * @throws InstantiationException nothing special
   * @throws IllegalAccessException nothing special
   */
  public GoogleMailService()
      throws IOException, InstantiationException, IllegalAccessException {
    super();
  }

  /**
   * Creates or gets the googleService(Gmail).
   * @param userKey the current user that is connected
   * @return the gmail service
   * @throws IOException nothing special
   */
  private Gmail getService(final String userKey) throws IOException {
    //TODO kea by Djer |POO| Comme pour CalendatService tu ne peux pas en avoir un isntance partagée entre tous tes comptes Google
    if (gmailService == null) {
      //TODO kea by Djer |POO| (ancien TO-DO) Attention en multi-compte tu vas avoir des problemes, car tu va "mettre en cache" le service du premier utilisateur. Et tout les autres ne verront QUE les informations de ce premier utilisateur.
      gmailService = new Gmail.Builder(getCustomConfig().getHttpTransport(),
          JSON_FACTORY,
          getCredentials(userKey))
              .setApplicationName(getCustomConfig().getApplicationName()).build();
    }
    return gmailService;
  }

  /**
   * Counts all unread messages from primary category in Gmail.
   * @param userKey the identifier that wants to access his unread mails
   * @return an integer that tells you how many mails unread you have
   * @throws IOException nothing special
   */
  public int getUnreadMessageCount(final String userKey) throws IOException {
    gmailService = getService(userKey);
    ListMessagesResponse listMessagesResponse = gmailService.users().messages()
        .list(getUser())
        .setQ(
                //TODO kea by Djer |POO| Pourquoi faire des concaténation ? Elles ne sont pas utiles ici
            "in:unread -category:" + "{social promotions updates forums}" + "")
        .execute();
    List<Message> messages = new ArrayList<Message>();
    while (listMessagesResponse.getMessages() != null) {
      messages.addAll(listMessagesResponse.getMessages());
      if (listMessagesResponse.getNextPageToken() != null) {
        String pageToken = listMessagesResponse.getNextPageToken();
        listMessagesResponse = gmailService.users().messages().list(getUser())
            .setPageToken(pageToken).execute();
      } else {
        break;
      }
    }
    int unreadMessageCount = 0;
    for (int i = 0; i < messages.size(); i++) {
      unreadMessageCount++;
    }
    return unreadMessageCount;
  }

  /**
   * gets all labels in Gmail Service.
   * @param userKey the user that wants to access his account data
   * @return a string list
   * @throws IOException nothing special
   */
  public List<String> getLabels(final String userKey) throws IOException {
    gmailService = getService(userKey);
    ListLabelsResponse listResponse = gmailService.users().labels()
        .list(getUser()).execute();
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
   * get the singleton logger.
   * @return the logger
   */
//TODO kea by Djer |POO| Pourquoi public ? MailController l'utilise, mais il ne devrait pas, il doit logger dans sa propre catégorie
  public Logger getLogger() {
    return LOGGER;
  }
}
