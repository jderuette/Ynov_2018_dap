package fr.ynov.dap.GmailPOO.metier;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.PeopleServiceScopes;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactsService extends GoogleService {
	public ContactsService() throws UnsupportedOperationException, GeneralSecurityException, IOException {
		super();
		// TODO Auto-generated constructor stub
	}
	 //TODO bes by Djer |POO|  Dans un service (ou un controller) qui sont pas défaut des Singleton, on évite d'avoir des variables d'instances.
	PeopleService service;
	//TODO bes by Djer |POO| n'est plus utilisé, mais comme "sans modifier" il hérite du modifier "public" de la classe et ton IDE ne peut pas te le signaler.
	ListConnectionsResponse response;



public List<Person> connections(String userKey) throws IOException, GeneralSecurityException {
    //TODO bes by Djer |POO| utilise une varaible lcoa ici. Tu est OBLIGE de re récupére le service à chaque apel, car le service est **dépendant** du userKey.
    // Tu pourrais éventuellement avoir un "caceh" par userKey, mais les caches/optimisations sont des sujets à traiter lorsqu'on rencontre un probleme de perf.
	 service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT,userKey))
			.setApplicationName(configuration.getApplicationName()).build();
	
	ListConnectionsResponse response = service.people().connections().list("people/me").setPageSize(1000)
			.setPersonFields("names,emailAddresses").execute();
	return  response.getConnections();
}

}
