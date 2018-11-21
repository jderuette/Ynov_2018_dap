package fr.ynov.dap;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class GoogleService {
  @Autowired
  protected Config customConfig;
  protected static final JsonFactory JSON_FACTORY = JacksonFactory
      .getDefaultInstance();
  protected List<String> scopes = null;
  protected String user = "me";
  //protected GoogleAuthorizationCodeFlow flow = null;
  protected GoogleClientSecrets clientSecrets;

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
   * @throws InstantiationException nothing special
   * @throws IllegalAccessException nothing special
   */
  public void init() throws InstantiationException, IllegalAccessException {
    ArrayList<String> myscopes = new ArrayList<String>(
        Arrays.asList(GmailScopes.GMAIL_LABELS, GmailScopes.GMAIL_READONLY,
            CalendarScopes.CALENDAR_READONLY));
    this.scopes = myscopes;
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
    return user;
  }

  /**
   * sets the user with you want to authenticate.
   * @param user the userId
   */
  public void setUser(final String user) {
    this.user = user;
  }

  /**
   *  the application has access to users' data
   *  based on an access token and a refresh token
   *  to refresh that access token when it expires.
   * @return the token
   * @throws IOException nothing special
   */
  public GoogleAuthorizationCodeFlow getFlow() throws IOException {
    InputStream in = GmailService.class
        .getResourceAsStream(getCustomConfig().getCredentialsFolder());
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
