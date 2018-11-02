package fr.ynov.dap;

import java.nio.file.Paths;

/**
 * Manage all configs of application.
 * @author thibault
 *
 */
public class Config {
    /**
     * Default path of credentials file.
     */
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";
    /**
     * Default path of directory that contains tokens.
     */
    private static final String TOKEN_DIR_PATH = "tokens";
    /**
     * Default application name.
     */
    private static final String APPLICATION_NAME = "JeanHeude";
    /**
     * Default root dir.
     */
    private static final String ROOT_DIR = Paths.get(System.getProperty("user.home"), "dap").toString();
    /**
     * Path of credentials file.
     */
    private String credentialsFilePath;
    /**
     * Path of directory that contains tokens.
     */
    private String tokenDirPath;
    /**
     * My application name.
     */
    private String applicationName;
    /**
     * Root dir of conf.
     */
    private String rootDir;

    /**
     * Constructor of class Config.
     * Set defaults config.
     */
    public Config() {
        this.credentialsFilePath    = CREDENTIALS_FILE_PATH;
        this.tokenDirPath           = TOKEN_DIR_PATH;
        this.applicationName        = APPLICATION_NAME;
        this.rootDir                = ROOT_DIR;
    }

    /**
     * @return the credentialsFilePath
     */
    public String getCredentialsFilePath() {
        return Paths.get(rootDir, credentialsFilePath).toString();
    }

    /**
     * @param path the credentialsFilePath to set
     * @return this
     */
    public Config setCredentialsFilePath(final String path) {
        this.credentialsFilePath = path;
        return this;
    }

    /**
     * @return the tokenDirPath
     */
    public String getTokenDirPath() {
        return Paths.get(rootDir, tokenDirPath).toString();
    }

    /**
     * @param path the tokenDirPath to set
     * @return this
     */
    public Config setTokenDirPath(final String path) {
        this.tokenDirPath = path;
        return this;
    }

    /**
     * @return the applicationName
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * @param appName the applicationName to set
     * @return this
     */
    public Config setApplicationName(final String appName) {
        this.applicationName = appName;
        return this;
    }

    /**
     * @return the oAuth2 callback url
     */
    public String getoAuth2CallbackUrl() {
        return "/oAuth2Callback";
    }

    /**
     * @return the rootDir
     */
    protected String getRootDir() {
        return rootDir;
    }

    /**
     * @param path the rootDir path to set
     * @return this
     */
    protected Config setRootDir(final String path) {
        this.rootDir = path;
        return this;
    }
}
