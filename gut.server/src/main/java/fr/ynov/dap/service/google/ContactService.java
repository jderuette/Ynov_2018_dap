package fr.ynov.dap.service.google;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Person;



@Service
public class ContactService extends GoogleService{

	/**
	 * Initiliatision du service
	 * @throws IOException
	 */
	public ContactService() throws IOException {
		getLogger().debug("Initialisation du service contact (peopleapi)");
		init();	
	}

	/**
	 * Récupération du service
	 * @param userId
	 * @return le service people
	 * @throws IOException
	 */
	public PeopleService  getService(String userId) throws IOException {
		getLogger().debug("Recuperation du service people");
		return 	new PeopleService .Builder(getConfiguration().getHTTP_TRANSPORT(), JSON_FACTORY, getCredentials(userId))
				.setApplicationName(getConfiguration().getApplicationName())        
				.build();
	}

	/**
	 * Récupération du nombre de contacts 
	 * @param userId
	 * @return le nombre de contacts
	 * @throws IOException
	 */
	public int getContactCount(final String userId) throws IOException{
		getLogger().debug("Recuperation des contacts google");
		PeopleService service = getService(userId);
		ListConnectionsResponse r = service.people().connections()
				.list("people/me")
				.setPageSize(500)
				// specify fields to be returned
				.setRequestMaskIncludeField("person.names,person.emailAddresses")
				.execute();

		//TODO gut by Djer |API Google| (ancien TO-DO) Et si j'ai plus de 500 contacts ? (utilise la pagination comme dans getUnreadMessageCount)
		List<Person> connections = r.getConnections();
		return connections.size();
	}
}
