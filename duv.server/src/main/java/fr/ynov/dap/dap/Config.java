package fr.ynov.dap.dap;

/**
 *
 * @author David_tepoche
 *
 */
//TODO duv by Djer Généralement on évite les "chaine magiques", et on les places dans des constantes
public class Config {

    /**
     *
     */
    public Config() {

    }

    /**
     * store the oAuth2CallbackUrl.
     */
    private String oAuth2CallbackUrl = "/oAuth2Callback";

    /**
    *
    */
    private String clientSecretFile = "credentials.json";

    /**
    *
    */
    private String applicationName = "YnovDaP";

    /**
    *
    */
    private String credentialsFolder = "token";

    /**
     * @return the oAuth2CallbackUrl
     */
    public String getoAuth2CallbackUrl() {
        return oAuth2CallbackUrl;
    }

    /**
     * get the directory of the dataStore default = the home directory of the user.
     */
    private String dataStoreDirectory = System.getProperty("user.home");

    /**
     *
     * @param dataDirectory the path of the new directory to store the credential
     */
    public Config(final String dataDirectory) {
        this.dataStoreDirectory = dataDirectory;
    }

    /**
     * @return the dataStoreDirectory
     */
    public String getDataStoreDirectory() {
        return this.dataStoreDirectory;
    }

    /**
     * @param dataDirectory the dataStoreDirectory to set
     */
    public void setDataStoreDirectory(final String dataDirectory) {
        this.dataStoreDirectory = dataDirectory;
    }

    /**
     * @return the credentialsFolder
     */
    public String getCredentialsFolder() {
        return credentialsFolder;
    }

    /**
     * @return the clientSecretDir
     */
    public String getClientSecretFile() {
        return clientSecretFile;
    }

    /**
     * @return the applicationName
     */
    public String getApplicationName() {
        return applicationName;
    }

}
