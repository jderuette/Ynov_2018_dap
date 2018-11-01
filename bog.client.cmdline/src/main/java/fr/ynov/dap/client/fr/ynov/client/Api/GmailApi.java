package fr.ynov.dap.client.fr.ynov.client.Api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 
 * @author Mon_PC
 * GmailApi class get all Gmail's fonctions
 */
public class GmailApi extends BaseApi
{
	/**
	 * default chemin of the GmailApi
	 * To change this, use SetChemin from BaseApi 
	 */
	private static String chemin = "/gmail/email/";
	
	/**
	 * Constructor of GmailApi and send attributs "chemin" to BaseApi
	 * @throws URISyntaxException
	 * @throws IOException
	 */
    public GmailApi() throws URISyntaxException, IOException{
    super(chemin);
	}
    /**
     * 
     * @param  userKey
     * userKey param
     * @return String GetUnreadMails of the user
     * @throws URISyntaxException
     * @throws IOException
     */
	public String GetEmailNonLus(String userKey) throws URISyntaxException, IOException
    {
		setParam(userKey);
    	return GetResponseBody();
    }
}