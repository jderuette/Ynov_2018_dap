package fr.ynov.dap.client;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import fr.ynov.dap.services.HttpRequestService;

/**
 * The Class App.
 */
public class App {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 * @throws URISyntaxException       the URI syntax exception
	 */
	public static void main(String[] args) throws IOException, GeneralSecurityException, URISyntaxException {
	    //TODO mot by Djer |POO| C'est une varaibled evrait être écrit en minuscule
	    String URl_PATH = "http://localhost:8080/";

		Scanner scannerConnexion = new Scanner(System.in);
		System.out.println("Veulliez saisir un nom d'utilisateur :  \n");
		String userKey = scannerConnexion.next();
		System.out.println("Veulliez saisir un nom de compte google :  \n");
		String accountName = scannerConnexion.next();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Menu Gmail Services \n");
		System.out.println("1) Se connecter \n");
		System.out.println("2) Récupéré le nombre de mails non lus \n");
		System.out.println("3) Récupéré le prochain évènement \n");
		System.out.println("4) Récupéré le nombre de contacts \n");
		System.out.println("Veulliez saisir le numéro de votre choix :");
		int choix = scanner.nextInt();

		lauchHttpRequest(choix, scanner, URl_PATH, userKey, accountName);

	}

	/**
	 * Lauch http request.
	 *
	 * @param choix       the choix
	 * @param scanner     the scanner
	 * @param URl_PATH    the u rl PATH
	 * @param userKey     the user key
	 * @param accountName the account name
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 * @throws URISyntaxException       the URI syntax exception
	 */
	private static void lauchHttpRequest(int choix, Scanner scanner, String URl_PATH, String userKey,
			String accountName) throws IOException, GeneralSecurityException, URISyntaxException {
		if (choix == 1) {
		    //TODO mot by Djer |API Microsoft| Gestion des comptes Microsoft ? 
			System.out.println("URL : " + URl_PATH + "add/googleAccount/" + accountName + "?userKey=" + userKey);
			String url = URl_PATH + "add/googleAccount/" + accountName + "?userKey=" + userKey;

			URI uri = new URI(url);
			Desktop.getDesktop().browse(uri);

			System.out.println("Vous venez de vous connecter au compte " + accountName + " avec le user " + userKey);
			System.out.println("Voulez vous effectuer une nouvelle action ? :");
			lauchHttpRequest(scanner.nextInt(), scanner, URl_PATH, userKey, accountName);

		} else if (choix == 2) {
			System.out.println("Votre choix est : " + choix);

			String result = new HttpRequestService()
					.getResult(URl_PATH + "/emails/nbrUnreadMailGoogle" + "?userKey=" + userKey);
			System.out.println(result);
			System.out.println("Voulez vous effectuer une nouvelle action ? :");
			lauchHttpRequest(scanner.nextInt(), scanner, URl_PATH, userKey, accountName);

		} else if (choix == 3) {
			System.out.println("Votre choix est : " + choix);
			String result = new HttpRequestService()
					.getResult(URl_PATH + "/calendar/nextEventGoogle" + "?userKey=" + userKey);
			System.out.println(result);
			System.out.println("Voulez vous effectuer une nouvelle action ? :");
			lauchHttpRequest(scanner.nextInt(), scanner, URl_PATH, userKey, accountName);

		} else if (choix == 4) {
			System.out.println("Votre choix est : " + choix);
			String result = new HttpRequestService()
					.getResult(URl_PATH + "/contact/nbContGoogle" + "?userKey=" + userKey);
			System.out.println("le nombre de contact est : " + result);
			System.out.println("Voulez vous effectuer une nouvelle action ? :");
			lauchHttpRequest(scanner.nextInt(), scanner, URl_PATH, userKey, accountName);
		} else {
			System.out.println("Le choix sélectionné n'existe pas, veuillez en saisir un nouveau : ");
			lauchHttpRequest(scanner.nextInt(), scanner, URl_PATH, userKey, accountName);
		}

	}
}
