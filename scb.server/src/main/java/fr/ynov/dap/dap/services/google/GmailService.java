package fr.ynov.dap.dap.services.google;

import java.io.IOException;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import fr.ynov.dap.dap.Config;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.GoogleAccount;


@Service
public class GmailService extends GoogleService{
	@Autowired
	AppUserRepository repository;
	
	public GmailService() { 
		super();
	}
	/**
	 * 
	 * @param user
	 * @return return the service that provides info on mails
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	//TODO scb by Djer |POO| Pas de modifier donc celui de la classe par defaut (donc public). N'est utilisé que par cette classe, devrait être privée
	Gmail getService(String user) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, this.GetCredentials(user))
                .setApplicationName(config.getAppName())
                .build();
        return service;
	}
	
	/**
	 * 
	 * @param user
	 * @return number of unread messages
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public Integer getNbUnreadEmails(String user) throws IOException, GeneralSecurityException {
		AppUser appUser = repository.findByName(user);
		//TODO scb by Djer |POO| Vérifie si le appUser n'est pas null (cas ou le userKey n'existe pas dans la BDD)
		List<GoogleAccount> gAccounts = appUser.getAccounts();
		Integer totalUnreadMessages = 0;
		for(int i =0; i < gAccounts.size(); i++) {
	        Label label = this.getService(gAccounts.get(i).getName()).users().labels().get("me", "INBOX").execute();
	        totalUnreadMessages += 	label.getMessagesUnread();
		}
		return totalUnreadMessages;
	}
	
	/**
	 * 
	 * @param user
	 * @return all mails labels
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	//TODO scb by Djer |JavaDoc| le "user" pour cette méthode est un acocuntName, alors que pour getNbUnreadEmails le "user" est un userKey. Clarifie au MOINS par le nom de variable et la JavaDoc
	public List<Label> getMailLabels(String user) throws IOException, GeneralSecurityException{
        ListLabelsResponse listResponse = this.getService(user).users().labels().list("me").execute();
        List<Label> labels = listResponse.getLabels();
        return labels;
	}
	

}
