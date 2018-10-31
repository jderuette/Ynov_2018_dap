package fr.ynov.dap.dap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.people.v1.PeopleServiceScopes;


//FIXME scb by Djer Mauvaise utilisation du @configuration, ici c'est JUSTE un "@Bean", mais bon comme presque tout est static, c'est juste ... presque rien (du point de vue POO)
@Configuration
//FIXME scb by Djer principe ZeronConf à revoir, ici tu as un prinicpe "NO conf".
// Il doit y avoir un conf "par defaut" mais elle DOIT être modifiable
public class Config{
    //TODO scb by Djer ton fichier "credential" doit etre src/main/resources, et PAS dans src/main/java (car ce n'est PAS du java)
    public static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    public static final String TOKENS_DIRECTORY_PATH = "tokens";
    public static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    
    //TODO scb by Djer évite de mélanger des conf "developpeur" et des conf "administrateur systeme"
	public static final int SENSIBLE_DATA_LAST_CHAR = 0;
	public static final int SENSIBLE_DATA_FIRST_CHAR = 0;
	//TODO scb by Djer En Majuscule et pas STATIC FINAL ? Un "attribut" sans Getter/Setter ?
	public List<String> ALL_SCOPES = new ArrayList<String>(Arrays.asList(GmailScopes.GMAIL_LABELS, CalendarScopes.CALENDAR_READONLY, PeopleServiceScopes.CONTACTS_READONLY));
	public String getoAuth2CallbackUrl() {
		return "/oAuth2CallBack";
	}
}