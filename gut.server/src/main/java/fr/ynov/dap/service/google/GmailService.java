package fr.ynov.dap.service.google;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;
import com.google.api.services.gmail.model.ListMessagesResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class GmailService extends GoogleService{

	/**
	 * Initialisation du service
	 * @throws IOException
	 */
	public GmailService() throws IOException {
		getLogger().debug("Initialisation du service Gmail");
		init();	
	}

	/**
	 * Récupération du service service
	 * @param userId
	 * @return Gmail service
	 * @throws IOException
	 */
	public Gmail getService(String userId) throws IOException {
		getLogger().debug("Recuperation du service Gmail");
		return 	 new Gmail.Builder(getConfiguration().getHTTP_TRANSPORT(), JSON_FACTORY, getCredentials(userId))
				.setApplicationName(getConfiguration().getApplicationName())        
				.build();
	}

	/**
	 * Récupération du nombre de messages non lus 
	 * @param userId
	 * @return List de message non lus
	 * @throws IOException
	 */
	public List<Message>  getUnreadMessageCount(final String userId) throws IOException{
		getLogger().debug("Recuperation du nombre de messages non lus");
		Gmail service = getService(userId);
		ListMessagesResponse listMessagesResponse = service.users().messages().list(getUser()).setQ("in:unread -category:{social promotions updates forums}").execute();
		List<Message> messages = new ArrayList<Message>();
		while (listMessagesResponse.getMessages() != null) {
			messages.addAll(listMessagesResponse.getMessages());
			if (listMessagesResponse.getNextPageToken() != null) {
				String pageToken = listMessagesResponse.getNextPageToken();
				listMessagesResponse = service.users().messages().list(getUser()).setPageToken(pageToken).execute();
			} else {
				break;
			}
		}
		return messages;
	}

	/**
	 * Récupération des labels 
	 * @param userId
	 * @return la liste des labels
	 * @throws IOException
	 */
	public List<String> getLabels(@RequestParam final String userId) throws IOException {
		getLogger().debug("Recuperation des labels lies au compte gmail");
		ListLabelsResponse listResponse = getService(userId).users().labels().list(getUser()).execute();
		List<Label> labels = listResponse.getLabels();
		List<String> listeLabels= new ArrayList<String>();
		if (!labels.isEmpty()) {
			for (Label label : labels) {
				listeLabels.add(label.getName());
			}
		}
		return listeLabels;
	}


}