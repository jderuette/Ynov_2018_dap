package fr.ynov.dap.GoogleMaven;

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactService extends GoogleService {

	//private static Config maConfig = getConfiguration();
	private static ContactService instance;
	//TODO elj by Djer Ne force pas à une category vide, Laisse Log4Jfaire, ou utilise le nom, qualifié, de la classe
	private static final Logger logger = LogManager.getLogger("");

	/**
	 * @return the instance
	 */
	//TODO elj by Djer Plus utile Spring le fait pour toi
	public static ContactService getInstance() {
		if (instance == null) {
			instance = new ContactService();
		}
		return instance;
	}


	/**
	 * @param instance the instance to set
	 */
	//TODO elj by Djer Pourquoi un "set" de l'instance ? 
	public static void setInstance(ContactService instance) {
		ContactService.instance = instance;
	}
	@RequestMapping("/NombreDeContact")
	//TODO elj by Djer Pourquoi static ?
	public static void GetContact() throws IOException, GeneralSecurityException {
		try{
			PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
			.setApplicationName(configuration.getApplicationName())
			.build();
			//System.out.println(service.people().connections().list("").size());
			// Request 10 connections.
			ListConnectionsResponse response = service.people().connections()
					.list("people/me")
					.setPageSize(1000)
					.setPersonFields("names,emailAddresses")
					.execute();
			int i=0;
			// Print display name of connections if available.
			List<Person> connections = response.getConnections();
			if (connections != null && connections.size() > 0) {
				for (Person person : connections) {
					List<Name> names = person.getNames();
					if (names != null && names.size() > 0) {
						
						i++;
					} else {
					  //TODO elj by Djer Pas de sysout sur un Server ! Une LOG si besoin.
						System.out.println("No names available for connection.");
					}
				}
			} else {
			  //TODO elj by Djer Pas de sysout sur un Server ! Une LOG si besoin.
				System.out.println("No connections found.");
			}
			//TODO elj by Djer Pas de sysout sur un Server ! Une LOG si besoin.
			System.out.println("Vous avez "+i+" contact(s)");

		} catch (IOException e) {
            //TODO elj by Djer Evite de logger juste le message de l'exxeption. En premie paramètre met ton propre message.
            // Puis utilise le secon, pour indiquer la cause.
            //TODO elj by Djer Pourquoi info ?
			logger.info(e.getMessage());
		}
		//TODO elj by Djer Il état demandé de renvoyer le nombre de contatcs, pas le détails.
	}
}
