package fr.ynov.dap;

import fr.ynov.dap.utils.StrUtils;

/**
 * This class allow developer to configure this application.
 * @author Kévin Sibué
 *
 */
//TODO sik by Djer Dans la doc tu indique que c'est configurable, mais pas vraiment, il faudrait recompiler/builder.
// Pour que ca soit parémètrable par un "admin system" il faut que tu lui laisse la possibilité de le modifier.
// soit via paramètre de ligne de commande (pas idéal sur un "serveur")
// Tu peux uasis jetter un oeil du coté de "spring properties" : https://www.baeldung.com/properties-with-spring
public class Config {
    //TODO sik by Djer En general on évite les "chaine magique". On préfère créer des constantes pour "documenter".

    /**
     * Store OAuth2 callback url.
     */
    private String oAuth2CallbackUrl = "/oAuth2Callback";

    /**
     * Store credentials folder.
     */
    private String credentialsFolder = "dap";

    /**
     * Store filename of credentials.
     */
    private String clientSecretDir = "/web_credentials.json";

    /**
     * Store application name.
     */
    private String applicationName = "HoC DaP";

    /**
     * Store datastore directory path.
     */
    private String datastoreDirectory = System.getProperty("user.home");

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
