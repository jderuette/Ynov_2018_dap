package fr.ynov.dap;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.people.v1.PeopleService.People;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
@RestController
public class PeopleService extends GoogleService {
  //FIXME baa by Djer Séparation Controller/Services ?

    private static PeopleService instancePeopleService;
    /**
     * Constructeur en Singleton de la classe
     * @throws GeneralSecurityException
     * @throws IOException
     */
    private PeopleService() throws GeneralSecurityException, IOException {
	super();
    }
    /**
     * Récupère le service
     * @param userId
     * @return le service
     * @throws IOException
     */
    public  com.google.api.services.people.v1.PeopleService getService(String userId) throws IOException {
	com.google.api.services.people.v1.PeopleService service = new com.google.api.services.people.v1.PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY,
		super.getCredentials(HTTP_TRANSPORT, userId)).setApplicationName("Ynov Dap").build();
	return service;
    }
    /**
     * Récupère l'instance si elle existe
     * @return l'instance existante, ou créee
     * @throws GeneralSecurityException
     * @throws IOException
     */
    public static PeopleService get() throws GeneralSecurityException, IOException {
	if (instancePeopleService == null) {
	    return instancePeopleService = new PeopleService();
	}
	return instancePeopleService;
    }

    @RequestMapping("/contacts")
    /**
     * Récupère les contacts, leur nombre, nom, tel
     * @param user
     * @return les valeurs voulus
     * @throws IOException
     */
    public String getContacts(String user) throws IOException {
	//FIXME baa by Djer Attention tu écrase le paramètre de la méthode !
	user="me";
	ListConnectionsResponse response = getService(user).people().connections()
		.list("people/me")
		.setPageSize(10)
		.setPersonFields("names,emailAddresses")
		.execute();
	// Print display name of connections if available.
	List<Person> connections = response.getConnections();
	String message = "Vous avez %%% contacts : <br>";
	Integer nbrContacts = 0;
	if (connections != null && connections.size() > 0) {
	    for (Person person : connections) {
		nbrContacts++;
		List<Name> names = person.getNames();
		if (names != null && names.size() > 0) {
		    message += "Nom: " + person.getNames().get(0).getDisplayName() + "<br> N°Tel : " + person.getPhoneNumbers() + "<br>" + "-----------------" + "<br>";
		}
	    }
	} else {
	    message = "Vous n'avez pas d'ami (:sad:)";
	}
	
	//TODO baa by Djer Gérer la pagination, sinon toujours MAX 10 contacts !!!
	message.replace("%%%", nbrContacts.toString());
	System.out.println(message.replace("<br>", "\n"));
	return message;
    }   
}