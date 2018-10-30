package fr.ynov.dap;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;


@RestController
//TODO baa by Djer Pourquoi un restController sur le Launcher ? Sans RequestMapping, pas très utile.
@SpringBootApplication
public class Launcher {
  //TODO baa by Djer Pouruqoi faire ? Traiter le warning "variable non utilisé ?
    private static String defaultUser = "me";
    //TODO baa by Djer A quoi sert ce contexte ?
    static ConfigurableApplicationContext context;
    //FIXME baa by DJer Ne pas passer "", sinon cela sera la category du Logger. 
    //Sans parametre Log4J utilise autoatiquement le nom qualifié de la classe.
    private static final Logger logger = LogManager.getLogger("");
    //TODO baa by Djer Pouruqoi faire ? Traiter le warning "variable non utilisé ?
    private static Config loadConfig; 
    
    //TODO en clarifiant la JavaDoc (quel Programe, client?Server?) ca aurait aider à mieux résoudre le devoir ! 
    /**
     * Fonction principale du programme (CQFD)
     * @param args
     * @throws GeneralSecurityException
     * @throws IOException
     * @throws URISyntaxException
     */
    public static void main(String[] args) throws GeneralSecurityException, IOException, URISyntaxException {
	SpringApplication.run(Launcher.class, args);
	supprStoredCredential();
	
	//FIXME baa by Djer Ce code doit être dans un client indépendant !!!
	try{
	    Scanner scan = new Scanner(System.in); 

	    int choix;
	    boolean bool = true;
	    while(bool)
	    {
		scan = new Scanner(System.in);
		System.out.println("Bienvenue dans ce systeme TRES avance de gestion de comptes google /n");
		System.out.println("Que voulez-vous réaliser ? /n");
		System.out.println("Tapez 1 pour voir le nombre de mails non lus sur votre boîte gmail./n");
		System.out.println("Tapez 2 pour voir vos prochains evenements calendar. /n");
		System.out.println("Tapez 3 pour voir vos contacts et leurs numéros.");
		choix = Integer.parseInt(scan.next());
		switch(choix)
		{
		case 1 :
		    URL url = new URL("http://localhost:8080/email/messagesNonLus");
		    HttpURLConnection unread = (HttpURLConnection) url.openConnection();
		    unread.getResponseMessage();
		  //FIXEME baa by Djer : affichage de la réponse ???
		    break;
		case 2 :
		    Desktop.getDesktop().browse(new URI("http://www.example.com"));
		    URL url2 = new URL("http://localhost:8080/calendar/nexEvent");
		    HttpURLConnection nextEvent = (HttpURLConnection) url2.openConnection();
		    nextEvent.getResponseMessage();
		    //FIXEME baa by Djer : affichage de la réponse ???
		    break;
		case 3 :
		    URL url3 = new URL("http://localhost:8080/contacts");
		    HttpURLConnection contacts = (HttpURLConnection) url3.openConnection();
		    contacts.getResponseMessage();
		  //FIXEME baa by Djer : affichage de la réponse ???
		    break;
		default :
		    System.out.println("Valeur incorecte, j'en conclu que vous voulez partir. \n Bonne journée.");
		    bool = false;
		    scan.close();
		    break;
		}
	    }
	} catch (IOException e) {
	    //TODO baa by Djer Fermeture du Scanner ?
	    //TODO baa By Djer une Error serait plus approprié ?
	    logger.info(e.getMessage());
	}
    }

    @Bean
    /**
     * Load la config, permet de la réutiliser simplement grâce à l'autowired
     * @return
     */
    public Config loadConfig() {
	Config maConfig = new Config();
	return maConfig;
    }

    /**
     * utilisé ici pour permettre de switch d'adresse mail, non optimal en prod
     */
    static void supprStoredCredential() {
	//FIXME baa by Djer utiliser le Config ????
	File fichier = new File("tokens\\StoredCredential");
	if (fichier.delete()) {
	    System.out.println("Deleted");
	}
    }
}
