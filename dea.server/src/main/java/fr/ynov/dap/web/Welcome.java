
package fr.ynov.dap.web;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.DataStore;
import fr.ynov.dap.google.GMailService;


@Controller
public class Welcome
{ 
  @Autowired
  GMailService gmail;

  @RequestMapping("/")
  public String welcome(ModelMap model) throws IOException, GeneralSecurityException
  {
//  Integer nbUnreadEmails = MailController.getNbUnreadEmail("me", "toto", null);
    Integer nbUnreadEmails = -8;

    model.addAttribute("nbEmails", nbUnreadEmails);

    return "welcome";
  }

  @RequestMapping("/GetDataStore")
  public String getDataStore(ModelMap model) throws IOException
  {
    GoogleAuthorizationCodeFlow googleCodeFlow = gmail.getFlow();

    Map<String, String> dataStoreMap = new HashMap<String, String>();
    DataStore<StoredCredential> dataStore = googleCodeFlow.getCredentialDataStore();

    Object[] keySet = dataStore.keySet().toArray();

    for (int i = 0; i < keySet.length; i++)
    {
      dataStoreMap.put(keySet[i].toString(), dataStore.get(keySet[i].toString()).getAccessToken());
    }

    model.addAttribute("dataStore", dataStoreMap);

    return "getDataStore";
  }
}
