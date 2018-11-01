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
//TODO jog by Djer séparation Controller/service
public class GmailService extends GoogleService {
	
	private static GmailService instance;

	private GmailService() throws GeneralSecurityException, IOException {
		super();
		
	}

	private Gmail getService(String userId) throws IOException, GeneralSecurityException {
	    Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY,
	                super.getCredentials(HTTP_TRANSPORT, userId)).setApplicationName(configuration.getApplication_name()).build();
	    return service;
	    }
	
	//TODO job by Djer Maintenant fait par Spring (Cf remarque CalendarService)
	public static GmailService get() throws GeneralSecurityException, IOException {
		if(instance == null) {
			return instance = new GmailService();
		}
		else {
			return instance;
		}
	}
	
	/**
	 * Fonction qui retourne les emails non-lus
	 * @param user
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@RequestMapping("unread")
	public int getUnreadMessageCount(String user) throws IOException, GeneralSecurityException {
	  //TODO jog by Djer Attention tu écrase le user passé en paramètre !!
	    user ="me";
		ListMessagesResponse listMessagesResponse = getService(user).users().messages().list(user)
				.setQ("in:unread").execute();
		List<Message> messages = new ArrayList<Message>();
		while (listMessagesResponse.getMessages() != null) {
			messages.addAll(listMessagesResponse.getMessages());
			if (listMessagesResponse.getNextPageToken() != null) {
				String pageToken = listMessagesResponse.getNextPageToken();
				listMessagesResponse = getService(user).users().messages().list(user).setPageToken(pageToken).execute();
			} else {
				break;
			}
		}
		//TODO jog by Djer Il y a un "size" sur le Type List
		int unreadMessageCount = 0;
		for (Message message : messages) {
			unreadMessageCount++;
		}
		 //TODO jog by Djer PAS de Sysout sur un serveur ! (à la limite un LOG)
		System.out.println("vous avez " + unreadMessageCount +" mails non lus. \n");
		return unreadMessageCount;
	}

}