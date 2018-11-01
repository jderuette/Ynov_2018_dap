package fr.ynov.dap.configurations;

public class Config {
    /**
     * The application name
     */
    private static final String APPLICATION_NAME = "HoC DaP";
    /**
     * The token of the directory path
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    /**
     * The path where the credentials are stored.
     */
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * The private attribute for the application name
     */
    private String applicationName;
    /**
     * The private attribute for the token of the directory path
     */
    private String tokensDirectoryPath;
    /**
     * The private attribute for the path where the credentials are stored.
     */
    private String credentialsFilePath;

    /**
     * The default constructor of the Config class.
     */
    public Config() {
        this.applicationName = APPLICATION_NAME;
        this.tokensDirectoryPath = TOKENS_DIRECTORY_PATH;
        this.credentialsFilePath = CREDENTIALS_FILE_PATH;
    }

    /**
     * It sets the application name.
     * @param name The application name.
     */
    public final void setApplicationName(String name) {
        this.applicationName = name;
    }

    /**
     * It sets the tokens' directory path.
     * @param path tokens' directory path.
     */
    public final void setTokensDirectoryPath(String path) {
        this.tokensDirectoryPath = path;
    }

    /**
     * It sets the credentials file path.
     * @param filePath The credentials' file path.
     */
    public final void setCredentialsFilePath(String filePath) {
        this.credentialsFilePath = filePath;
    }

    /**
     * This getter returns the application name.
     * @return String
     */
    public final String getApplicationName() {
        return this.applicationName;
    }

    /**
     * This getter returns the token directory path name.
     * @return String
     */
    public final String getTokensDirectoryPath() {
        return this.tokensDirectoryPath;
    }

    /**
     * This getter returns the credentials' file path.
     * @return String
     */
    public final String getCredentialsFilePath() {
        return this.credentialsFilePath;
    }
}
