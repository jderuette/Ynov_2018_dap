
package fr.ynov.dap.google;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;
import fr.ynov.dap.Config;


/**
 * Classe parente des services google dap
 * 
 * @author antod
 *
 */
public abstract class GoogleServices
{
  /**
   * Scopes des services utilisés
   */
  protected final List<String> SCOPES = new ArrayList<String>();
  /**
   * json factory
   */
  protected final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  /**
   * http transport
   */
  protected NetHttpTransport HTTP_TRANSPORT = null;

  /**
   * Configuration global à l'application
   */
  @Autowired
  protected Config config;

  /**
   * Constructeur du parent Services
   * 
   * @throws GeneralSecurityException
   * @throws IOException
   */
  GoogleServices() throws GeneralSecurityException, IOException
  {
    HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    SCOPES.add(GmailScopes.GMAIL_LABELS);
    SCOPES.add(CalendarScopes.CALENDAR_READONLY);
    SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
  }

  /**
   * Récupération de la variable configuration
   * 
   * @return
   */
  protected Config getConfiguration()
  {
    return config;
  }

  /**
   * Creates an authorized Credential object.
   * 
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */
  protected Credential getCredentials(String user) throws IOException
  {
    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = getFlow();

    return flow.loadCredential(user);
  }

  /**
   * Récupération du flow google
   * 
   * @param userId
   * @return
   * @throws IOException
   */
  public GoogleAuthorizationCodeFlow getFlow() throws IOException
  {
    String folder = config.getCredentialFolder();

    if (folder != null && !folder.substring(folder.length() - 1).equals(System.getProperty("file.separator")))
    {
      folder += System.getProperty("file.separator");
    }

    // Load client secrets.
    File in = new File(config.getCredentialFolder() + "credentials.json");

    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
        new InputStreamReader(new FileInputStream(in), Charset.forName("UTF-8")));

    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
        clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(config.getClientSecretFile())))
            .setAccessType("offline").build();

    return flow;
  }
}
