package fr.ynov.dap;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Manage all configs of application.
 * @author thibault
 *
 */
@Configuration
public class Config {
    /**
     * Default name of credentials file.
     */
    private static final String CREDENTIALS_FILE_NAME = "credentials.json";
    /**
     * Default name of auth.properties that contains config microsoft.
     */
    private static final String AUTH_PROPERTIES_NAME = "auth.properties";
    /**
     * Default application name.
     */
    private static final String APPLICATION_NAME = "JeanHeude";
    /**
     * Default root dir.
     */
    private static final String ROOT_DIR = Paths.get(System.getProperty("user.home"), "dap").toString();
    /**
     * Name of credentials file.
     */
    @Value("${config.credentialsFileName:null}")
    private String credentialsFileName;
    /**
     * Name of auth.properties that contains tokens.
     */
    @Value("${config.authPropertiesName:null}")
    private String authPropertiesName;
    /**
     * My application name.
     */
    @Value("${config.applicationName:null}")
    private String applicationName;
    /**
     * Root dir of conf.
     */
    @Value("${config.configDir:null}")
    private String rootDir;

    /**
     * Constructor of class Config.
     * Set defaults config.
     */
    public Config() {
        this.credentialsFileName    = CREDENTIALS_FILE_NAME;
        this.authPropertiesName     = AUTH_PROPERTIES_NAME;
        this.applicationName        = APPLICATION_NAME;
        this.rootDir                = ROOT_DIR;
    }

    /**
     * @return the credentialsFilePath
     */
    public String getCredentialsFilePath() {
        String name = credentialsFileName;
        if (credentialsFileName.equals("null")) {
            name = CREDENTIALS_FILE_NAME;
        }
        return Paths.get(getRootDir(), name).toString();
    }

    /**
     * @param path the credentialsFileName to set
     * @return this
     */
    public Config setCredentialsFileName(final String path) {
        this.credentialsFileName = path;
        return this;
    }

    /**
     * @return the authPropertiesPath
     */
    public String getAuthPropertiesPath() {
        String name = authPropertiesName;
        if (authPropertiesName.equals("null")) {
            name = AUTH_PROPERTIES_NAME;
        }
        return Paths.get(getRootDir(), name).toString();
    }

    /**
     * @param path the authPropertiesName to set
     * @return this
     */
    public Config setAuthPropertiesName(final String path) {
        this.authPropertiesName = path;
        return this;
    }

    /**
     * @return the applicationName
     */
    public String getApplicationName() {
        String name = applicationName;
        if (applicationName.equals("null")) {
            name = APPLICATION_NAME;
        }
        return name;
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
    public String getOAuth2CallbackUrl() {
        return "/oAuth2Callback";
    }

    /**
     * @return the rootDir
     */
    protected String getRootDir() {
        String configDir = rootDir;
        if (rootDir.equals("null")) {
            configDir = ROOT_DIR;
        }
        return configDir;
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
