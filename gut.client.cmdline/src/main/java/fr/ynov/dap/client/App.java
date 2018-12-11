package fr.ynov.dap.client;

import java.io.IOException;
//TODO gut by Djer |IDE| (ancien TO-DO) Configuer les "save actions" de ton IDE. CF mémo Elcipse.
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Scanner;

/**
 * Application Client pour fr.ynov.dap
 *
 */
public class App 
{
	
	/**
	 * Récupère le pseudo utilisateur puis affiche les différentes données récupérées de l'api rest fr.ynov.dap
	 * @param args
	 * @throws MalformedURLException
	 * @throws IOException
	 */
    public static void main( String[] args ) throws MalformedURLException, IOException
    {
    	
        
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Entrer votre login:");
        String userId = reader.next();
        
        
        
        //once finished
        reader.close();
        
        DapService myService = new DapService(userId);
        
        myService.getUnreadMailsCount();
        myService.getContactsCount();
        myService.getEvents();
        myService.getGmailLabels();
        
        //TODO gut by Djer |API Google| Ajout de comtpe Google ? 
      //TODO gut by Djer |API Microsoft| Ajout de comtpe Microsoft ? 
    }
    
    
    
    
    
    
    
    
    
    
}
