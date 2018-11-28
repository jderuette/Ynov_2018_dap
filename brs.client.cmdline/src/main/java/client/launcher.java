package client;

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

		String user = "";
		while (true) {
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			System.out.println("\nMenu Options\n");
			System.out.println("(1) - Gmail");
			System.out.println("(2) - Calendar");
			System.out.println("(3) - Contact");
			System.out.println("(4) - AddUser");

			System.out.print("Please enter your selection:\t");
			int selection = scanner.nextInt();

			if (user.equalsIgnoreCase("")) {
				System.out.print("Please enter your user:\t");
				user = scanner.next();
			}

			
			//TOD brs by Djer un petit switch aurait été pas mal ici
			if (selection == 1) {
				String url = "http://localhost:8080/getEmails/getLabel/userKey=" + user;
				HttpBuilderService httpBuilder = new HttpBuilderService();
				String result = httpBuilder.sendGet(url);
				System.out.println(result);

			} else if (selection == 2) {
				String url = "http://localhost:8080/getCalendar/GetLastEvent/userKey=" + user;
				HttpBuilderService httpBuilder = new HttpBuilderService();
				String result = httpBuilder.sendGet(url);
				System.out.println(result);
			} else if (selection == 3) {
				String url = "http://localhost:8080/getContact/GetNbCOntact/userKey=" + user;
				HttpBuilderService httpBuilder = new HttpBuilderService();
				String result = httpBuilder.sendGet(url);
				System.out.println(result);
			} else if (selection == 4) {

				String url = "http://localhost:8080/account/add/userKey=" + user;
				HttpBuilderService httpBuilder = new HttpBuilderService();
				String result = httpBuilder.sendGet(url);
				System.out.println(result);

			}
		}

	}

}