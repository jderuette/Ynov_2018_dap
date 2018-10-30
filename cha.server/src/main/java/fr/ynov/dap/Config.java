package fr.ynov.dap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;

/**
 * The Class Config.
 */
@Controller
public class Config {
	//FIXME cha by DJer les attribut en MAJUSCULE devrait être static final
	//FIXME cha by Djer Pas de getter/setter sur des "constantes"
	
	/** The credentials folder. */
	private static String CREDENTIALS_FOLDER = "google/credentials" ; 
	
	/** The client secret dir. */
	private String CLIENT_SECRET_DIR = "google/client";
    
    /** The Constant CREDENTIALS_FILE_PATH. */
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	
	/** The Constant APPLICATION_NAME. */
    //TODO cha by Djer devrait être privé. sert à initialiser la variable, qui elle est accesible via un Getter
	public static final String APPLICATION_NAME = "HoCDap";
	
	/** The scopes. */
	private static List<String> SCOPES = new ArrayList<String>();

	
	/** The application name. */
	public String applicationName;
	
	/** The credential folder. */
	public String credentialFolder;
	
	/** The client secret file. */
	public String clientSecretFile;
	
	/**
	 * Instantiates a new config.
	 */
	public Config() {
		//TODO cha by Djer Les varaibles devraient être initialisées avec une valeur par defaut !
		
		//TODO cha by Djer mettre les Scope ici n'est pas idéal, mais pourquoi pas.
		this.SCOPES.add(GmailScopes.GMAIL_LABELS);
		this.SCOPES.add(CalendarScopes.CALENDAR_EVENTS_READONLY);
		this.SCOPES.add(PeopleServiceScopes.CONTACTS_READONLY);
	}

	/**
	 * Gets the scopes.
	 *
	 * @return the scopes
	 */
	public static List<String> getSCOPES() {
		return SCOPES;
	}

	/**
	 * Sets the scopes.
	 *
	 * @param scopes the new scopes
	 */
	public static void setSCOPES(List<String> scopes) {
		SCOPES = scopes;
	}

	/**
	 * Gets the credentials folder.
	 *
	 * @return the credentials folder
	 */
	public static String getCREDENTIALS_FOLDER() {
		return CREDENTIALS_FOLDER;
	}

	/**
	 * Sets the credentials folder.
	 *
	 * @param credentials_folder the new credentials folder
	 */
	public static void setCREDENTIALS_FOLDER(String credentials_folder) {
		CREDENTIALS_FOLDER = credentials_folder;
	}
	
	/**
	 * Gets the credentials file path.
	 *
	 * @return the credentials file path
	 */
	public static String getCredentialsFilePath() {
		return CREDENTIALS_FILE_PATH;
	}
}
