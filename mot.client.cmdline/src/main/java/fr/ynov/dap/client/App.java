package fr.ynov.dap.client;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import services.HttpRequestService;

/**
 * TODO mot by Djer Hello Pluton ! 
 * Hello world!.
 */
public class App 
{
	
	
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    public static void main( String[] args ) throws IOException, GeneralSecurityException
    {
    	String URl_PATH = "http://localhost:8080/";
    	
    	
    	Scanner scanner = new Scanner(System.in);
    	System.out.println("Menu Gmail Services \n");
    	System.out.println("1) Se connecter \n");
    	System.out.println("2) Récupéré le nombre de mails non lus \n");
    	System.out.println("3) Récupéré le prochain évènement \n");
    	System.out.println("4) Récupéré le nombre de contacts \n");
    	System.out.println("Veulliez saisir le numéro de votre choix :");
    	int choix = scanner.nextInt();
    	
    	lauchHttpRequest(choix, scanner, URl_PATH);
    
    }

	/**
	 * Lauch http request.
	 *
	 * @param choix the choix
	 * @param scanner the scanner
	 * @param URl_PATH the u rl PATH
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	private static void lauchHttpRequest(int choix, Scanner scanner, String URl_PATH) throws IOException, GeneralSecurityException {
		//TODO mot by Djer Ce todo ne semble plus d'actualité ! 
	    // TODO Auto-generated method stub
		if(choix == 1) {
    		System.out.println("Votre choix est : " + choix);
        	System.out.println("Veulliez saisir votre adresse mail :  \n");
        	String userMail = scanner.next();
        	
    		new HttpRequestService().getResult(URl_PATH + "account/add/" + userMail);
    		System.out.println("Voulez vous effectuer une nouvelle action ? :");
    		lauchHttpRequest(scanner.nextInt(), scanner, URl_PATH);
    		
    	}else if(choix == 2){
    		System.out.println("Votre choix est : " + choix);
        	System.out.println("Veulliez saisir votre adresse mail :  \n");
        	String userMail = scanner.next();
        	
    		String result = new HttpRequestService().getResult(URl_PATH + "getEmails/nbrunreadmail/" + userMail);
    		System.out.println(result);
    		lauchHttpRequest(scanner.nextInt(), scanner, URl_PATH);
    		
    	}else if(choix == 3) {
    		System.out.println("Votre choix est : " + choix);
        	System.out.println("Veulliez saisir votre adresse mail :  \n");
        	String userMail = scanner.next();
        	
    		String result = new HttpRequestService().getResult(URl_PATH + "getCalendar/getLastEvent/" + userMail);
    		System.out.println(result);
    		lauchHttpRequest(scanner.nextInt(), scanner, URl_PATH);
    		
    	}else if(choix == 4) {
    		System.out.println("Votre choix est : " + choix);
        	System.out.println("Veulliez saisir votre adresse mail :  \n");
        	String userMail = scanner.next();
        	
    		String result = new HttpRequestService().getResult(URl_PATH + "getContact/getNbCont/" + userMail);
    		System.out.println(result);
    		lauchHttpRequest(scanner.nextInt(), scanner, URl_PATH);
    	}else {
    		System.out.println("Le choix sélectionné n'existe pas, veuillez en saisir un nouveau : ");
    		lauchHttpRequest(scanner.nextInt(), scanner, URl_PATH);
    	}
		
	}
}
