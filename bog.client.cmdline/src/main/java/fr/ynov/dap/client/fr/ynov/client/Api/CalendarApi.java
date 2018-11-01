package fr.ynov.dap.client.fr.ynov.client.Api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 
 * @author Mon_PC
 * CalendarApi class get all Calendar's fonctions
 */
public class CalendarApi extends BaseApi {
	/**
	 * default chemin of the CalendarApi
	 * To change this, use SetChemin from BaseApi 
	 */
	private static String chemin = "/calendar/event/";
	
	/**
	 * Constructor of CalendarApi and send attributs "chemin" to BaseApi
	 * @throws URISyntaxException
	 * @throws IOException
	 */
    public CalendarApi() throws URISyntaxException, IOException{
    super(chemin);
	}
    /**
     * 
     * @param userKey
     * userKey param
     * @return String GetNextEvent of the user
     * @throws URISyntaxException
     * @throws IOException
     */
    public String GetNextEvent(String userKey) throws URISyntaxException, IOException
    {
		setParam(userKey);
		return GetResponseBody();
    }
}
