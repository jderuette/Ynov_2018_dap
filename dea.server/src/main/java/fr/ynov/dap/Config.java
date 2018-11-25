
package fr.ynov.dap;


/**
 * Classe de configuration
 * 
 * @author antod
 *
 */
public class Config
{
  /**
   * Nom du dossier où se trouvent les credentials
   */
  private final String CREDENTIALS_FOLDER = "credentials";
  /**
   * Nom du chemin des infos du client
   */
  private final String CLIENT_SECRET_DIR = "tokens";
  /**
   * Nom de l'application
   */
  private final String APPLICATION_NAME = "HoC DaP";
  /**
   * Chemin vers le dossier source de l'application
   */
  private final String APPLICATION_FOLDER = System.getProperty("user.home") + System.getProperty("file.separator");

  /**
   * Attribut pour le nom de l'application
   */
  private String applicationName;
  /**
   * Attribut pour le chemin des credential
   */
  private String credentialFolder;
  /**
   * Attribut pour le chemin des infos du client
   */
  private String clientSecretFile;
  /**
   * Attribut pour le chemin de l'application
   */
  private String applicationFolder;

  /**
   * Constructeur de la classe de configuration.
   * 
   */
  public Config()
  {
    applicationFolder = APPLICATION_FOLDER;
    applicationName = APPLICATION_NAME;
    credentialFolder = CREDENTIALS_FOLDER;
    clientSecretFile = CLIENT_SECRET_DIR;
  }

  /**
   * Renvoie le chemin vers l'adresse de callback
   * 
   * @return
   */
  public String getoAuth2CallbackUrl()
  {
    return "/oAuth2Callback";
  }

  /**
   * Récupère le nom de l'application
   * 
   * @return
   */
  public String getApplicationName()
  {
    return applicationName;
  }

  /**
   * Change le nom de l'application
   * 
   * @param applicationName
   */
  public void setApplicationName(String applicationName)
  {
    this.applicationName = applicationName;
  }

  /**
   * Récupère le chemin du fichier où est rangé le fichier credential.json
   * 
   * @return
   */
  public String getCredentialFolder()
  {
    return applicationFolder + System.getProperty("file.separator") + credentialFolder;
  }

  /**
   * Change le chemin du fichier dans lequel est rangé le fichier credential.json
   * 
   * @param credentialFolder
   */
  public void setCredentialFolder(String credentialFolder)
  {
    this.credentialFolder = credentialFolder;
  }

  /**
   * Récupère le chemin du répertoire tokens
   * 
   * @return
   */
  public String getClientSecretFile()
  {
    return applicationFolder + System.getProperty("file.separator") + clientSecretFile;
  }

  /**
   * Change le chemin où est rangé le répertoire tokens
   * 
   * @param clientSecretFile
   */
  public void setClientSecretFile(String clientSecretFile)
  {
    this.clientSecretFile = clientSecretFile;
  }

  /**
   * Change le chemin de la racine du répertoire du projet
   * 
   * @param path
   */
  public void setRacineFolder(String path)
  {
    this.applicationFolder = path;
  }

  /**
   * Récupère le chemin de la racine du répertoire du projet
   * 
   * @return
   */
  public String getRacineFolder()
  {
    return applicationFolder;
  }
}
