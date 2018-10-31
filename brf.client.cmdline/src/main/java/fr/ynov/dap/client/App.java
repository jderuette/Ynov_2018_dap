package fr.ynov.dap.client;

import java.io.IOException;
import java.net.URISyntaxException;
import fr.ynov.dap.client.rest_api.Evenement;
import fr.ynov.dap.client.rest_api.Mail;
import fr.ynov.dap.client.rest_api.AjoutCompte;
import fr.ynov.dap.client.rest_api.Contact;

/**
 * 
 * @author Florian BRANCHEREAU
 * Class App contient la fonction "main" qui permet de récupérer les argument et d'afficher les infos
 */
public class App 
{
	private static Mail mail;
	private static Evenement evenement;
	private static Contact contact;
	private static AjoutCompte nouveaucompte;
	private static String responseMail;
	private static String responseEvenement;
	private static String responseContact;
	
	/**
	 * Récupération des Arguments, connexion a l'URI, récupération des informations puis affichage
	 * @param args
	 * @throws IOException
	 * @throws URISyntaxException
	 */
    public static void main( String[] args ) throws IOException, URISyntaxException
    {
    	mail = new Mail();
    	evenement = new Evenement();
    	contact = new Contact();
    	nouveaucompte = new AjoutCompte();
  	
    	String action = "";
    	String userKey = "";
    	boolean ActionValide = false;
    	
    	switch (args.length) {
		case 1: 
    		userKey = args[0];
    		ActionValide = true;
			break;
		case 2: 
    		action = args[0];
    		userKey = args[1];
    		ActionValide = true;
			break;
		default:
			break;
		}
    	
    	if(ActionValide)
    	{
    		if (action.toLowerCase().equals("") || action.toLowerCase().equals("view")) {
    			responseMail = mail.GetEmailNonLus(userKey);
        		System.out.println("Nombre de mails non lus : " + responseMail);
        		responseEvenement = evenement.GetNextEvent(userKey);
            	System.out.println(responseEvenement);
            	responseContact = contact.GetNbContacts(userKey);
            	System.out.println("Vous avez : " + responseContact + " contacts pour ce compte");
    		}
        	else {
        		if(action.toLowerCase().equals("add"))
        		{
        			nouveaucompte.GetNouveauCompte(userKey);
        			System.out.println("Le compte : " + userKey + " a été créer");
        		} else {
        			System.err.println("L'action renseigné est incorrect");
				}
			}
    	}
    }
}
