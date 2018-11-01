package fr.ynov.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

@RestController
//TODO jog by Djer séparation Controller/service
public class PeopleService extends GoogleService {

	private static PeopleService peopleInstance;
	   
		

	    public PeopleService() throws GeneralSecurityException, IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	    private com.google.api.services.people.v1.PeopleService getService(String userId) throws IOException, GeneralSecurityException {
		    com.google.api.services.people.v1.PeopleService service = new com.google.api.services.people.v1.PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY,
		                super.getCredentials(HTTP_TRANSPORT, userId)).setApplicationName(configuration.getApplication_name()).build();
		    return service;
		    }
	   
	  //TODO job by Djer Maintenant fait par Spring (Cf remarque CalendarService). Deplus ton consctructeur est toujour public !
	    public static PeopleService get() throws GeneralSecurityException, IOException {
			if(peopleInstance == null) {
				return peopleInstance = new PeopleService();
			}
			else {
				return peopleInstance;
			}
	    }
	    
	    /**
	     * Récupère les contacts de l'utilisateur
	     * @param user
	     * @return liste des utilisateurs
	     * @throws IOException
	     * @throws GeneralSecurityException
	     */
	    @RequestMapping("People")
		public String getPeople (String user) throws IOException, GeneralSecurityException {
	    	user = "me";
	    	String contacts = null;
	        // Request 10 connections.
	        ListConnectionsResponse response = getService(user).people().connections()
	                .list("people/me")
	                .setPageSize(10)
	                .setPersonFields("names,emailAddresses")
	                .execute();

	        // Print display name of connections if available.
	        List<Person> connections = response.getConnections();
	        if (connections != null && connections.size() > 0) {
	            for (Person person : connections) {
	                List<Name> names = person.getNames();
	                if (names != null && names.size() > 0) {
	                	
	                    contacts += "Name: " + person.getNames().get(0)
	                            .getDisplayName()+ "<br>";
	                } else {
	                	
	                    contacts = "No names available for connection.";
	                }
	            }
	        } else {
	            contacts = "No connections found.";
	        }
	        
	      //TODO jog by Djer PAS de Sysout sur un serveur ! (à la limite un LOG)
	        System.out.println(contacts.replace("<br>","\n"));
	        //TODO job by Djer Il était demandé d'afficher le nombre, pas le détail.
			return contacts;
	        
	    }
	}

