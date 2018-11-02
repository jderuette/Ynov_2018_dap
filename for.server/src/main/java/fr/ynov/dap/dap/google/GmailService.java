package fr.ynov.dap.dap.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
//TODO for by Djer Configure les "dave action" de ton IDE. Cd mémoe Elcipse
import com.google.api.services.calendar.Calendar;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleService.People;
import com.google.api.services.people.v1.PeopleService.People.Connections;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

import fr.ynov.dap.dap.App;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GmailService{
/**
 * Retourne le nom des labels
 * @param userKey
 * @return
 * @throws IOException
 * @throws GeneralSecurityException
 */
  @RequestMapping("/email/getLabels")
  public List<String> GetLabelsName(@RequestParam("userKey") final String userKey) throws IOException, GeneralSecurityException {

    Gmail service = getService(userKey);

    // Print the labels in the user's account.
    ListLabelsResponse listResponse = service.users().labels().list("me").execute();
    List<Label> labels = listResponse.getLabels();
    List<String> labelsNames = new ArrayList();
    if (labels.isEmpty()) {
      //TODO for by Djer Pas de sysout sur un serveur !
      System.out.println("No labels found.");
    //TODO for by Djer Evite les multiples return dans une même méthode
      return null;
    } else {
      //TODO for by Djer Pas de sysout sur un serveur !
      System.out.println("Labels:");
      for (Label label : labels) {
        //TODO for by Djer Pas de sysout sur un serveur !
        System.out.printf("- %s\n", label.getName());
        labelsNames.add(label.getName());
      }
      return labelsNames;
    }
  }

/**
 * Retourne le nombre de mails non-lus
 * @param userKey
 * @return
 * @throws IOException
 * @throws GeneralSecurityException
 */
  @RequestMapping("/email/nbUnread")
  public int GetEmailNumber(@RequestParam("userKey") final String userKey) throws IOException, GeneralSecurityException {
    Gmail service = getService(userKey);

    //Print the labels in the user's account.
    Label listResponse = service.users().labels().get("me", "INBOX").execute();

    int nMailCount = listResponse.getMessagesTotal();

  //TODO for by Djer Pas de sysout sur un serveur !
    System.out.println("Mail count:");
  //TODO for by Djer Pas de sysout sur un serveur !
    System.out.printf("%s\n", nMailCount);
    return nMailCount;
  }
    
/**
 * Créer le service pour accéder aux emails
 * @param userKey
 * @return
 * @throws IOException
 * @throws GeneralSecurityException
 */
  private Gmail getService(String userKey) throws IOException, GeneralSecurityException
  {
    // Build a new authorized API client service.
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    Gmail service = new Gmail.Builder(HTTP_TRANSPORT, App.config.GetJsonFactory(), GoogleService.getCredentials(HTTP_TRANSPORT, userKey))
        .setApplicationName(App.config.GetApplicationName())
        .build();
        
    return service;
  }
}