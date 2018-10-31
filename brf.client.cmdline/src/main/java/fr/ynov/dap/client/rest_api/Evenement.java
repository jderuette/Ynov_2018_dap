package fr.ynov.dap.client.rest_api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 
 * @author Florian BRANCHEREAU
 *
 */
public class Evenement extends Connexion_Rest_API {

	private static String chemin = "/calendarNextEvent?userKey=";
	
	/**
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 */
    public Evenement() throws URISyntaxException, IOException{
    super(chemin);
	}
    
    /**
     * 
     * @param userKey
     * @return Le prochain évènement
     * @throws URISyntaxException
     * @throws IOException
     */
    public String GetNextEvent(String userKey) throws URISyntaxException, IOException
    {
		setParam(userKey);
		String response = RecupInfo();
		return response;
    }
}
