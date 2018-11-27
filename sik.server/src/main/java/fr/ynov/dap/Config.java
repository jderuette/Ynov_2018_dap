package fr.ynov.dap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * This class allow developer to configure this application.
 * @author Kévin Sibué
 *
 */
@Configuration
@PropertySource("classpath:dap.properties")
public class Config {

    /**
     * Path where microsoft auth properties files are stored.
     */
    private static final String MICROSOFT_AUTH_PROPERTIES_PATH = System.getProperty("user.home")
            + System.getProperty("file.separator") + "auth.properties";

    /**
     * Store OAuth2 callback url.
     */
    @Value("${dap.providers.google.callback_url}")
    private String oAuth2CallbackUrl;

    /**
     * Store credentials folder.
     */
    @Value("${dap.providers.google.credentials_folder_name}")
    private String credentialsFolder;

    /**
     * Store application name.
     */
    @Value("${dap.application_name}")
    private String applicationName;

    /**
     * Store datastore directory path.
     */
    @Value("${dap.datastore.path}")
    private String datastoreDirectory;

    /**
     * Store Google credentials path.
     */
    @Value("${dap.providers.google.api.credentials_path}")
    private String googleCredentialsPath;

    /**
     * Store Microsoft App Id.
     */
    private String microsoftAppId;

    /**
     * Store Microsoft Redirect Url.
     */
    private String microsoftRedirectUrl;

    /**
     * Store Microsoft App Password.
     */
    private String microsoftAppPassword;

    /**
     * Logger instance.
     */
    private Logger logger = LogManager.getLogger();

    /**
     * Default constructor.
     * @throws IOException Exception
     */
    public Config() throws IOException {
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
     * Return credential folder.
     * @return Credential folder
     */
    public String getCredentialFolder() {
        return credentialsFolder;
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
     * @return the microsoftAppId
     */
    public String getMicrosoftAppId() {
        return microsoftAppId;
    }

    /**
     * @return the microsoftRedirectUrl
     */
    public String getMicrosoftRedirectUrl() {
        return microsoftRedirectUrl;
    }

    /**
     * @return the microsoftAppPassword
     */
    public String getMicrosoftAppPassword() {
        return microsoftAppPassword;
    }

    /**
     * Load Microsoft configuration from file.
     * @throws IOException Exception
     */
    public void loadConfig() throws IOException {

        InputStreamReader file = new InputStreamReader(new FileInputStream(MICROSOFT_AUTH_PROPERTIES_PATH),
                Charset.forName("UTF-8"));

        if (file.ready()) {

            Properties msProps = new Properties();

            try {

                msProps.load(file);

                microsoftAppId = msProps.getProperty("appId");
                microsoftAppPassword = msProps.getProperty("appPassword");
                microsoftRedirectUrl = msProps.getProperty("redirectUrl");

            } finally {

                file.close();

            }

        } else {

            throw new FileNotFoundException(
                    "Property file '" + MICROSOFT_AUTH_PROPERTIES_PATH + "' not found in the classpath.");

        }

    }

}
