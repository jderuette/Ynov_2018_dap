//TODO elj by Djer |POO| Evite les majuscules dans les nom de package, mais surtout evite de mettre des infos technique. Si jamais tu décides de ne plus utiliser Maven tu change tous les noms de packages ? 
package fr.ynov.dap.GoogleMaven;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.GoogleMaven.data.AppUser;
import fr.ynov.dap.GoogleMaven.data.GoogleAccount;
import fr.ynov.dap.GoogleMaven.repository.AppUserRepostory;
import fr.ynov.dap.GoogleMaven.repository.GoogleAccountRepository;
import fr.ynov.dap.GoogleMaven.web.MailController;


//TODO elj by Djer |Rest API| Est-ce vraiment un controller Rest ? 
@RestController
@SpringBootApplication
public class Launcher implements CommandLineRunner{
	private  final static Logger logger = LogManager.getLogger();
	
	//TODO elj by Djer |IDE| Ton IDE te dit que ca n'est pas utilisé. Bug ? A supprimer ? 
	@Autowired
	private GoogleAccountRepository googleAccountRepository;
	
	//TODO elj by Djer |IDE| Ton IDE te dit que ca n'est pas utilisé. Bug ? A supprimer ? 
	@Autowired
	private AppUserRepostory appUserRepostory;
	
	//TODO elj by Djer |IDE| Ton IDE te dit que ca n'est pas utilisé. Bug ? A supprimer ? 
	@Autowired
	private fr.ynov.dap.GoogleMaven.repository.AccountData accountData;
	
	/**
	 * 
	 * @param user
	 * @return Launcher client
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public static void main(String[] args) throws IOException, GeneralSecurityException, URISyntaxException {
        
		SpringApplication.run(Launcher.class, args);
        

		//TODO elj by Djer |Rest API| La très grande majorité de ce code devrait être transféré dans le client !
		try{
			Scanner un = new Scanner(System.in); 
            String user = null;
   
			int variable;
			boolean loop = true;
			while(loop)
			{
				un = new Scanner(System.in);
				System.out.println("Que voulez vous ? \n"
						+ " 1) connaitre le nombre de mails  gmail non lus ? taper 1"
						+ " \n 2) Connaitre vos prochains evenement google ? taper 2 !"
						+ "\n 3) Connaitre le nombre de vos contacts google ? taper 3 !"
						+ "\n 4) Ajouter un nouveau compte et/ou utilisateur ? taper 4 !"
						+ "\n 5) Changer de UserKey ? taper 5 !"
						+ "\n 6) cvoir vos mails microsoft ? taper 6"
						+ "\n 7) connaitre les evenements microsoft ? taper 7"
						+ "\n 8) connaitre vos contacts microsoft ? taper 8"
						+ "  \n taper 0 pour quitter le programme");

				variable = Integer.parseInt(un.next());
				switch(variable)
				{
				case 1 :
				    //TODO elj by Djer |POO| Evite de suposer que Chrome est présent. Java porpose une méthdoe "multi plateforme" via Desktop.getDesktop().browse. Cependant Spring lance ton appli en mdoe "server" et donc "headLess" ce qui va empecher la JVM d'ouvrir un naviguateur (ce code DOIT être dnas un projet "client")
					Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome http://localhost:8080/unreadmailgoogle/"+user});
					break;
				case 2 :
					Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome http://localhost:8080/NextEvents/"+user});
					break;
				case 3 :
					Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome http://localhost:8080/NombreDeContactgmail/"+user});
					break;
				case 4 :
					Scanner un2 = new Scanner(System.in);
					System.out.println("Entrer le nom de votre compte suivi du userKey (exemple : google georges) : \n");
				    user = un2.next().split(" ")[1];
					URL ajouturl = new URL("http://localhost:8080//account/add/"+un2.next().split(" ")[0]+"/"+un2.next().split(" ")[1]);
					HttpURLConnection AjoutUser = (HttpURLConnection) ajouturl.openConnection();
					
					un2.close();
					break;
				case 5 :
					Scanner un3 = new Scanner(System.in);
					System.out.println("Entrer le userKey : \n");
				    user = un3.next();
					break;
				case 6 :
					Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome http://localhost:8080/Outlookmails"});
					break;
				case 7 :
					Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome http://localhost:8080/eventsoutlook"});
					break;
				case 8 :
					Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome http://localhost:8080/contactsoutlook"});
					break;
				case 0 :
					loop = false;
					break;
				}
			}
			un.close();
			
			System.out.println("à bientôt !");

		} catch (IOException e) {

			logger.warn("Une erreur a été détecter dans le launcher avec comme détails : "+e.getMessage());
		}
		
	}
	@Bean
	public Config loadConfig() {

		return new Config();
	}
	public void run(String... arg0) throws Exception {
			//AppUser myuser = appUserRepostory.save(new AppUser("Jaxter"));
			//AppUser jawad = accountData.consulterUser("jawad");
		    //googleAccountRepository.save(new GoogleAccount(myuser, "YNOV","blabla@ynov.com",""));
			//System.out.println(appUserRepostory.());
		   // System.out.println("Vous venez de chercher l'utilisateur : "+ jawad.getUserKey());
	}

}
