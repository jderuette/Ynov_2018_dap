package client;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;


public class Client {
	private static final String USER_AGENT = "Mozilla/5.0";
	public static void main(String[] args) throws Exception {

		Scanner scan = new Scanner(System.in);

		int choix;
		String pseudo;
		String accountName;
		boolean bool = true;
		scan = new Scanner(System.in);
		System.out.println("Bienvenue dans ce systeme TRES avance de gestion de comptes google /n");
		System.out.println("Quel-est votre pseudo ?/n");
		pseudo = scan.next();
		while (bool) {
			
			System.out.println("Que voulez-vous r�aliser ? /n");
			System.out.println("Tapez 0 pour vous enregistrer, si vous �tes un nouvel utilisateur./n");
			System.out.println("Si, et SEULEMENT SI, vous etes deja enregistres : /n");
			System.out.println("Tapez 1 pour vous ajouter un compte microsoft /n");
			System.out.println("Tapez 2 pour vous ajouter un compte google /n");
			System.out.println("Tapez 3 pour voir le nombre de mails non lus sur tous vos comptes /n");
			System.out.println("Tapez 4 pour voir votre prochain evenements /n");
			System.out.println("Tapez 5 pour voir vos contacts sur tous vos comptes /n");
			choix = Integer.parseInt(scan.next());
			switch (choix) {
			case 0:
				Desktop.getDesktop().browse(new URI("http://localhost:8080/add/user/" + pseudo));
				break;
			case 1:
				Desktop.getDesktop().browse(new URI("http://localhost:8080/index"));
				break;
			case 2:
				System.out.println("------------------------------------------------ /n");
				System.out.println("Nommez votre compte : ");
				accountName = scan.next();
				//TODO baa by Djer |Rest API| Il manque le protocole, le serveur et le port (http://localhost:8080)
				Desktop.getDesktop().browse(new URI("/add/googleAccount/"+ pseudo + "/" + accountName));
				break;
			case 3:
				URL urlMail = new URL("http://localhost:8080/email/tousMessagesNonLus/");
				Object mails = sendGet(urlMail); 
				System.out.println(mails);
				break;
			case 4:
				URL urlEvent = new URL("http://localhost:8080/calendar/nextEvent/" + pseudo);
				Object events = sendGet(urlEvent); 
				System.out.println(events);
				break;
			case 5:
				URL urlPeople = new URL("http://localhost:8080/tousContacts/" + pseudo);
				Object people = sendGet(urlPeople); 
				System.out.println(people);
				break;
			default:
				System.out.println("Valeur incorecte, j'en conclu que vous voulez partir. \n Bonne journ�e.");
				bool = false;
				break;
			}
		}
		scan.close();
	}
	
	private static String sendGet(URL url) throws Exception {


        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();

    }
}
