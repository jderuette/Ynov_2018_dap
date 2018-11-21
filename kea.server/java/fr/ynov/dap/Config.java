package fr.ynov.dap;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import org.springframework.stereotype.Component;
/**
 * Contains all configuration needed to call the Google API.
 * @author Antoine
 *
 */

@Component
public class Config {
  private String credentialsFolder;
  private InputStream clientSecretDir;
  private String applicationName;
  private String tokensDirectoryPath;
  private NetHttpTransport httpTransport;
  private String openAuth2CallbackUrl;

  /**
   * Instantiate the NetHttpTransport specifically for Google.
   * @throws IOException nothing special
   * @throws GeneralSecurityException nothing special
   */
  public Config() throws IOException, GeneralSecurityException {
    credentialsFolder = "/credentials.json";
    clientSecretDir = GmailService.class.getResourceAsStream(credentialsFolder);
    httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    applicationName = "HoC DaP";
    tokensDirectoryPath  = "tokens";
    openAuth2CallbackUrl = "/oAuth2Callback";
  }

  /**
   * get the URL you want to redirect when authentication is done.
   * @return relative url after domain name
   */
  public String getoAuth2CallbackUrl() {
    return openAuth2CallbackUrl;
  }

  /**
   * set the URL you want to redirect when authentication is done.
   * @param openAuth2CallbackUrl url de redirection
   */
  public void setoAuth2CallbackUrl(String openAuth2CallbackUrl) {
    this.openAuth2CallbackUrl = openAuth2CallbackUrl;
  }

  /**
   * set the httpTransport.
   * @param httpTransport needed to communicate
   */
  public void setHttpTransport(NetHttpTransport httpTransport) {
    this.httpTransport = httpTransport;
  }

  /**
   * set the directory where the credentials.json is stored.
   * @param credentialsFolder folder that contains google credentials
   */
  public void setCredentialsFolder(String credentialsFolder) {
    this.credentialsFolder = credentialsFolder;
  }

  /**
   * The InputStream is the superclass of all classes representing
   * an input stream of bytes.
   * @param clientSecretDir directory of credentials
   */
  public void setClientSecretDir(InputStream clientSecretDir) {
    this.clientSecretDir = clientSecretDir;
  }

  /**
   * sets the application name.
   * @param applicationName of your application
   */
  public void setApplicationName(String applicationName) {
    this.applicationName = applicationName;
  }

  /**
   * Sets the directory path to the stored credentials.
   * @param tokensDirectoryPath a string that contains the path
   */
  public void setTokensDirectoryPath(String tokensDirectoryPath) {
    this.tokensDirectoryPath = tokensDirectoryPath;
  }

  /**
   * Gets the directory path to the stored credentials.
   * @return a string that contains the relative path in the project
   */
  public String getCredentialsFolder() {
    return credentialsFolder;
  }

  /**
   * The InputStream is the superclass of all classes representing
   * an input stream of bytes.
   * @return the inputStream
   */
  public InputStream getClientSecretDir() {
    return clientSecretDir;
  }

  /**
   * Gets the application name.
   * @return  String the application name
   */
  public String getApplicationName() {
    return applicationName;
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
    return tokensDirectoryPath;
  }
}
