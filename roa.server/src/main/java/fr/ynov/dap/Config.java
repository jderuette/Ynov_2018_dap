package fr.ynov.dap;
/**
 * Classe de configuration.
 * @author alex
 */
//TODO roa by Djer |Design Patern| Pour le princiep ZeroConf, crée plutot une "vrai classe" (sans "static de partout), avec des constante pour elsvaleur par defaut, et des attributs pour la modifications.
//TODO roa by Djer |Spring| Pour demander a Spring de "créer une config par dafaut", tu as 2 options, une méthode, anoté par @Bean qui créer (et configure) une instance de Config (dnas ton launcher par exemple). Annoter avec @Configuration ta classe config (ou n'importe quelle annotation "spring" qui créra un Singleton pour toi). L'option @Configuration est moins flexible
public abstract class Config {
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
  public static String getApplicationName() {
    return APPLICATIONNAME;
  }
  /**
   * Permet de récupérer le chemin des crédentials.
   * @return String
   */
  public static String getCredentialFilePath() {
    return CREDENTIALSFILEPATH;
  }
  /**
   * Permet de récupérer le chemin des tokens.
   * @return String
   */
  public static String getClientSecretFile() {
    return TOKENSDIRECTIONPATH;
  }
  /**
   * Renvois l'url de callBack de oAuth2.
   * @return String
   */
  public static String getoAuth2CallbackUrl() {
    return AUTH2CALLBACKURL;
  }
}
