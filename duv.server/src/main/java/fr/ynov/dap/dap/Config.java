package fr.ynov.dap.dap;

/**
 *
 * @author David_tepoche
 *
 */
public class Config {

    /**
     * name of the application.
     */
    private static final String APPLICATION_NAME = "YnovDaP";

    /**
     * folder's name of the credentials.
     */
    private static final String CREDENTIALS_FOLDER_NAME = "token";

    /**
     * file's name of the client' secret.
     */
    private static final String SECRETS_CLIENT_FILE_NAME = "credentials.json";

    /**
     * root of the redirect url.
     */
    private static final String ROOT_URL_REDIRECT = "/oAuth2Callback";

    /**
     * store the oAuth2CallbackUrl.
     */
    private final String oAuth2CallbackUrl = ROOT_URL_REDIRECT;

    /**
    *
    */
    private final String clientSecretFile = SECRETS_CLIENT_FILE_NAME;

    /**
    *
    */
    private final String applicationName = APPLICATION_NAME;

    /**
    *
    */
    private final String credentialsFolder = CREDENTIALS_FOLDER_NAME;

    /**
     * get the directory of the dataStore default = the home directory of the user.
     */
    private String dataStoreDirectory = System.getProperty("user.home");

    /**
     *
     */
    public Config() {

    }

    /**
     *
     * @param dataDirectory the path of the new directory to store the credential
     */
    public Config(final String dataDirectory) {
        this.dataStoreDirectory = dataDirectory;
    }

    /**
     * @return the applicationName
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * @return the clientSecretDir
     */
    public String getClientSecretFile() {
        return clientSecretFile;
    }

    /**
     * @return the credentialsFolder
     */
    public String getCredentialsFolder() {
        return credentialsFolder;
    }

    /**
     * @return the dataStoreDirectory
     */
    public String getDataStoreDirectory() {
        return this.dataStoreDirectory;
    }

    /**
     * @return the oAuth2CallbackUrl
     */
    public String getoAuth2CallbackUrl() {
        return oAuth2CallbackUrl;
    }

    /**
     * @param dataDirectory the dataStoreDirectory to set
     */
    public void setDataStoreDirectory(final String dataDirectory) {
        this.dataStoreDirectory = dataDirectory;
    }

}
