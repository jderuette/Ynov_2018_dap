package fr.ynov.dap_client.dap_client;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * App.
 */
public class App {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws URISyntaxException the URI syntax exception
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {
		choiceRoute(args);
	}

	/**
	 * Choice route.
	 *
	 * @param args the args
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void choiceRoute(String[] args) throws IOException {
		if (args == null || args.length == 0 || args.length > 4) {
			System.out.println("Wrong args !!!");
			help();
			return;
		}

		String url = "";

		switch (args[0]) {
			case "add":
				switch (args[1]) {
				case "google":
					url = "http://localhost:8080/account/add/google/" + args[2] + "?userKey=" + args[3];
					break;
				case "outlook":
					url = "http://localhost:8080/account/add/outlook/" + args[2] + "?userKey=" + args[3];
					break;
				default:
					help();
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
				System.out.println(request("http://localhost:8080/mail/inbox?userKey=" + args[1]));
				break;
			case "user":
				System.out.println(request("http://localhost:8080/user/add/" + args[1]));
				break;
			case "calendar":
				System.out.println(request("http://localhost:8080/calendar/nextEvent?userKey=" + args[1]));
				break;
			case "contact":
				System.out.println(request("http://localhost:8080/contact/getContacts?userKey=" + args[1]));
				break;
			case "view":
				try {
					Desktop.getDesktop().browse(new URI("http://localhost:8080/data"));
					Desktop.getDesktop().browse(new URI("http://localhost:8080/outlook/mails?userKey=" + args[1]));
				} catch (URISyntaxException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        	break;
			default:
				help();
				break;
			}
	}

	/**
	 * Help.
	 */
	private static void help() {
		System.out.println("Command :");
		System.out.println("add [google|microsoft] <account> <userKey>");
		System.out.println("view <userKey>");
		System.out.println("email <userKey>");
		System.out.println("calendar <userKey>");
		System.out.println("contact <userKey>");
	}

	/**
	 * Request.
	 *
	 * @param route the route
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String request(String route) throws IOException {
		try {
			URL path = new URL(route);
			HttpURLConnection connection = (HttpURLConnection) path.openConnection();
			try {
				connection.setRequestMethod("GET");
				int responseCode = connection.getResponseCode();
				System.out.println("GET Response Code :: " + responseCode);
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				return response.toString();
			} finally {
				connection.disconnect();
			}

		} catch (Exception e) {
			System.out.println("ERROR : " + e.getMessage());
			return null;
		}
	}
}
