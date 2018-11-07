package fr.ynov.dap.dap;
/**
 * Classe de configuration.
 * @author alex
 */
public class Config {
  /**
   * Chemin de récupération des credentials pour la connexion à google.
   */
  private static final String CREDENTIALSFILEPATH = "/credentials.json";
  /**
   * Chemin de stockage des tokens de connexion à google incluant les droits
   * d'accès aux différentes API google.
   */
  private static final String TOKENSDIRECTIONPATH = "tokens";
  /**
   * Nom de l'application.
   */
  private static final String APPLICATIONNAME     = "Ynov dap";
  /**
   * url de redirection oauth2.
   */
  private static final String AUTH2CALLBACKURL    = "/oAuth2Callback";
  /**
   * Permet de récupérer le nom de l'application.
   * @return String
   */
  public String getApplicationName() {
    return APPLICATIONNAME;
  }
  /**
   * Permet de récupérer le chemin des crédentials.
   * @return String
   */
  public String getCredentialFilePath() {
    return CREDENTIALSFILEPATH;
  }
  /**
   * Permet de récupérer le chemin des tokens.
   * @return String
   */
  public String getClientSecretFile() {
    return TOKENSDIRECTIONPATH;
  }
  /**
   * Renvois l'url de callBack de oAuth2.
   * @return String
   */
  public String getoAuth2CallbackUrl() {
    return AUTH2CALLBACKURL;
  }
}
