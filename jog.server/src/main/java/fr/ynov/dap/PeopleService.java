package fr.ynov.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

@Service
public class PeopleService extends GoogleService {

	public PeopleService() throws GeneralSecurityException, IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	private com.google.api.services.people.v1.PeopleService getService(String userId)
			throws IOException, GeneralSecurityException {
		com.google.api.services.people.v1.PeopleService service = new com.google.api.services.people.v1.PeopleService.Builder(
				http_transport, JSON_FACTORY, super.getCredentials(http_transport, userId))
						.setApplicationName(configuration.getApplicationName()).build();
		return service;
	}

	/**
	 * Récupère les contacts de l'utilisateur
	 * 
	 * @param user
	 * @return liste des utilisateurs
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public String getPeople(String userKey, String gUser) throws IOException, GeneralSecurityException {

		String contacts = null;
		// Request 10 connections.
		ListConnectionsResponse response = getService(userKey).people().connections().list("people/" + gUser)
				.setPageSize(10).setPersonFields("names,emailAddresses").execute();

		// Print display name of connections if available.
		List<Person> connections = response.getConnections();
		if (connections != null && connections.size() > 0) {
			for (Person person : connections) {
				List<Name> names = person.getNames();
				if (names != null && names.size() > 0) {

					contacts += "Name: " + person.getNames().get(0).getDisplayName() + "<br>";
				} else {

					contacts = "No names available for connection.";
				}
			}
		} else {
			contacts = "No connections found.";
		}
		System.out.println(contacts.replace("<br>", "\n"));
		return contacts;

	}
}
