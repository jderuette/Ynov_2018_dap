package fr.ynov.dap;

import fr.ynov.dap.utils.StrUtils;

/**
 * This class allow developer to configure this application.
 * @author Kévin Sibué
 *
 */
public class Config {

    /**
     * Default config for oAuth2Callback.
     */
    private static final String OAUTH_2_CALLBACK_URL = "/oAuth2Callback";

    /**
     * Default config for credentialsFolder.
     */
    private static final String CREDENTIALS_FOLDER = "dap";

    /**
     * Default config for client secret dir.
     */
    private static final String CLIENT_SECRET_DIR = "/web_credentials.json";

    /**
     * Default config for application name.
     */
    private static final String APPLICATION_NAME = "HoC DaP";

    /**
     * Default config for datastore directory.
     */
    private static final String DATASTORE_DIRECTORY = System.getProperty("user.home");

    /**
     * Store OAuth2 callback url.
     */
    private String oAuth2CallbackUrl = OAUTH_2_CALLBACK_URL;

    /**
     * Store credentials folder.
     */
    private String credentialsFolder = CREDENTIALS_FOLDER;

    /**
     * Store filename of credentials.
     */
    private String clientSecretDir = CLIENT_SECRET_DIR;

    /**
     * Store application name.
     */
    private String applicationName = APPLICATION_NAME;

    /**
     * Store datastore directory path.
     */
    private String datastoreDirectory = DATASTORE_DIRECTORY;

    /**
     * Default constructor.
     */
    public Config() {

    }

    /**
     * Default constructor.
     * @param dataStoreDirectory Directory path to store credential file
     */
    public Config(final String dataStoreDirectory) {
        if (dataStoreDirectory != null) {
            String newPath = StrUtils.resolvePath(dataStoreDirectory);
            datastoreDirectory = newPath;
        }
    }

    /**
     * Return Datastore directory.
     * @return Datastore directory
     */
    public String getDatastoreDirectory() {
        return datastoreDirectory;
    }

    /**
     * Return application name.
     * @return Application name
     */
    public String getApplicationName() {
        return applicationName;
    }

    /**
     * Set new application name.
     * @param name Application's name
     */
    public void setApplicationName(final String name) {
        applicationName = name;
    }

    /**
     * Return credential folder.
     * @return Credential folder
     */
    public String getCredentialFolder() {
        return credentialsFolder;
    }

    /**
     * Set new credential folder.
     * @param folder Credential folder
     */
    public void setCredentialFolder(final String folder) {
        credentialsFolder = folder;
    }

    /**
     * Return client secret file.
     * @return Client secret file
     */
    public String getClientSecretFile() {
        return clientSecretDir;
    }

    /**
     * Set new client secret file.
     * @param clientSecretFile Client secret file
     */
    public void setClientSecretFile(final String clientSecretFile) {
        clientSecretDir = clientSecretFile;
    }

    /**
     * Return OAuth2 CallBack Url.
     * @return OAuth2 CallBack Url
     */
    public String getOAuth2CallbackUrl() {
        return oAuth2CallbackUrl;
    }

}
