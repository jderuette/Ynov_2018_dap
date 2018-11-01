package fr.ynov.dap.dap.Controller;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.dap.Service.GmailService;
import fr.ynov.dap.dap.Service.GoogleService;


/**
 * 
 * @author Gaël BOSSER
 * Class GmailController
 * Manage every maps of Gmail
 */
@RestController
public class GmailController extends GoogleService implements Callback {
	
	/**
	 * gmailService is managed by Spring on the loadConfig()
	 */
	@Autowired
	private GmailService gmailService;
	
	/**
	 * Constructor of GmailController
	 * @throws Exception
	 * @throws IOException
	 */
	public GmailController() throws Exception, IOException
	{
	}
	/**
	 * 
	 * @param userId
	 * userId put parameter
	 * @return Int MessagesUnreaded
	 * @throws Exception
	 */
	@RequestMapping("/gmail/email/{userId}")
    public int EmailsUnreads(@PathVariable("userId") final String userId) throws Exception
    {
    	return gmailService.getMsgsUnread(userId);
    }
}