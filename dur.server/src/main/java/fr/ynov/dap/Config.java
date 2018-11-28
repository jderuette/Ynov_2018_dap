package fr.ynov.dap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

/**
 * The Class Config.
 */
public class Config {
    /**
     * Store OAuth2 callback url.
     */
    private String oAuth2CallbackUrl;

    /**
     * Store credentials folder.
     */
    private String credentialsFolder;

    /**
     * Store filename of credentials.
     */
    private String clientSecretPath;

    /**
     * Store application name.
     */
    private String applicationName;

    /** The scopes. */
    private final List<String> scopes = new ArrayList<String>();

    /**
     * Instantiates a new config.
     * @throws IOException 
     */
    public Config() {
        scopes.add(CalendarScopes.CALENDAR_READONLY);
        scopes.add(PeopleServiceScopes.CONTACTS_READONLY);
        scopes.add(GmailScopes.GMAIL_LABELS);

        try {
            loadConfig();
        } catch (IOException e) {

        }
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
        return clientSecretPath;
    }

    /**
     * Set new client secret file.
     * @param clientSecretFile Client secret file
     */
    public void setClientSecretFile(final String clientSecretFile) {
        clientSecretPath = clientSecretFile;
    }
    
    /**
     * Gets the scopes.
     *
     * @return the scopes
     */
    public final List<String> getScopes() {
        return scopes;
    }

    /**
     * Return OAuth2 CallBack Url.
     * @return OAuth2 CallBack Url
     */
    public String getOAuth2CallbackUrl() {
        return oAuth2CallbackUrl;
    }

    private void loadConfig() throws IOException {
        String authConfigFile = "config.properties";
        InputStream authConfigStream = Config.class.getClassLoader().getResourceAsStream(authConfigFile);

        if (authConfigStream != null) {
            Properties authProps = new Properties();
            try {
                authProps.load(authConfigStream);
                credentialsFolder = authProps.getProperty("credentialPath");
                clientSecretPath = authProps.getProperty("clientSecretPath");
                applicationName = authProps.getProperty("appName");
                oAuth2CallbackUrl = authProps.getProperty("oauth2Callback");

            } finally {
                authConfigStream.close();
            }
        } else {
            throw new FileNotFoundException("Property file '" + authConfigFile + "' not found in the classpath.");
        }
    }
}