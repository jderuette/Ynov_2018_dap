package fr.ynov.dap.dap.Controller;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.dap.Service.GoogleService;
import fr.ynov.dap.dap.Service.PeopleGoogleService;

/**
 * 
 * @author Mon_PC
 * Class PeopleController
 * Manage every maps of PeopleGoogle
 */
@RestController
public class PeopleController extends GoogleService implements Callback {
	/**
	 * peopleService is managed by Spring on the loadConfig()
	 */
	@Autowired
	private PeopleGoogleService peopleService;
	/**
	 * Constructor PeopleController
	 * @throws Exception
	 * @throws IOException
	 */
	public PeopleController() throws Exception, IOException
	{
	}
	/**
	 * 
	 * @param userId
	 * userId put parameter
	 * @return numbers of contacts to this user
	 * @throws Exception
	 */
	@RequestMapping("/contact/{userId}")
    public int GetAllContacts(@PathVariable("userId") final String userId) throws Exception
    {
    	int nbContact = 0;
    	nbContact = peopleService.GetNbContact(userId);
    	return nbContact;
    }
}
