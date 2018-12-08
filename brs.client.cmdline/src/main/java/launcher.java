//TODO brs by Djer |POO| Evite de lassier des classe dnas le "package par defaut" (sans package)
import service.HttpBuilderService;

import java.util.Scanner;

/**
 * The Class launcher.
 */
public class launcher {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String... args) throws Exception {

	    //TODO brs by Djer |IDE| Ton IDE te dit que ca n'est pas utilsé. Bug ? A supprimer ? 
		String user = "";
		while (true) {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			System.out.println("\nMenu Options\n");
			System.out.println("(1) - Gmail");
			System.out.println("(2) - Calendar");
			System.out.println("(3) - Contact");
			System.out.println("(4) - Add Microsoft account");
			System.out.println("(5) - Add Google account");

			System.out.print("Please enter your selection:\t");
			int selection = scanner.nextInt();

		

			if (selection == 1) {
				System.out.println("User Key = ");
				String userKey = scanner.next();
				String url = "http://localhost:8080/email/nbUnread?userKey=" + userKey;
				HttpBuilderService httpBuilder = new HttpBuilderService();
				String result = httpBuilder.sendGet(url);
				System.out.println(result);

			} else if (selection == 2) {
				System.out.println("User Key = ");
				String userKey = scanner.next();
				String url = "http://localhost:8080/calendar/nextEvent?userKey=" + userKey;
				HttpBuilderService httpBuilder = new HttpBuilderService();
				String result = httpBuilder.sendGet(url);
				System.out.println(result);
			} else if (selection == 3) {
				System.out.println("User Key = ");
				String userKey = scanner.next();
				String url = "http://localhost:8080/contact/nbContact?userKey=" + userKey;
				HttpBuilderService httpBuilder = new HttpBuilderService();
				String result = httpBuilder.sendGet(url);
				System.out.println(result);
			} else if (selection == 4) {
				System.out.println("User Key = ");
				String userKey = scanner.next();
				System.out.println("AccountName = ");
				String accountName = scanner.next();
				String url = "http://localhost:8080/account/add/microsoft/"+accountName+"?userKey=" + userKey;
				HttpBuilderService httpBuilder = new HttpBuilderService();
				String result = httpBuilder.sendGet(url);
				System.out.println(result);

			}
			else if (selection == 5) {
				System.out.println("User Key = ");
				String userKey = scanner.next();
				System.out.println("AccountName = ");
				String accountName = scanner.next();
				String url = "http://localhost:8080/add/account/"+accountName+"?userKey=" + userKey;
				HttpBuilderService httpBuilder = new HttpBuilderService();
				String result = httpBuilder.sendGet(url);
				System.out.println(result);

			}
		}

	}

}