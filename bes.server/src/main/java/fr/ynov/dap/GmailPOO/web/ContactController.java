package fr.ynov.dap.GmailPOO.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

import fr.ynov.dap.GmailPOO.metier.ContactsService;

@RestController
public class ContactController {
	@Autowired
	//TODO bes by Djer |POO| Pourquoi static ? 
	static ContactsService contactService;

@RequestMapping("/contact")
//TOD bes by Djer |POO| Evite de undersocre dans les noms de méthode (utilise le camelCase)
//TODO bes by Djer |POO| Poruquoi static ? 
public static String nombre_total_contacts() throws IOException, GeneralSecurityException {
	
    //TODO bes by Djer |API Google| Sans userKey ca ne peu pas fonctionenr corectement ! 
	List<Person> connections = contactService.connections("");
	System.out.println("le nombre total de contacts  "+connections.size());
	return "le nombre total de contacts  "+connections.size();
}

	@RequestMapping("/contactList")
	public String listcontacts() throws IOException, GeneralSecurityException {
		
		
		String contact="";
		//TODO bes by Djer |POO | NPE ici a cause du service "static", et ne PEU pas fonctionner sans userkey
		List<Person> connections = contactService.connections("");
		connections.size();
		if (connections != null && connections.size() > 0) {
			for (Person person : connections) {
				List<Name> names = person.getNames();
				if (names != null && names.size() > 0) {
				    //TODO bes by Djer |Log4J| Pas de Sysout sur un serveur. utilise une Log !
					System.out.println("Name: " + person.getNames().get(0).getDisplayName());
					//TODO bes by Djer |POO| Toutes tes "alimentation" de contact sont dans des bloc différents, donc pas besoin de concatener (et d'initialiser ) "").
					contact+="Name: " + person.getNames().get(0).getDisplayName()+"<br>";
				} else {
				    //TODO bes by Djer |Log4J| Pas de Sysout sur un serveur. utilise une Log !
					System.out.println("No names available for connection.");
					contact+="No names available for connection.<br>";
				}
			}
		} else {
		  //TODO bes by Djer |Log4J| Pas de Sysout sur un serveur. utilise une Log !
			System.out.println("No connections found.");
			contact+="No connections found.<br>";
		}
		
		
		return contact;
	}
}
