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
public class App {

	/** The backendServer url. */
	private static URI url;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {

		if (args[1] == "help") {
			System.out.println("\n usage API and add user : -jar client-0.0.1-SNAPSHOT.jar [options] [userKey] \n"
					+ " example: java -jar client-0.0.1-SNAPSHOT.jar \"contacts\" loic  \n");

			System.out
					.println("\n usage Add Account: -jar client-0.0.1-SNAPSHOT.jar [options] [userKey] [accountName] \n"
							+ " example: java -jar client-0.0.1-SNAPSHOT.jar \"add-google-account\" loic personnel  \n");
		}
		if (args.length == 2) {
			try {
				switch (args[0]) {
				case "add-user":
					addUser(args[1]);
					break;
				case "mail-unread":
					showMailInboxUnread(args[1]);
					break;
				case "events":
					showCalendarNextEvent(args[1]);
					break;
				case "contacts":
					showNumberOfContacts(args[1]);
					break;
				case "view":
					showAll(args[1]);
					break;
				default:
					System.out.println("Enter arguments");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("\n usage: -jar client-0.0.1-SNAPSHOT.jar [options] [userKey] \n"
						+ " example: java -jar client-0.0.1-SNAPSHOT.jar \"contacts\" loic  \n");
			}
		} else if (args.length == 3) {
			try {
				switch (args[0]) {
				case "add-google-account":
					addAccountGoogle(args[1], args[2]);
					break;
				case "add-microsoft-account":
					addAccountMicrosoft(args[1], args[2]);
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("\n usage: -jar client-0.0.1-SNAPSHOT.jar [options] [userKey] [accountName] \n"
						+ " example: java -jar client-0.0.1-SNAPSHOT.jar \"add-google-account\" loic personnel  \n");
			}
		}
	}

	/**
	 * Show all.
	 *
	 * @param userId
	 *            the user id
	 * @throws Exception
	 *             the exception
	 */
	private static void showAll(String userId) throws Exception {
		showCalendarNextEvent(userId);
		showNumberOfContacts(userId);
		showMailInboxUnread(userId);
	}

	/**
	 * Show number of contacts.
	 *
	 * @param userKey the user key
	 * @throws Exception             the exception
	 */
	private static void showNumberOfContacts(String userKey) throws Exception {
		StringBuffer response = sendGet(Constant.getNumberOfContacts + userKey);
		System.out.println(response.toString());
	}

	/**
	 * Show calendar next event.
	 *
	 * @param userKey the user key
	 * @throws Exception             the exception
	 */
	private static void showCalendarNextEvent(String userKey) throws Exception {
		StringBuffer response = sendGet(Constant.getNextEvent + userKey);
		System.out.println(response.toString());
	}

	/**
	 * Adds the account google.
	 *
	 * @param userKey the user key
	 * @param accountName the account name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void addAccountGoogle(String userKey, String accountName) throws IOException {
		try {
			url = new URI(Constant.urlAddAccountGoogle + accountName + "?userKey=" + userKey);
			Desktop.getDesktop().browse(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the account microsoft.
	 *
	 * @param userKey the user key
	 * @param accountName the account name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void addAccountMicrosoft(String userKey, String accountName) throws IOException {
		try {
			url = new URI(Constant.urlAddAccountMicrosoft + accountName + "?userKey=" + userKey);
			Desktop.getDesktop().browse(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the user.
	 *
	 * @param userKey the user key
	 * @throws Exception the exception
	 */
	private static void addUser(String userKey) throws Exception {
		try {
			StringBuffer response = sendGet(Constant.urlAddUser + userKey);
			System.out.println(response.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Show mail inbox unread.
	 *
	 * @param userKey the user key
	 * @throws Exception             the exception
	 */
	private static void showMailInboxUnread(String userKey) throws Exception {
		StringBuffer response = sendGet(Constant.getMailUnreadInbox + userKey);
		System.out.println(response.toString());
	}

	/**
	 * Send get.
	 *
	 * @param urlString
	 *            the url string
	 * @return the string buffer
	 * @throws Exception
	 *             the exception
	 */
	private static StringBuffer sendGet(String urlString) throws Exception {

		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response;
	}
}
