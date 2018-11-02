package fr.ynov.dap.consoleDaP;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @author  Robin Dudek
 *
 */
public class App 
{
	/**
	 * 
	 * @param args 2 arg sont attendu, le premier est le mot clé pour indiqué quelle url sera appelée, 
	 * le second est l'id du user sur lequel on exécute la fonction
	 * @throws MalformedURLException j'ai pas eu le temps de réparer mes logs
	 * @throws IOException j'ai pas eu le temps de réparer mes logs
	 */
    public static void main( String[] args ) throws MalformedURLException, IOException
    {
        //TODO Dur by Djer tu pourrais extraire agr[0] et [1] dans des variables
        //TODO dur by Djer tu pourrais valider les données entrées
    	URL url = null;
    	if(args[0].equals("mails")) {
    		url = new URL("http://localhost:8080/mails/unreads?userID=" + args[1]);
    		//TODO dur by Djer "==" sur des chaines de TEXTES ??? As-tu vérifié un minimum ?
    	} else if (args[0] == "addaccount") {
    		url = new URL("http://localhost:8080/account/add/" + args[1]);
    	} else if (args[0] == "nextevent") {
    		url = new URL("http://localhost:8080/event/" + args[1]);
    	} else if (args[0] == "log") {
    		url = new URL("http://localhost:8080/log/");
    	}
	
    	CallURL helper = new CallURL();
    	//TODO du by DJer Ton IDE t'indique d'appeler en mode static, ca serait gentil de traiter sa demande
        System.out.println(helper.callGETURL(url));
    }
}
