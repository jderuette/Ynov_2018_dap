package fr.ynov.dap.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author  Robin Dudek
 *
 */
public class App 
{
	

    /**
     * Logger instance.
     */
    private static final Logger LOGGER = LogManager.getLogger();
	
	/**
	 * 
	 * @param args 2 arg sont attendu, le premier est le mot clé pour indiqué quelle url sera appelée, 
	 * le second est l'id du user sur lequel on exécute la fonction
	 * @throws MalformedURLException j'ai pas eu le temps de réparer mes logs
	 * @throws IOException j'ai pas eu le temps de réparer mes logs
	 */
    public static void main( String[] args ) throws MalformedURLException, IOException
    {

        if (args.length == 1) {

            String userId = args[0];

            showUserInformations(userId);

        } else if (args.length > 1) {

            String cmd = args[0].toLowerCase();
            String userId = args[1];

            switch (cmd) {
            case "view":
                showUserInformations(userId);
                break;
            case "add":
                if (args.length >= 2) {
                    String accountName = args[2];
                    addGoogleAccount(accountName, userId);
                } else {
                    showError();
                }
                break;
            case "create":
                createNewAccount(userId);
                break;
            default:
                showError();
            }

        } else {

            showError();

        }

    	URL url = null;
    	if(args[0].equals("mails")) {
    		url = new URL("http://localhost:8080/mails/unreads?userID=" + args[1]);

    	} else if (args[0].equals("addaccount")) {
    		url = new URL("http://localhost:8080/account/add/" + args[1]);
    	} else if (args[0].equals("nextevent")) {
    		url = new URL("http://localhost:8080/event/" + args[1]);
    	} else {
            url = new URL("http://localhost:8080/hello");
    	}
	
    	CallURL helper = new CallURL();

        System.out.println(CallURL.callGETURL(url));
    }
}
