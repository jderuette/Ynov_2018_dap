package fr.ynov.dap.client;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

import fr.ynov.dap.client.Services.HttpRequestService;

/**
 * App
 * @author benjaminthomas
 */
public class App 
{	
    
    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main( String[] args )
    {    	   	
        Scanner scanner = new Scanner(System.in);
        System.out.print("1. Login google account \n");
        System.out.print("2. Check emails \n");
        System.out.print("3. Check contacts \n");
        System.out.print("4. Check calendar \n");
        System.out.print("You'r choice : ");
        
        Integer choice = scanner.nextInt();
        checkChoice(choice, scanner);
      }
    
	/**
	 * Check choice.
	 *
	 * @param choice the choice
	 * @param scanner the scanner
	 */
	private static void checkChoice(Integer choice, Scanner scanner) {
		switch (choice) {
	    case 1:
			try {
				System.out.print("Set email : ");
				String accountName = scanner.next();
				System.out.println("User key");
				String userKey = scanner.next();
				
				try {
					Desktop.getDesktop().browse(new URI("http://localhost:8080/account/add/google/" + accountName + "?userKey=" + userKey));
				} catch (URISyntaxException e) {
					
				}
				
				System.out.print("Another action : ");
				checkChoice(scanner.nextInt(), scanner);
			} catch (IOException e) {
				System.err.println("Connection error");
			}
	    	break;
	    case 2:
	    	try {
	    		System.out.println("User key");
				String userKey = scanner.next();
				new HttpRequestService("/mail/unread?userKey="+userKey);
				
				System.out.print("Another action : ");
				checkChoice(scanner.nextInt(), scanner);
			} catch (IOException e) {
				System.err.println("Connection error");
			}
	    	break;
	    case 3:
	    	try {
	    		System.out.println("User key");
				String userKey = scanner.next();
				new HttpRequestService("/contact/total?userKey="+userKey);
				
				System.out.print("Another action : ");
				checkChoice(scanner.nextInt(), scanner);
			} catch (IOException e) {
				System.err.println("Connection error");
			}
	    	break;
	    case 4:
	    	try {
	    		System.out.println("User key");
				String userKey = scanner.next();
				new HttpRequestService("/calendar/next?userKey="+userKey);
				
				System.out.print("Another action : ");
				checkChoice(scanner.nextInt(), scanner);
			} catch (IOException e) {
				System.err.println("Connection error");
			}
	    	break;
		default:
			break;
		}
	}
}
