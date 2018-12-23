package fr.ynov.dap.dap.config;

/**
 * @author Florian BRANCHEREAU
 *
 */
//TODO brf by Djer |Design Patern| Rendre configurable les emplacements des fichiers ?
public class Config {

    /**.
     * APPLICATION_NAME
     */
    private static final String APPLICATION_NAME = "YNOV_GOOGLE_API_JAVA";
    /**.
     * TOKENS_DIRECTORY_PATH
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    /**.
     * CREDENTIALS_FILE_PATH
     */
    private static final String CREDENTIALS_FILE_PATH = System.getProperty("user.home")
            + System.getProperty("file.separator") + "Java_Serveur" + System.getProperty("file.separator")
            + "credentials.json";
    /**.
     * CREDENTIALS_FILE_PATH
     */
    private static final String AUTH_PROPERTIES = System.getProperty("user.home")
            + System.getProperty("file.separator") + "Java_Serveur" + System.getProperty("file.separator")
            + "auth.properties";

    /**.
     * applicationName
     */
    private String applicationName;
    /**.
     * tokensDirectoryPath
     */
    private String tokensDirectoryPath;
    /**.
     * credentialsFilePath
     */
    private String credentialsFilePath;
    /**.
     * authproperties
     */
    private String authProperties;

    /**.
     * constructeur de la classe
     */
    public Config() {
        applicationName = APPLICATION_NAME;
        tokensDirectoryPath = TOKENS_DIRECTORY_PATH;
        credentialsFilePath = CREDENTIALS_FILE_PATH;
        authProperties = AUTH_PROPERTIES;
    }

    /**
     * @return applicationName
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * @param theApplicationName fonction
     */
    public void setApplicationName(final String theApplicationName) {
        applicationName = theApplicationName;
    }

    /**
     * @return tokensDirectoryPath
     */
    public String getTokensDirectoryPath() {
        return tokensDirectoryPath;
    }

    /**
     * @param theTokensDirectoryPath recupere le token
     */
    public void setTokensDirectoryPath(final String theTokensDirectoryPath) {
        tokensDirectoryPath = theTokensDirectoryPath;
    }

    /**
     * @return credentialsFilePath
     */
    public String getCredentialsFilePath() {
        return credentialsFilePath;
    }

    /**
     * @param theCredentialsPath fonction
     */
    public void setCredentialsFilePath(final String theCredentialsPath) {
        credentialsFilePath = theCredentialsPath;
    }

    /**
     * @return the authProperties
     */
    public String getAuthProperties() {
        return authProperties;
    }

    /**
     * @param authProperties the authProperties to set
     */
    public void setAuthProperties(String authProperties) {
        this.authProperties = authProperties;
    }

    /**
     * @return url /oAuth2Callback
     */
    public String getoAuth2CallbackUrl() {
        return "/oAuth2Callback";
    }
}
