package fr.ynov.dap_client.dap_client;
//TODO brc by Djer Pourquoi un nom de package aussi "moche" ?

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

/**
 * The Class App.
 */
public class App 
{
    
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws URISyntaxException the URI syntax exception
     */
    public static void main( String[] args ) throws IOException, URISyntaxException
    {         
        //TODO brc by Djer Extraire arg[0] dans "action" et args[1] dans "userKey" aurait clarifier le code
		if(args[0].equals("add")) {
            URI uri = new URI("http://localhost:8080/account/add/" + args[1]);
			Desktop.getDesktop().browse(uri);
		}
		else {
			if(args[0].equals("gmail") || args[0].equals("contact") || args[0].equals("calendar"))
				sendUrl(new URL("http://localhost:8080/"+args[0]+"?userId=" + args[1]));
			
			if(args[0].equals("view")) {
				sendUrl(new URL("http://localhost:8080/calendar?userId=" + args[1]));
				sendUrl(new URL("http://localhost:8080/contact?userId=" + args[1]));
				sendUrl(new URL("http://localhost:8080/gmail?userId=" + args[1]));
			}
		}
    }
    
    /**
     * Send url.
     *
     * @param url the url
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private static void sendUrl(URL url) throws IOException {
    	URLConnection connection = url.openConnection();
	    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
	    //FIXME brc by Djer U u n d v c !!! (traduction : Utilise un nom de varaible claire)
	    String i; 
			   
	    //print the source code line by line. 
	    while ((i = br.readLine()) != null)  
		{ 
	        //TODO brc by Djer Renvoyer la réponse et laisser l'appelant décider du mode d'affichage serait mieux.
			System.out.println(i); 
		} 
    }
}
