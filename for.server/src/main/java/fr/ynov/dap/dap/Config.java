package fr.ynov.dap.dap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

@EnableJpaRepositories
public class Config {

    private static final String APPLICATION_NAME = "DaP";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String OAUTH2_CALLBACK_URL = "/oAuth2Callback";

    private String oauth2CallbackUrl = OAUTH2_CALLBACK_URL;

    //TODO for by Djer Evite de mélanger de la conf "dev" et de la conf "admin systeme"
    private static final List<String> SCOPES = new ArrayList<String>();
    private static final String CONFIG_FILE_PATH = System.getProperty("user.home") + "\\dap.properties";
    private static final String CREDENTIALS_FILE_PATH = System.getProperty("user.home") + "\\credentials.json";
    private static final String MICROSOFT_CREDENTIALS_FILE_PATH = System.getProperty("user.home") + "\\auth.properties";

    static private String applicationName = APPLICATION_NAME;
    private List<String> scopes = SCOPES;
    static private String credentialsFilePath = CREDENTIALS_FILE_PATH;
    static private String microsoftCredentialsFilePath = MICROSOFT_CREDENTIALS_FILE_PATH;
    static private String configFilePath = CONFIG_FILE_PATH;
    private JsonFactory jsonFactory = JSON_FACTORY;
    private String tokensDirectoryPath = TOKENS_DIRECTORY_PATH;

    /**
    * Constructeur par défaut du Config
     * @throws IOException 
    */
    public Config() throws IOException{
    	this.applicationName = APPLICATION_NAME;
    	this.configFilePath = CONFIG_FILE_PATH;
    	this.credentialsFilePath = CREDENTIALS_FILE_PATH;
    	this.microsoftCredentialsFilePath = MICROSOFT_CREDENTIALS_FILE_PATH;

    	InputStreamReader configStream = new InputStreamReader(new FileInputStream(CONFIG_FILE_PATH), Charset.forName("UTF-8"));

        if (null != configStream) {
          Properties configProps = new Properties();
          try {
            configProps.load(configStream);
            applicationName = configProps.getProperty("applicationName");
            credentialsFilePath = configProps.getProperty("credentialsFilePath");
            microsoftCredentialsFilePath = configProps.getProperty("microsoftCredentialsFilePath");
          } finally {
        	  configStream.close();
          }
        }
        setScopes(GmailScopes.GMAIL_LABELS);
        setScopes(GmailScopes.GMAIL_READONLY);
        setScopes(CalendarScopes.CALENDAR_EVENTS_READONLY);
        setScopes(PeopleServiceScopes.CONTACTS_READONLY);
    }
    
    void loadConfig() throws IOException {
        InputStreamReader configStream = new InputStreamReader(new FileInputStream(App.config.getConfigFile()), Charset.forName("UTF-8"));

        if (null != configStream) {
          Properties configProps = new Properties();
          try {
            configProps.load(configStream);
            applicationName = configProps.getProperty("applicationName");
            credentialsFilePath = configProps.getProperty("credentialsFilePath");
            microsoftCredentialsFilePath = configProps.getProperty("microsoftCredentialsFilePath");
          } finally {
        	  configStream.close();
          }
        }
      }

    /**
     * Retourne le nom de l'application
     * @return
     */
    public String GetApplicationName() {
        return applicationName;
    }

    /**
     * Retourne le chemin d'accès aux Credentials
     * @return
     */
    public String getCredentialsFilePath() {
        return credentialsFilePath;
    }
    
    /**
     * Retourne le chemin d'accès aux Credentials
     * @return
     */
    public String getMicrosoftCredentialsFile() {
        return microsoftCredentialsFilePath;
    }
    
    /**
     * Retourne le chemin d'accès aux Configs
     * @return
     */
    public String getConfigFile() {
        return configFilePath;
    }

    /**
     * Retourne le chemin d'accès aux tokens
     * @return
     */
    public String GetTokensDirectoryPath() {
        return tokensDirectoryPath;
    }

    /**
     * Retourne la JSON factory
     * @return
     */
    public JsonFactory GetJsonFactory() {
        return jsonFactory;
    }

    /**
     * Retourne les Scopes
     * @return
     */
    public List<String> getScopes() {
        return scopes;
    }

    /**
     * Défini les scopes
     * @param scope
     */
    public void setScopes(String scope) {
        scopes.add(scope);
    }

    /**
     * Retourne l'url d'accès à l'Authentification
     * @return
     */
    public String getoAuth2CallbackUrl() {
        return oauth2CallbackUrl;
    }
}
