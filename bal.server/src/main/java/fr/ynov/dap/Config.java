package fr.ynov.dap;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * The Class Config.
 */
@Configuration
@PropertySource("classpath:application.properties")
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
    private static final String DATASTORE_FOLDER = System.getProperty("user.home");

	/** The Constant GOOGLE_CREDENTIAL_PATH. */
    private static final String GOOGLE_CREDENTIAL_PATH = System.getProperty("user.home")
            + System.getProperty("file.separator") + "credentials.json";

	/** The Constant MICROSOFT_CREDENTIAL_PATH. */
    private static final String MICROSOFT_CREDENTIAL_PATH = System.getProperty("user.home")
            + System.getProperty("file.separator") + "auth.properties";

	/** The Constant GLOBAL_CONFIG_PATH. */
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
    private String datastoreFolder = DATASTORE_FOLDER;

	/** The google credentials path. */
    private String googleCredentialsPath = GOOGLE_CREDENTIAL_PATH;

	/** The microsoft credentials path. */
    private String microsoftCredentialsPath = MICROSOFT_CREDENTIAL_PATH;

	/** The microsoft app id. */
    private String microsoftAppId;

	/** The microsoft redirect url. */
    private String microsoftRedirectUrl;

	/** The microsoft app password. */
    private String microsoftAppPassword;
    
	/** The microsoft authority url. */
    private String microsoftAuthorityUrl;

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
            String newPath = resolvePath(dataStoreDirectory);
            datastoreFolder = newPath;
        }
        loadConfig();
    }

    /**
     * Return Datastore directory.
     * @return Datastore directory
     */
    public String getDatastoreFolder() {
        return datastoreFolder;
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
	 * Gets the google credentials path.
	 *
	 * @return the googleCredentialsPath
	 */
    public String getGoogleCredentialsPath() {
        return googleCredentialsPath;
    }

    /**
	 * Sets the google credentials path.
	 *
	 * @param val the googleCredentialsPath to set
	 */
    public void setGoogleCredentialsPath(final String val) {
        this.googleCredentialsPath = val;
    }

    /**
	 * Gets the microsoft credentials path.
	 *
	 * @return the microsoftCredentialsPath
	 */
    public String getMicrosoftCredentialsPath() {
        return microsoftCredentialsPath;
    }

    /**
	 * Sets the microsoft credentials path.
	 *
	 * @param val the microsoftCredentialsPath to set
	 */
    public void setMicrosoftCredentialsPath(final String val) {
        this.microsoftCredentialsPath = val;
    }

    /**
	 * Gets the microsoft app id.
	 *
	 * @return the microsoftAppId
	 */
    public String getMicrosoftAppId() {
        return microsoftAppId;
    }

    /**
	 * Sets the microsoft app id.
	 *
	 * @param val the microsoftAppId to set
	 */
    public void setMicrosoftAppId(final String val) {
        this.microsoftAppId = val;
    }

    /**
	 * Gets the microsoft redirect url.
	 *
	 * @return the microsoftRedirectUrl
	 */
    public String getMicrosoftRedirectUrl() {
        return microsoftRedirectUrl;
    }

    /**
	 * Sets the microsoft redirect url.
	 *
	 * @param val the microsoftRedirectUrl to set
	 */
    public void setMicrosoftRedirectUrl(final String val) {
        this.microsoftRedirectUrl = val;
    }

    /**
	 * Gets the microsoft app password.
	 *
	 * @return the microsoftAppPassword
	 */
    public String getMicrosoftAppPassword() {
        return microsoftAppPassword;
    }

    /**
	 * Sets the microsoft app password.
	 *
	 * @param val the microsoftAppPassword to set
	 */
    public void setMicrosoftAppPassword(final String val) {
        this.microsoftAppPassword = val;
    }

	/**
	 * Gets the microsoft authority url.
	 *
	 * @return the microsoft authority url
	 */
    public String getMicrosoftAuthorityUrl() {
		return microsoftAuthorityUrl;
	}

	/**
	 * Sets the microsoft authority url.
	 *
	 * @param microsoftAuthorityUrl the new microsoft authority url
	 */
	public void setMicrosoftAuthorityUrl(String microsoftAuthorityUrl) {
		this.microsoftAuthorityUrl = microsoftAuthorityUrl;
	}

	/**
	 * Load config.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void loadConfig() throws IOException {

        InputStreamReader isr = loadGlobalConfig();

        if (isr != null) {

            Properties authProps = new Properties();

            try {

                authProps.load(isr);
                oAuth2CallbackUrl = authProps.getProperty("dap.providers.google.callback_url");
                credentialsFolder = authProps.getProperty("dap.providers.google.credentials_folder");
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
        }
    }

	/**
	 * Load global config.
	 *
	 * @return the input stream reader
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
    private InputStreamReader loadGlobalConfig() throws IOException {
        InputStreamReader file = new InputStreamReader(new FileInputStream(GLOBAL_CONFIG_PATH),
                Charset.forName("UTF-8"));
        if (file.ready()) {
            return file;
        }
        return null;
    }
    /**
     * Transform string path with write system separator.
     * IMPORTANT ! Use "/" on your path.
     * @param val String path
     * @return New string path with every "/" replaced by file.separator
     */
    public static String resolvePath(final String val) {
        String result = "";
        String[] parts = val.split("/");
        for (int i = 0; i < parts.length; i++) {
            result += parts[i];
            if (i != parts.length - 1) {
                result += System.getProperty("file.separator");
            }
        }
        return result;
}

}
