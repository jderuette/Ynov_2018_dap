package fr.ynov.dap;

import org.apache.logging.log4j.LogManager;

/**
 * Config file.
 * @author MBILLEMAZ
 *
 */
public class Config {

    /**
     * Default folder used to stock credentials.json file.
     */
    private static final String CREDENTIALS_FOLDER = "credentials" + System.getProperty("file.separator");

    /**
     * Default folder used to stock tokens ?
     */
    private static final String CLIENT_SECRET_DIR = "tokens";

    /**
     * Default application name.
     */
    private static final String APPLICATION_NAME = "HoC DaP";

    /**
     * Default root folder for tokens and credentials.
     */
    private static final String ROOT_FOLDER = System.getProperty("user.home") + System.getProperty("file.separator")
            + "dap" + System.getProperty("file.separator");

    /**
     * Application name.
     */
    private String applicationName;

    /**
     * folder used to stock credentials.json file.
     */
    private String credentialsFolder;

    /**
     * folder used to stock tokens.
     */
    private String clientSecretDir;

    /**
     * root folder for tokens and credentials.
     */
    private String rootFolder;

    /**
     * Create config instance.
     * @param newRootFolder directory where are stocked tokens and credentials.json
     */
    public Config(final String newRootFolder) {
        super();
        this.applicationName = APPLICATION_NAME;
        this.credentialsFolder = CREDENTIALS_FOLDER;
        this.clientSecretDir = CLIENT_SECRET_DIR;
        this.rootFolder = newRootFolder;

    }

    /**
     * Create config instance with default values.
     */
    public Config() {
        super();
        this.applicationName = APPLICATION_NAME;
        this.credentialsFolder = CREDENTIALS_FOLDER;
        this.clientSecretDir = CLIENT_SECRET_DIR;
        this.rootFolder = ROOT_FOLDER;
        //TODO bim by Djer Pourquoi une "error" ?
        LogManager.getLogger().error(this.rootFolder);
    }

    /**
     * return application name.
     * @return application name
     */
    public final String getApplicationName() {
        return applicationName;
    }

    /**
     * update application name.
     * @param newApplicationName name
     */
    public final void setApplicationName(final String newApplicationName) {
        this.applicationName = newApplicationName;
    }

    /**
     * update credential folder.
     * @param newCredentialFolder folder
     */
    public final void setCredentialsFolder(final String newCredentialFolder) {
        this.credentialsFolder = newCredentialFolder;
    }

    /**
     * client dir.
     * @return dir
     */
    public final String getClientSecretDir() {
        return rootFolder + clientSecretDir;
    }

    /**
     * update client directory.
     * @param newClientSecretDirectory directory
     */
    public final void setClientSecretFile(final String newClientSecretDirectory) {
        this.clientSecretDir = newClientSecretDirectory;
    }

    /**
     * @return the credentialsFolder
     */
    public final String getCredentialsFolder() {
        return rootFolder + credentialsFolder;
    }

    /**
     * @return the rootFolder
     */
    public final String getRootFolder() {
        return rootFolder;
    }

    /**
     * @param newRootFolder the rootFolder to set
     */
    public final void setRootFolder(final String newRootFolder) {
        this.rootFolder = newRootFolder;
    }

}
