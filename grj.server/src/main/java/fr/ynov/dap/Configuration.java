package fr.ynov.dap;

/**
 * Configuration
 */
public class Configuration {


    private String credentialFolder = "/google/credentials.json";
    private String clientSecretDir  = "tokens";
    private String applicationName  = "Ynov DaP";
    private String callbackUrl      = "http://localhost:8080/oAuth2Callback";

    /**
     * Default constructor
     */
    public Configuration() {
    }

    /**
     * Constructor with credential folder specified
     *
     * @param credentialFolder string
     */
    public Configuration(final String credentialFolder) {
        this.credentialFolder = credentialFolder;
    }

    /**
     * Getters / Setters
     **/

    public String getCredentialFolder() {
        return credentialFolder;
    }

    public void setCredentialFolder(String credentialFolder) {
        this.credentialFolder = credentialFolder;
    }

    public String getClientSecretDir() {
        return clientSecretDir;
    }

    public void setClientSecretDir(String clientSecretDir) {
        this.clientSecretDir = clientSecretDir;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
