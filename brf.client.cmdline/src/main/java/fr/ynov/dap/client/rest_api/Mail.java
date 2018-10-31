package fr.ynov.dap.client.rest_api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 
 * @author Florian BRANCHEREAU
 *
 */
public class Mail extends Connexion_Rest_API
{
	private static String chemin = "/emailNonLu?userKey=";
	
	/**
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 */
    public Mail() throws URISyntaxException, IOException{
    super(chemin);
	}

    /**
     * 
     * @param userKey
     * @return Nombre de message non lu
     * @throws URISyntaxException
     * @throws IOException
     */
	public String GetEmailNonLus(String userKey) throws URISyntaxException, IOException
    {
		setParam(userKey);
		String response = RecupInfo();
    	return response;
    }
}