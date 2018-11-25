package fr.ynov.dap;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import fr.ynov.dap.exception.ConfigurationException;
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
     * Default config for application name.
     */
    private static final String APPLICATION_NAME = "HoC DaP";

    /**
     * Default config for datastore directory.
     */
    private static final String DATASTORE_DIRECTORY = System.getProperty("user.home");

    private static final String GOOGLE_CREDENTIAL_PATH = System.getProperty("user.home")
            + System.getProperty("file.separator") + "credentials.json";

    private static final String MICROSOFT_CREDENTIAL_PATH = System.getProperty("user.home")
            + System.getProperty("file.separator") + "auth.properties";

    private static final String GLOBAL_CONFIG_PATH = System.getProperty("user.home")
            + System.getProperty("file.separator") + "dap_global_config.properties";

    /**
     * Store OAuth2 callback url.
     */
    private String oAuth2CallbackUrl = OAUTH_2_CALLBACK_URL;

    /**
     * Store credentials folder.
     */
    private String credentialsFolder = CREDENTIALS_FOLDER;

    /**
     * Store application name.
     */
    private String applicationName = APPLICATION_NAME;

    /**
     * Store datastore directory path.
     */
    private String datastoreDirectory = DATASTORE_DIRECTORY;

    private String googleCredentialsPath = GOOGLE_CREDENTIAL_PATH;

    private String microsoftCredentialsPath = MICROSOFT_CREDENTIAL_PATH;

    private String microsoftAppId;

    private String microsoftRedirectUrl;

    private String microsoftAppPassword;

    /**
     * Default constructor.
     * @throws IOException Exception
     */
    public Config() throws IOException {
        loadConfig();
    }

    /**
     * Default constructor.
     * @param dataStoreDirectory Directory path to store credential file
     * @throws IOException Exception
     */
    public Config(final String dataStoreDirectory) throws IOException {
        if (dataStoreDirectory != null) {
            String newPath = StrUtils.resolvePath(dataStoreDirectory);
            datastoreDirectory = newPath;
        }
        loadConfig();
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
     * Return OAuth2 CallBack Url.
     * @return OAuth2 CallBack Url
     */
    public String getOAuth2CallbackUrl() {
        return oAuth2CallbackUrl;
    }

    /**
     * @return the googleCredentialsPath
     */
    public String getGoogleCredentialsPath() {
        return googleCredentialsPath;
    }

    /**
     * @param val the googleCredentialsPath to set
     */
    public void setGoogleCredentialsPath(final String val) {
        this.googleCredentialsPath = val;
    }

    /**
     * @return the microsoftCredentialsPath
     */
    public String getMicrosoftCredentialsPath() {
        return microsoftCredentialsPath;
    }

    /**
     * @param val the microsoftCredentialsPath to set
     */
    public void setMicrosoftCredentialsPath(final String val) {
        this.microsoftCredentialsPath = val;
    }

    /**
     * @return the microsoftAppId
     */
    public String getMicrosoftAppId() {
        return microsoftAppId;
    }

    /**
     * @param val the microsoftAppId to set
     */
    public void setMicrosoftAppId(final String val) {
        this.microsoftAppId = val;
    }

    /**
     * @return the microsoftRedirectUrl
     */
    public String getMicrosoftRedirectUrl() {
        return microsoftRedirectUrl;
    }

    /**
     * @param val the microsoftRedirectUrl to set
     */
    public void setMicrosoftRedirectUrl(final String val) {
        this.microsoftRedirectUrl = val;
    }

    /**
     * @return the microsoftAppPassword
     */
    public String getMicrosoftAppPassword() {
        return microsoftAppPassword;
    }

    /**
     * @param val the microsoftAppPassword to set
     */
    public void setMicrosoftAppPassword(final String val) {
        this.microsoftAppPassword = val;
    }

    private void loadConfig() throws IOException {

        InputStreamReader isr = loadGlobalConfig();

        if (isr != null) {

            Properties authProps = new Properties();

            try {

                authProps.load(isr);

                oAuth2CallbackUrl = authProps.getProperty("dap.providers.google.callback_url");
                credentialsFolder = authProps.getProperty("dap.providers.google.credentials_folder_name");
                googleCredentialsPath = authProps.getProperty("dap.providers.google.api.credentials_path");

                microsoftAppId = authProps.getProperty("dap.providers.microsoft.app_id");
                microsoftAppPassword = authProps.getProperty("dap.providers.microsoft.app_password");
                microsoftRedirectUrl = authProps.getProperty("dap.providers.microsoft.redirect_url");

                applicationName = authProps.getProperty("dap.application_name");

            } finally {

                isr.close();

            }

        } else {

            loadMicrosoftConfig();

        }

    }

    /**
     * Load configuration.
     * @throws IOException Exception
     */
    protected void loadMicrosoftConfig() throws IOException {

        InputStreamReader file = new InputStreamReader(new FileInputStream(getMicrosoftCredentialsPath()),
                Charset.forName("UTF-8"));

        if (file.ready()) {

            Properties authProps = new Properties();

            try {

                authProps.load(file);

                microsoftAppId = authProps.getProperty("appId");
                microsoftAppPassword = authProps.getProperty("appPassword");
                microsoftRedirectUrl = authProps.getProperty("redirectUrl");

            } finally {

                file.close();

            }

        } else {

            throw new ConfigurationException();

        }

    }

    private InputStreamReader loadGlobalConfig() throws IOException {
        InputStreamReader file = new InputStreamReader(new FileInputStream(GLOBAL_CONFIG_PATH),
                Charset.forName("UTF-8"));
        if (file.ready()) {
            return file;
        }
        return null;
    }

}
