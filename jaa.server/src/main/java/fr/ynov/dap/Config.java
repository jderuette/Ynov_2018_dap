package fr.ynov.dap;

import java.io.File;

/**
 * Config class.
 *
 */
public class Config {
    /**
     * Microsoft auth.properties default name.
     */
    private static final String MICROSOFT_AUTH_PROPERTIES_FILE_NAME = "auth.properties";

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
    private String credentialFolder = DEFAULT_ROOT_DIR + File.separator + TOKENS_DIRECTORY_PATH;
    /**
     * credential file path.
     */
    private String credentialsFilePath = DEFAULT_ROOT_DIR + File.separator + CREDENTIALS_FILE_PATH;
    /**
     * default root directory.
     */
    private String defaultRootDir = DEFAULT_ROOT_DIR;
    /**
     * Default path of the Microsoft auth.properties file.
     */
    private String microsoftAuthPropertiesDefaultPath = DEFAULT_ROOT_DIR +
            File.separator + MICROSOFT_AUTH_PROPERTIES_FILE_NAME;

    /**
     * @return the microsoftAuthPropertiesDefaultPath
     */
    public String getMicrosoftAuthPropertiesDefaultPath() {
        return microsoftAuthPropertiesDefaultPath;
    }
    /**
     * @param defaultPath the microsoftAuthPropertiesDefaultPath to set
     */
    public void setMicrosoftAuthPropertiesDefaultPath(final String defaultPath) {
        this.microsoftAuthPropertiesDefaultPath = defaultPath;
    }
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
