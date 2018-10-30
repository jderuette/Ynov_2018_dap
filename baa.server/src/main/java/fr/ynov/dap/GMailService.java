package fr.ynov.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
@RestController
public class GMailService extends GoogleService{
  //FIXME baa by Djer Séparation Controller/Services ?

    private static GMailService instanceGMmailService;
    /**
     * Constructeur en Singleton de la classe
     * @throws GeneralSecurityException
     * @throws IOException
     */
    private GMailService() throws GeneralSecurityException, IOException {
	super();
    }
    /**
     * Retourne le service généré
     * @param userId
     * @return le service
     * @throws IOException
     */
    public Gmail getService(String userId) throws IOException {
	Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY,
		//TODO baa by Djer utiliser la Config ?
		super.getCredentials(HTTP_TRANSPORT, userId)).setApplicationName("Ynov Dap").build();
	return service;
    }
    /**
     * Récupère une instance de la classe, si existante, sinon en crée une
     * @return l'instance
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static GMailService get() throws GeneralSecurityException, IOException {
	if (instanceGMmailService == null) {
	    return instanceGMmailService = new GMailService();
	}
	return instanceGMmailService;
    }
    /**
     * @param user
     * @return
     * @throws IOException
     */
    //TODO baa by Djer JavaDoc ?
    @RequestMapping("/email/messagesNonLus")
    public String getUnreadMessageCount(String user) throws IOException {
	//TODO baa by Djer Attention tu écrase le paramètre de la méthode !! 
	user="me";
	ListMessagesResponse listMessagesResponse = getService(user).users().messages().list(user)
		//TODO baa by Djer uniquement dans ma boite principal ?
		.setQ("in:unread").execute();
	List<Message> messages = new ArrayList<Message>();
	while (listMessagesResponse.getMessages() != null) {
	    messages.addAll(listMessagesResponse.getMessages());
	    if (listMessagesResponse.getNextPageToken() != null) {
		String pageToken = listMessagesResponse.getNextPageToken();
		listMessagesResponse =getService(user).users().messages().list(user).setPageToken(pageToken).execute();
	    } else {
		break;
	    }
	}
	Integer unreadMessageCount = 0;
	//TODO baa by Djer un "size()" sur la liste fonctionnerait aussi bien
	for (Message message : messages) {
	    unreadMessageCount++;
	}
	System.out.println("Vous avez " +  unreadMessageCount.toString() +" messages non lus.");
	return "Vous avez " +  unreadMessageCount.toString() +" messages non lus.";
    }

}
