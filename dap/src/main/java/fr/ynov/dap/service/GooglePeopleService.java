package fr.ynov.dap.service;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;
@Service
/**
 * Permet de récupérer les infos relatives aux contacts google.
 * @author abaracas
 *
 */
public class GooglePeopleService extends GoogleService {

  //TODO baa by Djer |Log4J| Devrait être final (la (pseudo) référence ne sera pas modifiée)
    private static Logger LOG = LogManager.getLogger();
   /**
     * Constructeur  de la classe
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     */
    private GooglePeopleService() throws GeneralSecurityException, IOException {
	super();
    }
    /**
     * Récupère le service
     * @param userKey identifiant applicatif
     * @return le service 
     * @throws IOException  exception
     * @throws GeneralSecurityException exception
     */
    public  com.google.api.services.people.v1.PeopleService getService(String userKey) throws IOException, GeneralSecurityException {
	com.google.api.services.people.v1.PeopleService service = new com.google.api.services.people.v1.PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY,
		super.getCredentials(userKey)).setApplicationName(maConfig.getApplicationName()).build();
	return service;
    }

    /**
     * Récupère les contacts, leur nombre, nom, tel
     * @param userKey utilisateur applicatif
     * @param gUser google utilisateur
     * @return les valeurs voulues
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    //TODO baa by Djer |POO| Tu n'utilise pas la variable "gUser". D'après ta javadoc, c'est celui qui semble le plus correspondre à un "accountName" tu devrais donc l'utiliser à la place du "userKey"
    //TODO baa by Djer |Spring| TU n'est plus dans un controller, l'anotation @PathVariable n'est plus utile
    public String getContacts(String userKey, @PathVariable final String gUser) throws IOException, GeneralSecurityException {
	ListConnectionsResponse response = getService(userKey).people().connections()
		.list("people/me")
		.setPageSize(100)
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
	message.replace("%%%", nbrContacts.toString());
	System.out.println(message.replace("<br>", "\n"));
	LOG.info(userKey + " a " + message );
	//TODO baa by Djer |MVC| Ne formate pas les message dans le service, ca n'est pas de sa responsabilité
	return message;
    }   
}