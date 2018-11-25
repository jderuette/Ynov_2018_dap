
package fr.ynov.dap.web;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Servlet;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.DataStore;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.google.GMailService;


/**
 * Controlleur welcome
 * 
 * @author antod
 *
 */
@Controller
public class Welcome
{
  /**
   * Variable appUserRepository
   */
  @Autowired
  private AppUserRepository appUserRepository;

  /**
   * Service gmail global
   */
  @Autowired
  GMailService gmail;

  /**
   * Page principal
   * 
   * @param model
   * @return
   * @throws IOException
   * @throws GeneralSecurityException
   */
  @RequestMapping("/")
  public String welcome(ModelMap model) throws IOException, GeneralSecurityException
  {
    return "welcome";
  }

  /**
   * Data Store de l'application
   * 
   * @param model
   * @return
   * @throws IOException
   */
  @RequestMapping("/GetDataStore")
  public String getDataStore(ModelMap model, final HttpServletRequest request) throws IOException
  {
    GoogleAuthorizationCodeFlow googleCodeFlow = gmail.getFlow();
    
    String serverName = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

    List<DataStoreDap> dataStoreList = new ArrayList<DataStoreDap>();
    DataStoreDap dataStoreDap = null;

    DataStore<StoredCredential> dataStore = googleCodeFlow.getCredentialDataStore();
    List<AppUser> appUserList = (List<AppUser>) appUserRepository.findAll();
    AppUser appUser = new AppUser();

    List<GoogleAccount> googleAccountList = null;
    GoogleAccount googleAccount = null;
    List<MicrosoftAccount> microsoftAccountList = null;
    MicrosoftAccount microsoftAccount = null;

    Object[] keySet = dataStore.keySet().toArray();

    // On ajoute tous les comptes de google
    for (int i = 0; i < appUserList.size(); i++)
    {
      appUser = appUserList.get(i);
      googleAccountList = appUser.getGoogleAccounts();
      microsoftAccountList = appUser.getMicrosoftAccounts();

      // Récupération des infos des comptes google
      for (int j = 0; j < googleAccountList.size(); j++)
      {
        googleAccount = googleAccountList.get(j);

        // On itère sur le keySet pour récupérer les bons comptes google
        for (int k = 0; k < keySet.length; k++)
        {
          if (keySet[k].toString().equals(googleAccount.getUserName()))
          {
            dataStoreDap = new DataStoreDap();

            dataStoreDap.setCredentialType("Google");
            dataStoreDap.setUserName(keySet[k].toString());
            dataStoreDap.setToken(dataStore.get(keySet[k].toString()).getAccessToken());

            dataStoreList.add(dataStoreDap);
          }
        }
      }

      // Récupération des infos des comptes microsoft
      for (int j = 0; j < microsoftAccountList.size(); j++)
      {
        microsoftAccount = microsoftAccountList.get(j);

        dataStoreDap = new DataStoreDap();

        dataStoreDap.setCredentialType("Microsoft");
        dataStoreDap.setUserName(microsoftAccount.getUserName());
        dataStoreDap.setTenantId(microsoftAccount.getTenantId());
        dataStoreDap.setMailUrl(serverName + "/mail?userKey=" + microsoftAccount.getOwner().getName());

        dataStoreList.add(dataStoreDap);
      }
    }

    model.addAttribute("dataStore", dataStoreList);

    return "getDataStore";
  }
}
