package fr.ynov.dap.dap.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.stereotype.Service;
import com.google.api.services.people.v1.PeopleService;

/**
 * 
 * @author Mon_PC
 * Manage the redirection to the Controller
 * Extends MainService
 */
@Service
//TODO bog by Djer CF remarques de brf (FLorian) car même code
//FIXME bog by Djer ce code faisait partie du devoir et de vait donc etre INDIVIDUEL
public class PeopleGoogleService extends GoogleService {
	/**
	 * Constructor PeopleGoogleService
	 * @throws Exception
	 * @throws IOException
	 */
	public PeopleGoogleService() throws Exception, IOException
	{
		
	}
	/**
	 * 
	 * @return PeopleService
	 * @param userId
	 * userId parameter put by client
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 */
	public PeopleService GetServicePeople(String userId) throws IOException, GeneralSecurityException
	{
		return BuildPeopleService(userId);
	}
	/**
	 * 
	 * @return PeopleService
	 * @param userId
	 * userId parameter put by client
	 * @throws IOException
	 * @throws GeneralSecurityException 
	 */
	private PeopleService BuildPeopleService(String userId) throws IOException, GeneralSecurityException
	{
		PeopleService service = new PeopleService.Builder(GetHttpTransport(), GetJsonFactory(), getCredentials(userId))
	            .setApplicationName(configuration.getApplicationName())
	            .build();
		return service;
	}
	/**
	 * 
	 * @param userId
	 * userId parameter put by client
	 * @return int numbers of contact of this user
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public int GetNbContact(String userId) throws IOException, GeneralSecurityException
	{
		int nbContact = 0;
		try
		{
			nbContact = this.GetServicePeople(userId).people()
			.connections()
			.list("people/me")
			.setPersonFields("names,emailAddresses")
			.execute()
			.getTotalItems();
		}
		catch(Exception e)
		{
		    //TODO bog by Djer L'excception ne veux pas du tout dire "pas de conenxion"
		    // Ton message dans les logs est faux !
		    //TODO bog by Djer Pour logger une exception, ajoute la cause en deuxième paramètre
			LOG.error("Vous n'avez aucun contact attribué à cette adresse mail !");
		}
		return nbContact;		
	}
}