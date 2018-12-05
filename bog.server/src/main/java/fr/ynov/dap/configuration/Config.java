package fr.ynov.dap.configuration;

/**
 * @author Mon_PC
 * Class Config
 *
 */
//TODO bog by Djer |Spring| Externalisation de la configuration ?
public class Config {
    /**.
     * Nom de l'application de base
     */
    private static final String APPLICATION_NAME = "Ynov app Java";
    /**.
     * Répertoire de base du token
     */
    private static final String TOKENS_DIRECTORY_PATH = System.getProperty("user.home")
            + System.getProperty("file.separator") + "dap" + System.getProperty("file.separator") + "tokens";
    /**.
     * Répertoire de base qui regroupe les credentials
     */
    private static final String CREDENTIALS_FILE_PATH = System.getProperty("user.home")
            + System.getProperty("file.separator") + "dap" + System.getProperty("file.separator")
            + "web_credentials.json";
    /**.
     * Nom de l'application (peut être modifié à partir des accesseurs)
     */
    private String applicationName;
    /**.
     * Répertoire du token (peut être modifié à partir des accesseurs)
     */
    private String tokensDirectoryPath;
    /**.
     * Répertoire des credentials (peut être modifié à partir des accesseurs)
     */
    private String credentialsFilePath;

    /**.
     * Constructeur class Config
     */
    public Config() {
        applicationName = APPLICATION_NAME;
        tokensDirectoryPath = TOKENS_DIRECTORY_PATH;
        credentialsFilePath = CREDENTIALS_FILE_PATH;
    }

    /**
     * @return Application Name
     * Get the application name
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * @param newApplicationName
     * Set the name of the application
     */
    public void setApplicationName(final String newApplicationName) {
        applicationName = newApplicationName;
    }

    /**
     * @return tokens directory path
     * get the name of the directory path
     */
    public String getTokensDirectoryPath() {
        return tokensDirectoryPath;
    }

    /**
     * @param newTokensDirectoryPath
     * Set the name of the token directory path
     */
    public void setTokensDirectoryPath(final String newTokensDirectoryPath) {
        tokensDirectoryPath = newTokensDirectoryPath;
    }

    /**
     * @return Credentials file path
     * get the name credentials file path
     */
    public String getCredentialsFilePath() {
        return credentialsFilePath;
    }

    /**
     * @param newCredentialsFilePath
     * set the name credentials file path
     */
    public void setCredentialsFilePath(final String newCredentialsFilePath) {
        credentialsFilePath = newCredentialsFilePath;
    }

    /**
     * @return "/oAuth2Callback"
     * redirection path
     */
    public String getoAuth2CallbackUrl() {
        return "/oAuth2Callback";
    }
}
