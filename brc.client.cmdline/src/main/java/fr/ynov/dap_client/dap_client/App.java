package fr.ynov.dap_client.dap_client;

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
    	String action = args[0];
    	String type = args[1];
    	String userKey = args[2];
    	

		if(action.equals("add")) {
			URI uri = new URI("");

			switch(type) {
				case "user":
					uri = new URI("http://localhost:8080/user/add?userKey=" + userKey);
					Desktop.getDesktop().browse(uri);
					break;
				
				case "microsoft":
					String msAccountName = args[3];
					uri = new URI("http://localhost:8080/microsoft/account/add/" + msAccountName + "?userKey=" + userKey);
					Desktop.getDesktop().browse(uri);
					break;
					
				case "google":
					String googleAccountName = args[3];
					uri = new URI("http://localhost:8080/google/account/add/" + googleAccountName + "?userKey=" + userKey);
					Desktop.getDesktop().browse(uri);
					break;
			}
		}
		else if(action.equals("mail")){
			switch(type) {
				case "all":
					sendUrl(new URL("http://localhost:8080/mail/nbUnread?userKey=" + userKey));
					break;
				
				case "microsoft":
					sendUrl(new URL("http://localhost:8080/microsoft/nbUnreadMails?userKey=" + userKey));
					break;
					
				case "google":
					sendUrl(new URL("http://localhost:8080/google/nbUnreadMails?userKey=" + userKey));
					break;
			}
		}
		else if(action.equals("event")){
			switch(type) {
				case "google":
					sendUrl(new URL("http://localhost:8080/google/calendar?userKey=" + userKey));
					break;
				
				case "all":
					sendUrl(new URL("http://localhost:8080/RestNextEvent?userKey=" + userKey));
					break;
			}	
		}
		else if(action.equals("contact")) {
			switch(type) {
				case "google":
					sendUrl(new URL("http://localhost:8080/google/nbContact?userKey=" + userKey));
					break;
				
				case "microsoft":
					sendUrl(new URL("http://localhost:8080/microsoft/nbContact?userKey=" + userKey));
					break;
					
				case "all":
					sendUrl(new URL("http://localhost:8080/nbContact?userKey=" + userKey));
					break;
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
	    String response; 
			   
	    //print the source code line by line. 
	    while ((response = br.readLine()) != null)  
		{ 
			System.out.println(response); 
		} 
    }
}
