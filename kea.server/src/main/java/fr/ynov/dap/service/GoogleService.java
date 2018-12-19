package fr.ynov.dap.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

import fr.ynov.dap.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * All services classes extends this class.
 * @author Antoine
 *
 */
public abstract class GoogleService {
  /**
   * the customConfig made in Config.java.
   */
  @Autowired
  private Config customConfig;
  /**
   * used to parse Json.
   */
  protected static final JsonFactory JSON_FACTORY = JacksonFactory
      .getDefaultInstance();
  /**
   * All googles services scopes.
   */
  private List<String> scopes = null;
  /**
   * the current User authentified.
   */
  //TODO kea by Djer |POO| Attnetion très risqué, ton service est un Singleton (ce qui est plutot normal), doc il est partagé entre tous les utilisateurs. Si un utilisateur n'util
  private String gUser = "me";
  /**
   * the clientSecret Directory.
   */
  private GoogleClientSecrets clientSecrets;

  /**
   * get all google scopes.
   * @return a list of String
   */
  //TODO kea by DJer |POO| Pourquoi public ? A la limite "protected". Tu peux même le supprimer, pour le moment aucune classe ne l'utilise
  public List<String> getScopes() {
    return scopes;
  }

  /**
   * get the clientSecret File.
   * @return a googleClientSecret File
   */
  public GoogleClientSecrets getClientSecrets() {
    return clientSecrets;
  }

  /**
   * instantiate all the scopes needed in the whole application.
   * @throws InstantiationException nothing special
   * @throws IllegalAccessException nothing special
   */

  public GoogleService() throws InstantiationException, IllegalAccessException {
    init();
  }

  /**
   * Initialize the list of scopes used in the application.
   * TODO kea by Djer |Gestion Exception| Ces exceptions ne sont pas (plus) levée par le code
   * @throws InstantiationException nothing special
   * @throws IllegalAccessException nothing special
   */
  public void init() throws InstantiationException, IllegalAccessException {
    ArrayList<String> myscopes = new ArrayList<String>(
        Arrays.asList(GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_READONLY,
            CalendarScopes.CALENDAR_READONLY, PeopleServiceScopes.CONTACTS_READONLY));
    this.scopes = myscopes;
  }

  //TODO kea by Djer |POO| Evite de melanger des getter/setter au milieu de tes méthodes métiers
  /**
   * returns the current user.
   * @return a String that contains "me"
   */
  public String getgUser() {
    return gUser;
  }

  /**
   * get the configuration object set in Launcher class.
   * @return the configuration
   */
  public Config getCustomConfig() {
    return customConfig;
  }

  /**
   * set the configuration set in Launcher class.
   * @param newConfig autowired attribute
   */
  public void setCustomConfig(final Config newConfig) {
    this.customConfig = newConfig;
  }

  /**
   * Checks if the user that is trying to
   * connect has already been stored in credentials.
   * @param userKey the new userId
   * @return the token for the userId that corresponds to userKey
   * @throws IOException nothing special
   */
  protected Credential getCredentials(final String userKey) throws IOException {
    final GoogleAuthorizationCodeFlow flow = getFlow();
    return flow.loadCredential(userKey);
  }

  /**
   * gets the user.
   * @return the current user that is using the application
   */
  public String getUser() {
    return gUser;
  }

  /**
   * sets the user with you want to authenticate.
   * @param user the userId
   */
  public void setUser(final String user) {
    this.gUser = user;
  }

  /**
   *  the application has access to users' data
   *  based on an access token and a refresh token
   *  to refresh that access token when it expires.
   * @return the token
   * @throws IOException nothing special
   */
  public GoogleAuthorizationCodeFlow getFlow() throws IOException {
    InputStream in = new FileInputStream(
        getCustomConfig().getCredentialsFolder());
    this.clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
        new InputStreamReader(in));
    return new GoogleAuthorizationCodeFlow.Builder(
        getCustomConfig().getHttpTransport(), JSON_FACTORY, clientSecrets,
        scopes)
            .setDataStoreFactory(new FileDataStoreFactory(
                new java.io.File(getCustomConfig().getTokensDirectoryPath())))
            .setAccessType("offline").build();
  }
}
