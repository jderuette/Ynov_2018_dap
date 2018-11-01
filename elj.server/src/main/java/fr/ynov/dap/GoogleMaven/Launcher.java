package fr.ynov.dap.GoogleMaven;


import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import org.apache.logging.log4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;




@RestController
@SpringBootApplication
public class Launcher {
	// static  Logger logLauncher=Logger.getLogger(Launcher.class);
    //TODO elj by Djer Pourquoi faire ce contexte ? 
	static ConfigurableApplicationContext context;
	//TODO elj by Djer Ne force pas à une category vide, Laisse Log4Jfaire, ou utilise le nom, qualifié, de la classe
	private static final Logger logger = LogManager.getLogger("");
	//TODO elj by Djer Pas de majusucule au début d'un attribut ! 
	private static Config loadConfig; 
	public static void main(String[] args) throws IOException, GeneralSecurityException, URISyntaxException {

		SpringApplication.run(Launcher.class, args);
		GoogleService monService = new GoogleService();
		monService.setConfiguration(loadConfig());
		monService.init();

		//TODO elj by Djer Eclipse t'endique que ce n'est plsu utilisé, tu devrait les supprimer.
		GMailService monGmail = GMailService.getInstance();
		CalendarService monCalendar = CalendarService.getInstance();
		ContactService mescontacts = ContactService.getInstance();

		DeleteTokens();

		try{
			Scanner un = new Scanner(System.in); 


			int variable;
			boolean loop = true;
			while(loop)
			{
				un = new Scanner(System.in);
				System.out.println("Que voulez vous ? \n 1) connaitre le nombre de mails non lus ? taper 1 \n 2) Connaitre vos prochains evenement ? taper 2 !"+
						"\n 3) Connaitre le nombre de vos contacts ? taper 3 !  \n taper 0 pour quitter le programme");

				variable = Integer.parseInt(un.next());
				switch(variable)
				{
				case 1 :
					URL	messageurl = new URL("http://localhost:8080/UnreadMails/me");
					HttpURLConnection messagenonlu = (HttpURLConnection) messageurl.openConnection();
					messagenonlu.getResponseMessage();
					break;
				case 2 :
					URL eventurl = new URL("http://localhost:8080/NextEvents");
					HttpURLConnection evenement = (HttpURLConnection) eventurl.openConnection();
					evenement.getResponseMessage();
					break;
				case 3 :
					URL contacturl = new URL("http://localhost:8080/NombreDeContact");
					HttpURLConnection nbcontact = (HttpURLConnection) contacturl.openConnection();
					nbcontact.getResponseMessage();
					break;
				case 4 :
					Scanner un2 = new Scanner(System.in);
					System.out.println("Entrer le nom de votre nouvel utilisateur : \n");
					URL ajouturl = new URL("http://localhost:8080//account/add/"+un2.next());
					HttpURLConnection AjoutUser = (HttpURLConnection) ajouturl.openConnection();
					AjoutUser.getResponseMessage();
					break;
				case 0 :
					loop = false;
					break;
				}
			}
			un.close();
			//TODO elj by Djer et "un2" tu ne le close pas ? 
			System.out.println("à bientôt !");

		} catch (IOException e) {
		  //TODO elj by Djer Evite de logger juste le message de l'exxeption. En premie paramètre met ton propre message.
            // Puis utilise le secon, pour indiquer la cause.
            //TODO elj by Djer Pourquoi info ?
			logger.info(e.getMessage());
		}



	}

	public static Config loadConfig() {

		return new Config();
	}

	static void DeleteTokens() {
		File fichier = new File("tokens\\StoredCredential");
		if (fichier.delete()) {
			System.out.println("Deleted");
		}
	}
}
