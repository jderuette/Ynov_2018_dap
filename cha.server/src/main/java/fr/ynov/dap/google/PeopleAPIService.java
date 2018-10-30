package fr.ynov.dap.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.people.v1.PeopleService;
import com.google.api.services.people.v1.model.ListConnectionsResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Class PeopleAPIService.
 */
@RestController
public class PeopleAPIService extends GoogleService {
	
    /** The Constant APPLICATION_NAME. */
    private static final String APPLICATION_NAME = "Google People API Java Quickstart";
    
    /** The Constant JSON_FACTORY. */
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    
    /**
     * Gets the service.
     *
     * @return the service
     * @throws GeneralSecurityException the general security exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public PeopleService getService() throws GeneralSecurityException, IOException {
    	// Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        //FIXME cha by Djer si tu étends Google Service, pourquoi appeler en static ?
        PeopleService service = new PeopleService.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleService.getCredentials(HTTP_TRANSPORT))
                //FIXME cha by Djer Utiliser la Config ?
        		.setApplicationName(APPLICATION_NAME)
                .build();
		return service;
	}

    /**
     * Gets the total people.
     *
     * @return the total people
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @RequestMapping(value="/people")
    public int getTotalPeople() throws IOException, GeneralSecurityException {
        
    	PeopleService service = getService();
    	
        // Request 10 connections.
        ListConnectionsResponse response = service.people().connections()
                .list("people/me")
                .setPageSize(10)
                .setPersonFields("names,emailAddresses")
                .execute();
       
        //FIXME cha by Djer PageSize à et pas de gestion de la pagination, donc MAX 10 !
       int totalPeople = response.getTotalPeople(); 
       
       return totalPeople;
    }
}