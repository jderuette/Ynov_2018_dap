package fr.ynov.dap.google;

import java.io.IOException;

import java.security.GeneralSecurityException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.Config;

/**
 * The Class GMailService.
 */
@RestController
public class GMailService extends GoogleService{
	//FIXME cha by Djer Séparation Controller/Service ?
	//TODO cha by Djer une partie d'URL commune "/email" serait pratique.
	
	//FIXME cha by Djer Plus nécéssaire, Spring le fait pour toi. Deplus ton constructeur est public !!!
	/** The Constant INSTANCE. */
	private static final GMailService INSTANCE = new GMailService();
		
	/**
	 * Instantiates a new g mail service.
	 */
	public GMailService() {
		
	}
	
	/**
	 * Gets the single instance of GMailService.
	 *
	 * @return single instance of GMailService
	 */
	public static GMailService getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Gets the service.
	 *
	 * @return the service
	 * @throws GeneralSecurityException the general security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Gmail getService() throws GeneralSecurityException, IOException {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, GoogleService.getJsonFactory(), GoogleService.getCredentials(HTTP_TRANSPORT))
        		//TODO cha by Djer utiliser le principe "ZeroConf" plutot qu'une Conf "en dur"
        		.setApplicationName(Config.APPLICATION_NAME)
                .build();
        
        return service;
	}
	
	/**
	 * Gets the nb unread emails.
	 *
	 * @param user the user
	 * @return the nb unread emails
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	//FIXME cha by Djer Nb quoi (email ? contact ? event ?), précise l'URL ou utilise une partie commune avec un @RequestMapping sur la classe
	@RequestMapping(value="/nb")
	public String getNbUnreadEmails(String user) throws IOException, GeneralSecurityException {
    	
		Gmail service = getService();
		//FIXME cha by Djer Tu écrase la valeur passé en paramètre !! Vérifie les Warning PMD/CheckStyle !
        user = "me";       
        Label listResponse = service.users().labels().get(user, "INBOX").execute();
       
        int nMailCount = listResponse.getMessagesUnread();
       
        //TODO cha by Djer Sysout inutile, tu renvoie maintenant la valeur. A la limite un LOG.debug()....
        System.out.println("Mail count:");
      //TODO cha by Djer Sysout sur Server inutiles ! 
        System.out.printf("%s\n", nMailCount);
        //TODO cha by Djer renvoyer un nombre serait mieux (traitement métier), le format d'affichage est à décider par le client (au pire le controller)
        return "Mail count : " + nMailCount;
    }
	
	/**
	 * Test.
	 *
	 * @return the string
	 */
	@RequestMapping(value="/test")
	public String test() {
		return "Coucou"; //TODO cha by Djer Bonjour ! (code de "test" a supprimer après avoir tester)
	}
	
}
