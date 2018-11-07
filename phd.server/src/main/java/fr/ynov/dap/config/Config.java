package fr.ynov.dap.config;

/**
 * .
 * @author Dom.
 *
 */
public class Config {
    /**.
     * CREDENTIALS_FILE_PATH  is a variable containing the file path of the credentials
     */
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    /**.
     * TOKENS_DIRECTORY_PATH  is a variable containing the directory path of the tokens
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**.
     * APPLICATION_NAME  is a variable containing the name of the application
     */
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";

    /**
     * .
     */
    private String applicationName;
    /**
     * .
     */
    private String credentialsFilePath;
    /**
     * .
     */
    private String tokensDirectoryPath;

    /**
     * .
     */
    public Config() {
        this.applicationName = APPLICATION_NAME;
        this.credentialsFilePath = CREDENTIALS_FILE_PATH;
        this.tokensDirectoryPath = TOKENS_DIRECTORY_PATH;
    }

    /**
     * @param applicationN The name of the application
     * @param credentialsFileP The file path of credentials
     * @param tokensDirectoryP The directory of the tokens
     */
    public Config(final String applicationN, final String credentialsFileP, final String tokensDirectoryP) {
        this.applicationName = applicationN;
        this.credentialsFilePath = credentialsFileP;
        this.tokensDirectoryPath = tokensDirectoryP;
    }

    /**
     * @return the applicationName
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * @param applicationN the applicationName to set
     */
    public void setApplicationName(final String applicationN) {
        this.applicationName = applicationN;
    }

    /**
     * @return the credentialsFilePath
     */
    public String getCredentialsFilePath() {
        return credentialsFilePath;
    }

    /**
     * @param credentialsFileP the credentialsFilePath to set
     */
    public void setCredentialsFilePath(final String credentialsFileP) {
        this.credentialsFilePath = credentialsFileP;
    }

    /**
     * @return the tokensDirectoryPath
     */
    public String getTokensDirectoryPath() {
        return tokensDirectoryPath;
    }

    /**
     * @param tokensDirectoryP the tokensDirectoryP to set
     */
    public void setTokensDirectoryPath(final String tokensDirectoryP) {
        this.tokensDirectoryPath = tokensDirectoryP;
    }

    /**
     * @return .
     */
    public String getoAuth2CallbackUrl() {
        return "/oAuth2Callback";
    }

}
