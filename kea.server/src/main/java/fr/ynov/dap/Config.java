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
        + "/Documents/credentials/credentials.json";
    googleClientSecretInputStream = new FileInputStream(
        new File(googleCredentialsFolder));
    httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    googleApplicationName = "HoC DaP";
    googleTokensDirectoryPath = System.getProperty("user.home")
        + "/Documents/credentials";
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
   * @param googleCredentialsPath the path to the credentials file
   */
  public void setGoogleCredentialsFolder(final String googleCredentialsPath) {
    this.googleCredentialsFolder = googleCredentialsPath;
  }

  /**
   * get the credentials file.
   * @return the credentials file
   */
  public InputStream getGoogleClientSecretInputStream() {
    return googleClientSecretInputStream;
  }

  /**
   * set the credentials file.
   * @param newgoogleClientSecretInputStream credential file
   */
  public void setGoogleClientSecretInputStream(
      final InputStream newgoogleClientSecretInputStream) {
    this.googleClientSecretInputStream = newgoogleClientSecretInputStream;
  }

  /**
   * get the GoogleApp name.
   * @return a string
   */
  public String getGoogleApplicationName() {
    return googleApplicationName;
  }

  /**
   * set the GoogleApp name.
   * @param newGoogleApplicationName a string
   */
  public void setGoogleApplicationName(final String newGoogleApplicationName) {
    this.googleApplicationName = newGoogleApplicationName;
  }

  /**
   * get the tokensDirectory.
   * @return a string
   */
  public String getGoogleTokensDirectoryPath() {
    return googleTokensDirectoryPath;
  }

  /**
   * set the tokens directory.
   * @param newGoogleTokensDirectoryPath a string
   */
  public void setGoogleTokensDirectoryPath(
      final String newGoogleTokensDirectoryPath) {
    this.googleTokensDirectoryPath = newGoogleTokensDirectoryPath;
  }

  /**
   * get authCallBack Url.
   * @return a string
   */
  public String getGoogleAuthCallBack() {
    return googleAuthCallBack;
  }

  /**
   * set authCallBack url.
   * @param newGoogleAuthCallBack url authorized for callback
   */
  public void setGoogleAuthCallBack(final String newGoogleAuthCallBack) {
    this.googleAuthCallBack = newGoogleAuthCallBack;
  }

  /**
   * get microsoft Appid.
   * @return a string
   */
  public String getMicrosoftAppId() {
    return microsoftAppId;
  }

  /**
   * sets the microsoft AppId.
   * @param newMicrosoftAppId in auth.properties
   */
  public void setMicrosoftAppId(final String newMicrosoftAppId) {
    this.microsoftAppId = newMicrosoftAppId;
  }

  /**
   * get the appPassword in properties.
   * @return a string
   */
  public String getMicrosoftAppPassword() {
    return microsoftAppPassword;
  }

  /**
   * sets the microsoft appPassword.
   * @param newMicrosoftAppPassword the string
   */
  public void setMicrosoftAppPassword(final String newMicrosoftAppPassword) {
    this.microsoftAppPassword = newMicrosoftAppPassword;
  }

  /**
   * get the microsoft url authorized to redirect.
   * @return a string
   */
  public String getMicrosoftRedirectUrl() {
    return microsoftRedirectUrl;
  }

  /**
   * sets the microsoft url authorized to redirect.
   * @param newmicrosoftRedirectUrl a string
   */
  public void setMicrosoftRedirectUrl(final String newmicrosoftRedirectUrl) {
    this.microsoftRedirectUrl = newmicrosoftRedirectUrl;
  }

  /**
   * gets the domain for microsoft App.
   * @return a string
   */
  public String getMicrosoftAuthority() {
    return microsoftAuthority;
  }

  /**
   * sests the domain for microsoft App.
   * @param newMicrosoftAuthority a string
   */
  public void setMicrosoftAuthority(final String newMicrosoftAuthority) {
    this.microsoftAuthority = newMicrosoftAuthority;
  }

  /**
   * gets the end of authorized Url added in webApp platform.
   * @return a string
   */
  public String getMicrosoftAuthorizeUrl() {
    return microsoftAuthorizeUrl;
  }

  /**
   * sets the end of authorized Url added in webApp platform.
   * @param newMicrosoftAuthorizeUrl a string
   */
  public void setMicrosoftAuthorizeUrl(final String newMicrosoftAuthorizeUrl) {
    this.microsoftAuthorizeUrl = newMicrosoftAuthorizeUrl;
  }

}
