package fr.ynov.dap.dap.service;



import java.io.IOException;
import java.security.GeneralSecurityException;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;


/**
 * The Class GmailService.
 */
@RestController
//TODO brs by Djer Un RestController sans méthode annotée "@RequetsMapping" ?
public class GmailService extends GoogleServices {
	
	
	
	/**
	 * Instantiates a new gmail service.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	private GmailService() throws IOException, GeneralSecurityException {
		super();
	}
	
	/** The instance. */
	private static GmailService INSTANCE = null;
	
	/**
	 * Gets the single instance of GmailService.
	 *
	 * @return single instance of GmailService
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	//TODO brs by Djer Inutile, Spring le fait pour toi sur un @RestController (ou un @Service).
    // Deplus ton unique méthode est static !!!
	public static GmailService getInstance() throws IOException, GeneralSecurityException {
		if(INSTANCE==null) {
			INSTANCE = new GmailService();
			
		}return INSTANCE;
	}

	/**
	 * Gets the label info.
	 *
	 * @param user the user
	 * @return the label info
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public static String getLabelInfo(String user) throws IOException, GeneralSecurityException {
	
		if(user == null) {
		    //TODO brs by Djer évite de laisser trainer ton adresse mail dans du code !
        	user = "sylvain69740@gmail.com";
        }
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, configuration.getJSON_FACTORY(), getCredentials(HTTP_TRANSPORT, configuration.getCREDENTIALS_FILE_PATH(), user))
                .setApplicationName(configuration.getAPPLICATION_NAME())
                .build();
        
		String userId = user;
        
		//TODO brs by Djer ne fonctionne QUE si je met mon adresse email comme "userKey" !!! 
        Label label = service.users().labels().get(userId, "INBOX").execute();
        JSONObject json = new JSONObject();
        json.put("Nb messages : %s\n", label.getMessagesTotal());
        json.put("- %s\n", label.getName());
        json.put("Nb messages unread : %s\\n", label.getMessagesUnread());

		return json.toString();
	}
}

