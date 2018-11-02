package fr.ynov.dap_client.dap_client;
//TODO bot by Djer Evite les _ dans les nom de package

import java.net.HttpURLConnection;
//TODO bot by Djer Configure les "save action" de ton IDE. CF mémo Eclipse
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;
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
		String url = "http://localhost:8080";
		String userKey = args[0];

		if (args[1].equals("addAccount")) {
			addAccount(url, userKey);
		} else if (args[1].equals("calendar")) {
			calendar(url, userKey);
		} else if (args[1].equals("gmail")) {
			gmail(url, userKey);
		}else if (args[1].equals("contact")) {
			contact(url, userKey);
		} else {
			System.out.println("Wrong args !");
		}
	}

	/**
	 * Adds the account.
	 *
	 * @param url the url
	 * @param userKey the user key
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws URISyntaxException the URI syntax exception
	 */
	public static void addAccount(String url, String userKey) throws IOException, URISyntaxException {
		String route = url + "/account/add/" + userKey;
		URI uri = new URI(route);
		Desktop.getDesktop().browse(uri);
		
	}

	/**
	 * Calendar.
	 *
	 * @param url the url
	 * @param userKey the user key
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void calendar(String url, String userKey) throws IOException {
		String route = url + "/calendar/nextEvent?userKey=" + userKey;
		URL path = new URL(route);
		System.out.println(request(path));
	}

	/**
	 * Gmail.
	 *
	 * @param url the url
	 * @param userKey the user key
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void gmail(String url, String userKey) throws IOException {
		String route = url + "/mail/inbox?userKey=" + userKey;
		URL path = new URL(route);
		System.out.println(request(path));
	}
	
	/**
	 * Contact.
	 *
	 * @param url the url
	 * @param userKey the user key
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void contact(String url, String userKey) throws IOException {
		String route = url + "/contact/getContacts?userKey=" + userKey;
		URL path = new URL(route);
		System.out.println(request(path));
	}

	/**
	 * Request.
	 *
	 * @param path the path
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static String request(URL path) throws IOException {
		HttpURLConnection con = (HttpURLConnection) path.openConnection();
		con.setRequestMethod("GET");
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();
		return response.toString();
	}
}
