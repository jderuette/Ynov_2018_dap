package fr.ynov.dap.GmailPOO.metier;

import java.io.IOException;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;

@Service
public class GMailService extends GoogleService {
	





	
	/**
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 * @throws UnsupportedOperationException 
	 * 
	 */
	
	public GMailService() throws UnsupportedOperationException, GeneralSecurityException, IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @return service gmail
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	
	public  Gmail getService(String user) throws GeneralSecurityException, IOException {
		
		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT,user))
				.setApplicationName(configuration.getApplicationName()).build();
		return service;
	}
	

	

	/**
	 * 
	 * @param user
	 * @return nomber of unread mail
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	
	public   List<Message> getListEmails(final  String user) throws IOException, GeneralSecurityException {
         
	ListMessagesResponse response = getService(user).users().messages().list("me").setQ("in:unread").execute();
		
		List<Message> messages = new ArrayList<Message>();
		
		while (response.getMessages() != null) {
			
			messages.addAll(response.getMessages());
			if (response.getNextPageToken() != null) {
				String pageToken = response.getNextPageToken();
		
				response = getService( user).users().messages().list("me").setQ("in:unread").setPageToken(pageToken).execute();
			} else {
				break;
			}
		}
		return messages;

	}
	
}
