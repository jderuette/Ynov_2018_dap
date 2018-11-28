package fr.ynov.dap.dap.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;

import fr.ynov.dap.dap.App;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.repository.AppUserRepository;

@RestController
public class GmailService{
	@Autowired
	public AppUserRepository appRepo;
	
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
    List<String> labelsNames = new ArrayList<String>();
    if (!labels.isEmpty()) {
      for (Label label : labels) {
        labelsNames.add(label.getName());
      }
    }
    return labelsNames;
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
    AppUser appUser = appRepo.findByUserKey(userKey);
    int nMailCount = 0;
    
    for (GoogleAccount googleAccount : appUser.getGoogleAccounts()) {
    	Gmail service = getService(googleAccount.getAccountName());
    	nMailCount += service.users().labels().get("me", "INBOX").execute().getMessagesTotal();
	}
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