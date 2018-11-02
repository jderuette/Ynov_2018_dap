package fr.ynov.dap.dap;

import java.util.ArrayList;
import java.util.List;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

public class Config {

    //TODO for by Djer Tu peux quand même être poli !
    private static final String APPLICATION_NAME = "FUCK IT";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String OAUTH2_CALLBACK_URL = "/oAuth2Callback";

    //TODO for by Djer Ce commentaire Javdoc est vraiment lié à cet attribut ???
    /**
    * Global instance of the scopes required by this quickstart.
    * If modifying these scopes, delete your previously saved tokens/ folder.
    */
    private String oauth2CallbackUrl = null;

    //TODO for by Djer Evite de mélanger de la conf "dev" et de la conf "admin systeme"
    private static final List<String> SCOPES = new ArrayList<String>();
    //TODO for by Djer les resources vont dasn src/main/resources, ca évite de devoir faire ces "bidouilles" !
    private static final String CREDENTIALS_FILE_PATH = "/resources/credentials.json";

    private String applicationName = null;
    private List<String> scopes = null;
    private String credentialsFilePath = null;
    private JsonFactory jsonFactory = null;
    private String tokensDirectoryPath = null;

    /**
    * Constructeur par défaut du Config
    */

    public Config() {
        applicationName = APPLICATION_NAME;
        scopes = SCOPES;
        setScopes(GmailScopes.GMAIL_LABELS);
        setScopes(GmailScopes.GMAIL_READONLY);
        setScopes(CalendarScopes.CALENDAR_EVENTS_READONLY);
        setScopes(PeopleServiceScopes.CONTACTS_READONLY);

        credentialsFilePath = CREDENTIALS_FILE_PATH;
        jsonFactory = JSON_FACTORY;
        tokensDirectoryPath = TOKENS_DIRECTORY_PATH;
        oauth2CallbackUrl = OAUTH2_CALLBACK_URL;
    }

    /**
     * Retourne le nom de l'application
     * @return
     */
    //TODO for b Djer Pas de majuscule au début des méthodes !
    public String GetApplicationName() {
        return applicationName;
    }

    /**
     * Retourne le chemin d'accès aux Credentials
     * @return
     */
    //TODO for b Djer Pas de majuscule au début des méthodes !
    public String GetCredentialsFilePath() {
        return credentialsFilePath;
    }

    /**
     * Retourne le chemin d'accès aux tokens
     * @return
     */
    //TODO for b Djer Pas de majuscule au début des méthodes !
    public String GetTokensDirectoryPath() {
        return tokensDirectoryPath;
    }

    /**
     * Retourne la JSON factory
     * @return
     */
    //TODO for b Djer Pas de majuscule au début des méthodes !
    public JsonFactory GetJsonFactory() {
        return jsonFactory;
    }

    /**
     * Retourne les Scopes
     * @return
     */
    public List<String> getScopes() {
        //TODO fir by Djer Renvoyer la constantes ?? elle vaut toujour [] en plus !
        return SCOPES;
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
