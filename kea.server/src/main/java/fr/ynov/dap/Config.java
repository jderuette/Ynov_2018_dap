package fr.ynov.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import org.springframework.stereotype.Component;

/**
 * Contains all configuration needed to call the Google and Microsoft API.
 * @author Antoine
 *
 */

@Component
public class Config {
  /**
   * the path to the storedCredentials.
   */
  private String googleCredentialsFolder;
  /**
   * the file that contains storedCredentials.
   */
  private InputStream googleClientSecretInputStream;
  /**
   * The application name.
   */
  private String googleApplicationName;
  /**
   * The path to the file that contains tokens.
   */
  private String googleTokensDirectoryPath;
  /**
   * the object to use HTTP protocols.
   */
  private NetHttpTransport httpTransport;
  /**
   * the URI authorized to redirect to Ynov Dap.
   */
  private String googleAuthCallBack;
  /**
   * The AppId of the microsoft Web application.
   */
  private String microsoftAppId;
  /**
   * The password used to use the Web application.
   */
  private String microsoftAppPassword;
  /**
   * the URI authorized for the redirect after auth.
   */
  private String microsoftRedirectUrl;
  /**
   * The start of the URL where to authenticate the user.
   */
  private String microsoftAuthority;
  /**
   * the Url to authenticate.
   */
  private String microsoftAuthorizeUrl;

  /**
   * Instantiate the NetHttpTransport specifically for Google.
   * @throws IOException nothing special
   * @throws GeneralSecurityException nothing special
   */
  public Config() throws IOException, GeneralSecurityException {
    googleCredentialsFolder = System.getProperty("user.home")
        + "/Documents/Antoine_Cours/eclipse/credentials/credentials.json";
    googleClientSecretInputStream = new FileInputStream(
        new File(googleCredentialsFolder));
    httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    googleApplicationName = "HoC DaP";
    googleTokensDirectoryPath = System.getProperty("user.home")
        + "/Documents/Antoine_Cours/eclipse/credentials";
    googleAuthCallBack = "/oAuth2Callback";
    microsoftAuthority = "https://login.microsoftonline.com";
    microsoftAuthorizeUrl = microsoftAuthority
        + "/common/oauth2/v2.0/authorize";
    microsoftAppId = null;
    microsoftRedirectUrl = null;
    microsoftAppPassword = null;
  }

  /**
   * get the URL you want to redirect when authentication is done.
   * @return relative url after domain name
   */
  public String getoAuth2CallbackUrl() {
    return googleAuthCallBack;
  }

  /**
   * set the URL you want to redirect when authentication is done.
   * @param authCallbackURL url de redirection
   */
  public void setoAuth2CallbackUrl(final String authCallbackURL) {
    this.googleAuthCallBack = authCallbackURL;
  }

  /**
   * set the httpTransport.
   * @param netHttpTransport needed to communicate
   */
  public void setHttpTransport(final NetHttpTransport netHttpTransport) {
    this.httpTransport = netHttpTransport;
  }

  /**
   * set the directory where the credentials.json is stored.
   * @param credFolder folder that contains google credentials
   */
  public void setCredentialsFolder(final String credFolder) {
    this.googleCredentialsFolder = credFolder;
  }

  /**
   * The InputStream is the superclass of all classes representing
   * an input stream of bytes.
   * @param clientSecretDirectory directory of credentials
   */
  public void setClientSecretDir(final InputStream clientSecretDirectory) {
    this.googleClientSecretInputStream = clientSecretDirectory;
  }

  /**
   * sets the application name.
   * @param appName of your application
   */
  public void setApplicationName(final String appName) {
    this.googleApplicationName = appName;
  }

  /**
   * Sets the directory path to the stored credentials.
   * @param tokensDirectory a string that contains the path
   */
  public void setTokensDirectoryPath(final String tokensDirectory) {
    this.googleTokensDirectoryPath = tokensDirectory;
  }

  /**
   * Gets the directory path to the stored credentials.
   * @return a string that contains the relative path in the project
   */
  public String getCredentialsFolder() {
    return googleCredentialsFolder;
  }

  /**
   * The InputStream is the superclass of all classes representing
   * an input stream of bytes.
   * @return the inputStream
   */
  public InputStream getClientSecretDir() {
    return googleClientSecretInputStream;
  }

  /**
   * Gets the application name.
   * @return  String the application name
   */
  public String getApplicationName() {
    return googleApplicationName;
  }

  /**
   * gets the HTTPTransport that is need to communicate.
   * @return an object to connect in HTTP
   */
  public NetHttpTransport getHttpTransport() {
    return httpTransport;
  }

  /**
   * gets the path where tokens are stored (relative in the project).
   * @return a string that contains the path
   */
  public String getTokensDirectoryPath() {
    return googleTokensDirectoryPath;
  }

  /**
   * get google Credential folder.
   * @return a string
   */
  public String getGoogleCredentialsFolder() {
    return googleCredentialsFolder;
  }

  /**
   * set google Credential path.
   * @param googleCredentialsFolder
   */
  public void setGoogleCredentialsFolder(String googleCredentialsFolder) {
    this.googleCredentialsFolder = googleCredentialsFolder;
  }

  public InputStream getGoogleClientSecretInputStream() {
    return googleClientSecretInputStream;
  }

  public void setGoogleClientSecretInputStream(
      InputStream googleClientSecretInputStream) {
    this.googleClientSecretInputStream = googleClientSecretInputStream;
  }

  public String getGoogleApplicationName() {
    return googleApplicationName;
  }

  public void setGoogleApplicationName(String googleApplicationName) {
    this.googleApplicationName = googleApplicationName;
  }

  public String getGoogleTokensDirectoryPath() {
    return googleTokensDirectoryPath;
  }

  public void setGoogleTokensDirectoryPath(String googleTokensDirectoryPath) {
    this.googleTokensDirectoryPath = googleTokensDirectoryPath;
  }

  public String getGoogleAuthCallBack() {
    return googleAuthCallBack;
  }

  public void setGoogleAuthCallBack(String googleAuthCallBack) {
    this.googleAuthCallBack = googleAuthCallBack;
  }

  public String getMicrosoftAppId() {
    return microsoftAppId;
  }

  public void setMicrosoftAppId(String microsoftAppId) {
    this.microsoftAppId = microsoftAppId;
  }

  public String getMicrosoftAppPassword() {
    return microsoftAppPassword;
  }

  public void setMicrosoftAppPassword(String microsoftAppPassword) {
    this.microsoftAppPassword = microsoftAppPassword;
  }

  public String getMicrosoftRedirectUrl() {
    return microsoftRedirectUrl;
  }

  public void setMicrosoftRedirectUrl(String microsoftRedirectUrl) {
    this.microsoftRedirectUrl = microsoftRedirectUrl;
  }

  public String getMicrosoftAuthority() {
    return microsoftAuthority;
  }

  public void setMicrosoftAuthority(String microsoftAuthority) {
    this.microsoftAuthority = microsoftAuthority;
  }

  public String getMicrosoftAuthorizeUrl() {
    return microsoftAuthorizeUrl;
  }

  public void setMicrosoftAuthorizeUrl(String microsoftAuthorizeUrl) {
    this.microsoftAuthorizeUrl = microsoftAuthorizeUrl;
  }

}
