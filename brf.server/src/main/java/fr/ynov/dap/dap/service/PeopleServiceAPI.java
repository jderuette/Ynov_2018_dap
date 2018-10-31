package fr.ynov.dap.dap.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.stereotype.Service;
import com.google.api.services.people.v1.PeopleService;

/**
 * 
 * @author Florian BRANCHEREAU
 *
 */
@Service
public class PeopleServiceAPI extends GoogleService {
	
	public PeopleServiceAPI() throws Exception, IOException
	{
		
	}
	/**
	 * 
	 * @return BuildPeopleService
	 * @throws IOException 
	 * @throws GeneralSecurityException 
	 */
	public PeopleService GetServicePeople(String userKey) throws IOException, GeneralSecurityException
	{
		return BuildPeopleService(userKey);
	}
	/**
	 * 
	 * @return peopleservice
	 * @throws IOException
	 * @throws GeneralSecurityException 
	 */
	private PeopleService BuildPeopleService(String userKey) throws IOException, GeneralSecurityException
	{
		PeopleService peopleservice = new PeopleService.Builder(GetHttpTransport(), GetJsonFactory(), getCredentials(userKey))
	            .setApplicationName(configuration.getApplicationName())
	            .build();
		return peopleservice;
	}
	
	/**
	 * 
	 * @param userKey
	 * @return GetServicePeople
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public int GetNbContact(String userKey) throws IOException, GeneralSecurityException
	{	
		return this.GetServicePeople(userKey)
				.people()
				.connections()
				.list("people/me")
				.setPersonFields("nicknames")
				.execute()
				.getTotalItems();
	}
}
