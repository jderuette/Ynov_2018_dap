package fr.ynov.dap.client.fr.ynov.client.Api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 
 * @author Mon_PC
 * PeopleApi class get all People's fonctions
 */
public class PeopleApi extends BaseApi{
	/**
	 * default chemin of the PeopleApi
	 * To change this, use SetChemin from BaseApi 
	 */
	private static String chemin = "/contact/";
	
	/**
	 * Constructor of PeopleApi and send attributs "chemin" to BaseApi
	 * @throws URISyntaxException
	 * @throws IOException
	 */
    public PeopleApi() throws URISyntaxException, IOException{
    super(chemin);
	}
    
    /**
     * 
     * @param userKey
     * userKey param
     * @return String Number of contacts of the user
     * @throws URISyntaxException
     * @throws IOException
     */
    public String GetNbContacts(String userKey) throws URISyntaxException, IOException
    {
		setParam(userKey);
		return GetResponseBody();
    }
}
