
package fr.ynov.dap.client.rest_api;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * 
 * @author Florian BRANCHEREAU
 * Création d'un compte
 */
public class AjoutCompte extends Connexion_Rest_API {

	private static String chemin = "/account/add/";
	
	/**
	 * 
	 * @param action permet de recupérer l'action (add ou view) et l'ajouter dans le chemin
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public AjoutCompte() throws URISyntaxException, IOException {
		super(chemin);
	}
	    
	/**
	 * Récupération du nom et création du compte
	 * @param userKey
	 * @return un nouveau compte
	 * @throws URISyntaxException
	 * @throws IOException
	 */
    public String GetNouveauCompte(String userKey) throws URISyntaxException, IOException
    {
		setParam(userKey);
		String response = RecupInfo();
		return response;
    }
}