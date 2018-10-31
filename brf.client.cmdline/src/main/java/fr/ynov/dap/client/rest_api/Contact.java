package fr.ynov.dap.client.rest_api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 
 * @author Florian BRANCHEREAU
 *
 */
public class Contact extends Connexion_Rest_API {
	
	private static String chemin = "/contact?userKey=";
	
	/**
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 */
    public Contact() throws URISyntaxException, IOException{
    super(chemin);
	}
    
    /**
     * 
     * @param userKey
     * @return Le nombre de contact
     * @throws URISyntaxException
     * @throws IOException
     */
    public String GetNbContacts(String userKey) throws URISyntaxException, IOException
    {
		setParam(userKey);
		String response = RecupInfo();
		return response;
    }
}