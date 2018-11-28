package fr.ynov.dap.dap;

import java.io.File;

/**
 * Config of the application.
 */
public class Config {
    /**
     * credentials folder for zero config.
     */
    private static final String CREDENTIALS_FOLDER = "/google/credentials.json";
    /**
     * Folder for secret client for zero config.
     */
    private static final String CLIENT_SECRET_DIR = "tokens";
    /**
     * The application name.
     */
    private static final String APPLICATION_NAME = "Ynov DaP";

    /**
     * public application name.
     */
    private String applicationName;
    /**
     * Public config for credential folder.
     */
    private String credentialFolder;
    /**
     * Public config for secret client folder.
     */
    private String clientSecretFile;

    /**
     * Initialize public variable.
     */
    public Config() {
        applicationName = APPLICATION_NAME;
        credentialFolder = CREDENTIALS_FOLDER;
        clientSecretFile = CLIENT_SECRET_DIR;
    }

    /**
     * set the path of credential Folder for personal config.
     *
     * @param credentialFolder : path to credential folder
     */
    public final void setCredentialsFolder(final String credentialFolder) {
        if (File.separatorChar != '/') {
            this.credentialFolder = credentialFolder.replace('/', File.separatorChar);
        } else {
            this.credentialFolder = credentialFolder.replace('\\', File.separatorChar);
        }
    }

    /**
     * set the path file to secret client file for personal config.
     *
     * @param clientSecretFile : path to secret client file
     */
    public final void setClientSecretFile(final String clientSecretFile) {
        // File.separator te renvoie le séparator du systeme ou s'éxécute Java (le server ici).
        if (File.separatorChar == '\\') {
            this.clientSecretFile = clientSecretFile.replace('/', File.separatorChar);
        } else {
            this.clientSecretFile = clientSecretFile.replace('\\', File.separatorChar);
        }
    }

    /**
     * URl for Authenticate Call back.
     *
     * @return String : URL too callback
     */
    final String getoAuth2CallbackUrl() {
        return "/oAuth2Callback";
    }

    /**
     * Get name's application
     *
     * @return String : name's application
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * Set name's application
     *
     * @params applicationName : name's application
     */
    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    /**
     * Get the credential folder
     *
     * @return String : path of credential folder
     */
    public String getCredentialFolder() {
        return credentialFolder;
    }

    /**
     * Set the path of credential folder
     *
     * @params credentialFolder : new path of crendential folder
     */
    public void setCredentialFolder(String credentialFolder) {
        this.credentialFolder = credentialFolder;
    }

    /**
     * Get the path of the client secret file
     *
     * @return String : path of the secret client file
     */
    public String getClientSecretFile() {
        return clientSecretFile;
    }
}
