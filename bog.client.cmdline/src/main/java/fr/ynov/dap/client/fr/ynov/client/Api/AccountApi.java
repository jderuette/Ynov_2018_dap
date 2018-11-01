package fr.ynov.dap.client.fr.ynov.client.Api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 
 * @author Mon_PC
 * AccountApi class get all Account's fonctions
 */
public class AccountApi extends BaseApi {
	/**
	 * default chemin of the AccountApi
	 * To change this, use SetChemin from BaseApi 
	 */
	private static String chemin = "/account/add/";
	
	/**
	 * Constructor of AccountApi and send attributs "chemin" to BaseApi
	 * @throws URISyntaxException
	 * @throws IOException
	 */
    public AccountApi() throws URISyntaxException, IOException{
    super(chemin);
	}
    /**
     * 
     * @param userKey
     * userKey param
     * @return String StatusResponse of the query (null or error 500)
     * if statusresponse is null, the account can be added. Else problem with this user.
     * @throws URISyntaxException
     * @throws IOException
     */
    public String CreateAccount(String userKey) throws URISyntaxException, IOException
    {
		setParam(userKey);
		OpenBrowser();
		return GetResponseBody();
    }
}