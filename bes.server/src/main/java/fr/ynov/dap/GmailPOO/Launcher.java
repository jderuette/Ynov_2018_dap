package fr.ynov.dap.GmailPOO;



import java.io.File;
import java.io.IOException;



import java.net.URISyntaxException;

import java.security.GeneralSecurityException;
//TODO bes by Djer |IDE| Configure les "save action" de ton IDE pour éviter de conserver des imports inutiles !
import java.util.Calendar;
import java.util.Date;

import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import fr.ynov.dap.GmailPOO.dao.AccountRepository;
import fr.ynov.dap.GmailPOO.dao.AppUserRepository;
import fr.ynov.dap.GmailPOO.data.AppUser;
import fr.ynov.dap.GmailPOO.data.GoogleAccount;


//TODO bes by Djer |IDE| Configure les "save action" de ton IDE pour éviter pour éviter d'avoir du code "mal formaté"


@SpringBootApplication
public class Launcher implements CommandLineRunner {
	static int nbEvent;
	public static String defaulUser = "monCompte@gmail.com";
	//TODO bes by Djer |POO| Cette attribut ne sert plus. Netoie ton code et supprime le code commenté
	// static Logger logLauncher=Logger.getLogger(Launcher.class);
	static ConfigurableApplicationContext context;
	private static final Logger logger = LogManager.getLogger();
/*	static GMailService monGmail;
    @Autowired
	static CalendarService monCalendar;
	static ContactsService monContact;
   
    @Autowired
    private GoogleAccountRepository googleAccountRepository;
    @Autowired
   private fr.ynov.dap.GmailPOO.metier.Account account;
*/
	 @Autowired
	 //TODO bes by Djer |IDE| ton IDE t'indique que ca n'est pas utilisé. A supprimer ? Bug ? 
	    private AppUserRepository appUserRepository;
	 @Autowired
	 //TODO bes by Djer |IDE| ton IDE t'indique que ca n'est pas utilisé. A supprimer ? Bug ?
	    private AccountRepository accountRepository;
	//TODO bes by Djer |POO| attention si pas de "modifier" alors celui de la classe. Cette attribut est donc public ! Elle est inutilisée mais ton IDE ne peut pas te le signaler car elle est public.
@Autowired
Config config; 
	public static void main(String[] args) throws IOException, GeneralSecurityException, URISyntaxException, InstantiationException, IllegalAccessException {
		SpringApplication.run(Launcher.class, args);
		
		  
		
	
		
        
		//choix();

		// DeleteTokens();
	}

	@Bean
	public static Config loadConfig() {

		return new Config();
	}

	

	//TODO bes by Djer |POO| attention si pas de "modifier" alors celui de la classe. Cette méthode est donc public ! Elle est inutilisée mais ton IDE ne peut pas te le signaler car elle est public.
	static void DeleteTokens() {
		File fichier = new File("");
		if (fichier.delete()) {
			System.out.println("Deleted");
		}
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("lancement de l'APP");
		
		
		
	}
}
