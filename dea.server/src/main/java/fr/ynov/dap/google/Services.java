
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
import org.springframework.context.annotation.Bean;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
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
import fr.ynov.dap.Launcher;


//TODO dea by Djer nom de classe franhcment pas Top. Un nom de lcasse devrait décrire son rôles et/ou objectif
public abstract class Services
{
    //TODO dea by Djer Javadoc des constantes ?
  protected static final List<String> SCOPES = new ArrayList<String>();
  protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
  protected static NetHttpTransport HTTP_TRANSPORT = null;

  //TODO dea by Djer : l'autowire va "récupérer" l'instance de Config existant. Comme dans le Launcher tu précise que c'est un Bean Spring s'occupe de tout.
  // Un des principe de l'IOC est que tu sait q'un object existe, mais tu ne sait pas "ou" ni "comment", car cela est en général inutile !
  @Autowired
  protected static Config config = Launcher.loadConfig();

  /**
   * Constructeur du parent Services
   * 
   * @throws GeneralSecurityException
   * @throws IOException
   */
  Services() throws GeneralSecurityException, IOException
  {
    HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    SCOPES.add(GmailScopes.GMAIL_LABELS);
    SCOPES.add(CalendarScopes.CALENDAR_READONLY);
    //TODO dea by Djer "all" C'est pas un peu "large" pour juste compter les contacts ?
    SCOPES.addAll(PeopleServiceScopes.all());
  }

  /**
   * Récupération de la variable configuration
   * 
   * @return
   */
  //TODO dea by Djer Tu as deja un Bean de type "Config" en avoir plusieurs risque d'être problématique.
  @Bean
  protected static Config getConfiguration()
  {
    return config;
  }

  /**
   * Creates an authorized Credential object.
   * 
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */
  protected static Credential getCredentials(String user) throws IOException
  {
    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = getFlow(user);

    return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize(user);
  }

  /**
   * Récupération du flow google
   * 
   * @param userId
   * @return
   * @throws IOException
   */
  public static GoogleAuthorizationCodeFlow getFlow(String userId) throws IOException
  {
	  //TODO dea by Djer La config devrait contenir le nom du fichier (en plus du dossier/emplacment).
	  //Le nom de la constante "CREDENTIALS_FOLDER" n'était pas très claire.
	  
    // Load client secrets.
    File in = new File(config.getCredentialFolder() + System.getProperty("file.separator") + "credentials.json");

    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
        new InputStreamReader(new FileInputStream(in), Charset.forName("UTF-8")));

    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
        clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(config.getClientSecretFile())))
            .setAccessType("offline").build();

    if (!userId.equals(""))
    {
      flow.loadCredential(userId);
    }

    return flow;
  }
}
