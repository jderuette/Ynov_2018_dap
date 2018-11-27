package com.ynov.client;

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
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String[] args)
    {        
        checkArgs(args);
    }
    
    private static void usageExplain() {
		System.out.println("USAGE :");
		System.out.println("add [google|microsoft] <userId>");
		System.out.println("view <userId>");
		System.out.println("email <userId>");
		System.out.println("calendar <userId>");
		System.out.println("contact <userId>");
    }
    
    /**
     * Check args.
     *
     * @param args the args
     */
    public static void checkArgs(String[] args) {
    	if (args == null || args.length == 0) {
    		System.out.println("MISSING PARAMS");
    	}
    	
    	if (args.length > 2) {
    		System.out.println("TOO MUCH PARAMS");
    		usageExplain();
    	}
        
        switch (args[0]) {
	        case "add":
	        	String url = "";
	        	
	        	switch (args[1]) {
	        		case "google":
	        			url = "http://localhost:8080/account/add/google/" + args[2];
	        			break;
	        		case "microsoft":
	        			url = "http://localhost:8080/account/add/microsoft/" + args[2];
	        			break;
		        	default:
			    		usageExplain();
			        	break;
	        	}
	        	
	        	URI myURI;
				try {
					myURI = new URI(url);
					Desktop.getDesktop().browse(myURI);

				} catch (URISyntaxException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	            break;
	        case "email":
	        	System.out.println(getRequest("http://localhost:8080/mail/unread/final/" + args[1]));
	            break;
	        case "calendar":
	        	System.out.println(getRequest("http://localhost:8080/calendar/" + args[1]));
	            break;
	        case "contact":
	        	System.out.println(getRequest("http://localhost:8080/contact/nb/" + args[1]));
	            break;
	        case "view":
	        	System.out.println(getRequest("http://localhost:8080/mail/unread/final/" + args[1]));
	        	System.out.println(getRequest("http://localhost:8080/calendar/" + args[1]));
	        	System.out.println(getRequest("http://localhost:8080/contact/nb/" + args[1]));
	        	break;
	        default:
	    		usageExplain();
	        	break;
        }
    }
    
	/**
	 * Gets the request.
	 *
	 * @param queryString the query string
	 * @return the request
	 */
	public static String getRequest(String queryString) {
		 try {
           URL url = new URL(queryString);
           HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
           try {
               BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
               StringBuilder stringBuilder = new StringBuilder();
               String line;
               while ((line = bufferedReader.readLine()) != null) {
                   stringBuilder.append(line).append("\n");
               }
               bufferedReader.close();
               return stringBuilder.toString();
           } finally {
               urlConnection.disconnect();
           }
       } catch (Exception e) {
           System.out.println("ERROR Request : " + e.getMessage());
           return null;
       }
	}
    
}
