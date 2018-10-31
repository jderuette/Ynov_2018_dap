package fr.ynov.dap.dap.controller;

import java.io.IOException;

import javax.security.auth.callback.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.service.GmailService;
import fr.ynov.dap.dap.service.GoogleService;


/**
 * 
 * @author Florian BRANCHEREAU
 *
 */
@RestController
public class GmailController extends GoogleService implements Callback {

	@Autowired
	private GmailService gmailservice;
	
	public GmailController() throws Exception, IOException
	{
	    //TODO brf by Djer Appeller Super ?
	}
	
	/**
	 * 
	 * @param userKey
	 * @return Le nombre de mail non lu
	 * @throws Exception
	 */
	@RequestMapping("/emailNonLu")
    public String EmailUnreads(@RequestParam("userKey") final String userKey) throws Exception
    {
	    //TODO brf by Djer Evite les underscore dans les noms de varaibles ! 
    	int message_unread = 0;
    	message_unread = gmailservice.getMsgsUnread(userKey);
    	String response = "Nombre de mails non lus : " + message_unread;
    	return response;
    }
	
}
