package fr.ynov.dap.Client_dap;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

/**
 * The Class App.
 */
public class App {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException        Signals that an I/O exception has occurred.
	 * @throws URISyntaxException the URI syntax exception
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {
		boolean stop = false;
		Scanner sc = new Scanner(System.in);

		while (stop != true) {

			URI uri = new URI("");
			String comm = sc.nextLine();
			String[] commandes = comm.split(" ");
			
			if (commandes[0].toUpperCase().equals("ADMIN"))
			{
				uri = new URI("http://localhost:8080/admin");
				Desktop.getDesktop().browse(uri);
			}
			else if (commandes[0].toUpperCase().equals("ADD"))
				switch (commandes[1].toUpperCase()) {

				case "USER":
					uri = new URI("http://localhost:8080/user/add/" + commandes[2]);
					Desktop.getDesktop().browse(uri);
					break;

				case "MICROSOFT": 

					uri = new URI(
							"http://localhost:8080/add/microsoft/account/" + commandes[2] + "?userKey=" + commandes[3]);
					Desktop.getDesktop().browse(uri);
					break;

				case "GOOGLE":

					uri = new URI(
							"http://localhost:8080/add/google/account/" + commandes[2] + "?userKey=" + commandes[3]);
					Desktop.getDesktop().browse(uri);
					break;
				}

			else if (commandes[0].toUpperCase().equals("VIEW")) {
				if (commandes[1].toUpperCase().equals("ALL"))
					switch (commandes[2].toUpperCase()) {
					case "UNREADMAIL":
						uri = new URI("http://localhost:8080/unreadMail/all/" + commandes[3]);
						Desktop.getDesktop().browse(uri);
						break;

					case "NEXTEVENT":
						uri = (new URI("http://localhost:8080/allEvent/" + commandes[3]));
						Desktop.getDesktop().browse(uri);
						break;

				
					case "NBCONTACT":
						uri = new URI("http://localhost:8080/nbContact/all/" + commandes[3]);
						Desktop.getDesktop().browse(uri);
						break;
					case "MAIL":
						uri = new URI("http://localhost:8080/all/email/" + commandes[3]);
						Desktop.getDesktop().browse(uri);
						break;
					}
				else if (commandes[1].toUpperCase().equals("GOOGLE"))
					switch (commandes[2].toUpperCase()) {
					case "UNREADMAIL":
						uri = new URI("http://localhost:8080/UnreadMail/google/" + commandes[3]);
						Desktop.getDesktop().browse(uri);
						break;
					case "NBCONTACT":
						uri = new URI("http://localhost:8080/nbContact/google/" + commandes[3]);
						Desktop.getDesktop().browse(uri);
						break;
					case "CONTACTLIST":
						uri = new URI("http://localhost:8080/contactList/google/" + commandes[3]);
						Desktop.getDesktop().browse(uri);
						break;
					}
				else if (commandes[1].toUpperCase().equals("MICROSOFT"))
					switch (commandes[2].toUpperCase()) {
					case "MAIL":
						uri = new URI("http://localhost:8080/list/email?accountName=" + commandes[3]);
						Desktop.getDesktop().browse(uri);
						break;
				
					}
			}
		}
	}

	
}
