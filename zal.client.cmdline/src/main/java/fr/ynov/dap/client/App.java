package fr.ynov.dap.client;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * The Class App.
 */
public class App 
{
	
	/** The url. */
    //TODO zal by Djer Quel "The", au moins quel catégorie. "www.google.com" est une "the url" ...
    //"BackenServer Url" serait plus parlant ? 
	private static URI url;
	
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main( String[] args ) {
    	if(args.length == 2){
    		try{
        		switch(args[0]){
	                case "add": addAccount(args[1]);
	                break;
	                case "mail": showAllMailInbox(args[1]);
	                break;
	                case "mail-unread": showMailInboxUnread(args[1]);
	                break;
	                case "calendar": showCalendarNextEvent(args[1]);
	                break;
	                case "contacts": showNumberOfContacts(args[1]);
	                break;
	                case "view": showAll(args[1]);
	                break;
	                default : System.out.println("Enter arguments");
	                break;
                }
        	}catch(Exception e){
        		System.out.println("\n usage: -jar client-0.0.1-SNAPSHOT.jar [options] [userId] \n"
        				+ " example: java -jar client-0.0.1-SNAPSHOT.jar \"contacts\" loic  \n");
        	}
    	}else {
    		System.out.println("\n  usage: -jar client-0.0.1-SNAPSHOT.jar [options] [userId]  \n"
    				+ " example: java -jar client-0.0.1-SNAPSHOT.jar \"contacts\" loic \n");
    	}
    }
    
    /**
     * Show all.
     *
     * @param userId the user id
     * @throws Exception the exception
     */
    private static void showAll(String userId) throws Exception {
    	showCalendarNextEvent(userId);
    	showNumberOfContacts(userId);
    	showAllMailInbox(userId);
    	showMailInboxUnread(userId);
	}

	/**
     * Show number of contacts.
     *
     * @param userId the user id
     * @throws Exception the exception
     */
    private static void showNumberOfContacts(String userId) throws Exception {
    	StringBuffer response = sendGet(Constant.getNumberOfContacts + userId);
    	System.out.println(response.toString());
	}

	/**
	 * Show calendar next event.
	 *
	 * @param userId the user id
	 * @throws Exception the exception
	 */
	private static void showCalendarNextEvent(String userId) throws Exception {
    	StringBuffer response = sendGet(Constant.getCalendarNextEvent + userId);
    	System.out.println(response.toString());
	}

	/**
	 * Adds the account.
	 *
	 * @param userId the user id
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void addAccount(String userId) throws IOException{
    	try {
			url = new URI(Constant.urlAddAccount + userId);
			Desktop.getDesktop().browse(url);
		} catch (URISyntaxException e) {
		    //TODO zal by Djer "e.printStackTrace()" affiche dans la console, ce qui n'est pas top pour un client ne ligne de commande.
		    // à minima afficher sur le flux d'erruer, sinon Logger permet de configurer.
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * Show all mail inbox.
     *
     * @param userId the user id
     * @throws Exception the exception
     */
    private static void showAllMailInbox(String userId) throws Exception {
    	StringBuffer response = sendGet(Constant.getAllMailInbox + userId);
    	System.out.println(response.toString());
    }
    
    /**
     * Show mail inbox unread.
     *
     * @param userId the user id
     * @throws Exception the exception
     */
    private static void showMailInboxUnread(String userId) throws Exception {
    	StringBuffer response = sendGet(Constant.getMailUnreadInbox + userId);
    	System.out.println(response.toString());
    }
    
	/**
	 * Send get.
	 *
	 * @param urlString the url string
	 * @return the string buffer
	 * @throws Exception the exception
	 */
	private static StringBuffer sendGet(String urlString) throws Exception {
		
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response;
	}
}
