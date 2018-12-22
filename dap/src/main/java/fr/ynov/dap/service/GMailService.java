package fr.ynov.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
@Service
/**
 * Permet d'effectuer les actions relatives aux mails google, notamment leur décompte.
 * @author abaracas
 *
 */
public class GMailService extends GoogleService{
    //TODO baa by Djer |Log4J| Devrait être final (la (pseudo) référence ne sera pas modifiée)
    private static Logger LOG = LogManager.getLogger();
    /**
     * Constructeur  de la classe
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     */
    private GMailService() throws GeneralSecurityException, IOException {
	super();
    }
    /**
     * Retourne le service généré
     * @param gUserId l'id de l'utilisateur google
     * @return le service
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    public Gmail getService(String gUserId) throws IOException, GeneralSecurityException {
	Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY,
		super.getCredentials(gUserId)).setApplicationName(maConfig.getApplicationName()).build();
	return service;
    }
    /**
     * Retourne les messages non lus d'un compte
     * @param accountName le nom du compte google
     * @return le nombre de ùessages non lus
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */   
    public Integer getUnreadMessageCount(String accountName) throws IOException, GeneralSecurityException {
	ListMessagesResponse listMessagesResponse = getService(accountName).users().messages().list("me")
		.setQ("in:unread").execute();
	List<Message> messages = new ArrayList<Message>();
	while (listMessagesResponse.getMessages() != null) {
	    messages.addAll(listMessagesResponse.getMessages());
	    if (listMessagesResponse.getNextPageToken() != null) {
		String pageToken = listMessagesResponse.getNextPageToken();
		listMessagesResponse = getService(accountName).users().messages().list("me").setPageToken(pageToken).execute();
	    } else {
		break;
	    }
	}
	Integer unreadMessageCount = 0;
	//TODO baa by Djer |POO| Il y a une méthdoe "size" sur les collections, pas besoin de parcourir pour compter
	for (int i = 0; i < messages.size(); i++) {
	    unreadMessageCount++;
	}
	//TODO baa by Djer |Rest API| pas de SysOut sur un serveur
	System.out.println(unreadMessageCount.toString());
	LOG.info("Le compte " + accountName + " a" + unreadMessageCount + " messages non lus.");
	return unreadMessageCount;
    }
    
    /**
     * Retourne les messages non lus de tous les comptes
     * @param userKey le nom du compte google
     * @return le nombre de ùessages non lus
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */   
    public Integer getAllUnreadMessageCount(String userKey) throws IOException, GeneralSecurityException {
	;
	//TODO baa by Djer |API Google| Il FAUT que tu itère sur les compte Google (enregistré en BDD) si tu veux que cela fonctionne. Là tu as exactement la même chose que dans "getUnreadMessageCount" 
	ListMessagesResponse listMessagesResponse = getService(userKey).users().messages().list("me")
		.setQ("in:unread").execute();
	List<Message> messages = new ArrayList<Message>();
	while (listMessagesResponse.getMessages() != null) {
	    messages.addAll(listMessagesResponse.getMessages());
	    if (listMessagesResponse.getNextPageToken() != null) {
		String pageToken = listMessagesResponse.getNextPageToken();
		listMessagesResponse = getService(userKey).users().messages().list("me").setPageToken(pageToken).execute();
	    } else {
		break;
	    }
	}
	Integer unreadMessageCount = 0;
	for (int i = 0; i < messages.size(); i++) {
	    unreadMessageCount++;
	}
	System.out.println(unreadMessageCount.toString());
	LOG.info("L'utilisateur " + userKey + " a" + unreadMessageCount + " messages non lus.");
	return unreadMessageCount;
    }
}
