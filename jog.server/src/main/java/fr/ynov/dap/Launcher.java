package fr.ynov.dap;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication

public class Launcher {
    //TODO jog by Djer Cette varaible n'est plus utilisée à priori
	private static String user = "me";

	public String getUser() {
		return user;
	}

	public void setDefaultUser(String user) {
	    //TODO jog by Djer Prend en comtpe la remarque d'Eclipse, tu mélange variable d'instane et de classe !
		this.user = user;
	}
/**
 * Main du programme qui lance l'invite de commande afin de laisser l'utilisateur choisir les options
 * @param args
 * @throws IOException
 * @throws GeneralSecurityException
 */
	public static void main(String[] args) throws IOException, GeneralSecurityException {
		SpringApplication.run(Launcher.class, args);
		
		//TODO jog by Djer Devrait être dans un projet "client" indépendant ! 
		SupprTokens();

		Scanner saisie = new Scanner(System.in);

		int variable;
		boolean loop = true;
		while (loop) {
			saisie = new Scanner(System.in);
			System.out.println(
					"Faites votre choix ? \n 1) Obtenir une liste de contacts ? \n 2) Prochains evenement ? "
							+ "\n 3) Nombre de mails non lus ?  \n taper 0 pour quitter le programme");

			variable = Integer.parseInt(saisie.next());
			switch (variable) {
			case 1:
				URL urlCase1 = new URL("http://localhost:8080/People");
				HttpURLConnection contacts = (HttpURLConnection) urlCase1.openConnection();
				contacts.getResponseMessage();
				break;
			case 2:
				URL urlCase2 = new URL("http://localhost:8080/calendar");
				HttpURLConnection event = (HttpURLConnection) urlCase2.openConnection();
				event.getResponseMessage();
				break;
			case 3:
				URL urlCase3 = new URL("http://localhost:8080/unread");
				HttpURLConnection mails = (HttpURLConnection) urlCase3.openConnection();
				mails.getResponseMessage();
				break;
			case 0:
				loop = false;
				break;
			}
		}
		saisie.close();
		System.out.println("aurevoir");

	}
	/**
	 * Fonction qui supprime le credential afin de permettre à un nouvel utilisateur de se connecter
	 */
	static void SupprTokens() {
	    //TODO Jog by Djer : Utiliser la Config ??? 
		File fichier = new File("tokens\\StoredCredential");
		if (fichier.delete()) {
		    //TODO jog by Djer pas de Sysout sur un serveur. Une LOG serait en revnache bien !
		    //TODO jog by Djer AJoute du contexte dans ton message (a minimum ce qui à été supprimé
		System.out.println("Deleted");
		}
		}
	/**
	 * Configuration de l'application
	 * @return  la configuration de l'application
	 */
	@Bean
	public static Config loadConfig() {
		Config maConf = new Config();
		return maConf;
	}

}