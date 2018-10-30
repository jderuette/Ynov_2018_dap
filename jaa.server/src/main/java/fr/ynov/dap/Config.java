package fr.ynov.dap;

import java.io.File;

/**
 * Config class. Used by GoogleService.
 * @author adrij
 *
 */
public class Config {

    /**
     * Default token directory path that contains the StoredCredential file used by GoogleService.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    /**
     * Default credential json file path. Used by GoogleService.
     */
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    /**
     * Default application name.
     */
    private static final String APPLICATION_NAME = "dap";

    /**
     * default root directory.
     */
    private static final String DEFAULT_ROOT_DIR = System.getProperty("user.home") + File.separator + "dap";

    /**
     * application name.
     */
    private String applicationName = APPLICATION_NAME;
    /**
     * token directory path.
     */
    private String credentialFolder = TOKENS_DIRECTORY_PATH;
    /**
     * credential file path.
     */
    private String credentialsFilePath = CREDENTIALS_FILE_PATH;
    private String defaultRootDir = DEFAULT_ROOT_DIR;

    /**
     * @return application name
     */
    public String getApplicationName() {
        return applicationName;
    }
    /**
     * @param appName set application name
     */
    public void setApplicationName(final String appName) {
        this.applicationName = appName;
    }

    /**
     * @return credential directory.
     */
    public String getCredentialFolder() {
        return credentialFolder;
    }

    /**
     * @param directory set credential directory.
     */
    public void setCredentialFolder(final String directory) {
        this.credentialFolder = directory;
    }

    /**
     * @return credential file path.
     */
    public String getCredentialsFilePath() {
        return credentialsFilePath;
    }

    /**
     * @param path set credential file path.
     */
    public void setCredentialsFilePath(final String path) {
        this.credentialsFilePath = path;
    }

    /**
     * Constructor of config with custom credential folder name.
     * @param  credentialDirectory folder name.
     */
    public Config(final String credentialDirectory) {
        this.credentialFolder = credentialDirectory;
    }

    /**
     * Default constructor.
     */
    public Config() {
    }
    /**
     * Get the Authentication OAuth2 Callback url. Used to receive the token send by the google api.
     * That url have to be configured on the google account api of this projet.
     * @return url
     */
    public String getoAuth2CallbackUrl() {
        return "/oAuth2Callback";
    }

}
