package fr.ynov.dap.GmailPOO;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Scanner;

import org.apache.logging.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Launcher {
    public static String defaulUser = "monCompte@gmail.com";
    // static  Logger logLauncher=Logger.getLogger(Launcher.class);
    static ConfigurableApplicationContext context;
    //TODO bes by Djer ne passe "" comme paramètre, sinon c'est loggé dans uen catégory "anonyme".
    // Avec Log4J tu peux laissé vide (il récupère automatiquement le nom, qualifié, de la classe).
    private static final Logger logger = LogManager.getLogger("");
    //TODO bes by Djer cette attribut n'est pas utilisé. Supprime le.
    // Tu devrait avoir un méthode "loadConfig" qui créé, et configure, une COnfiguration. Cette méthode devra être annoté @BEan pour que Spring la connaisse.
    // Une fois que Spring connait la méthode, il sait "réupérer" la valeur renvoyé, et l'injecter là ou tu lui demande.
    private static Config loadConfig;

    @RequestMapping("/")
    public String index() {
        return "Bonjour ";
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        SpringApplication.run(Launcher.class, args);
        //TODO bes by Djer Ne soit pas grossié avec ton code, sinon, il va encore plus planter !
        logger.info("merde");

        // logLauncher.info("nous somme a la void launcher");
        String user = "me";
        int nbEvent;
        Scanner in = new Scanner(System.in);
        char reponse = 'F';
        String toUp;

        //TODO bes by Djer Attention, tu "initialise" un "GoogleService" mais les classe filles,
        // Hérite de la "structure" de leur parent, pas des "données".
        // L'état d'un parent n'est PAS partagé avec les calsse fille.
        GoogleService monService = new GoogleService();
        monService.setConfiguration(loadConfig());
        monService.init();
        GMailService monGmail = GMailService.getInstance();
        CalendarService monCalendar = CalendarService.getInstance();
        logger.info("la config  " + monService.getConfiguration() + "   a la recherche nbunredmail");
        System.out.println("vous avez " + GMailService.getNbUnreadEmails(user) + " Unread Mail");
        nbEvent = monCalendar.getNbEvents();

        System.out.println("vous avez " + nbEvent + " Events");

        //System.out.println(monCalendar.getNextEvent());

        DeleteTokens();
    }

    @Bean
    public static Config loadConfig() {

        return new Config();
    }

    static void DeleteTokens() {
        //TODO bes by Djer Tu devrais utiliser LA config ! 
        File fichier = new File("tokens\\StoredCredential");
        if (fichier.delete()) {
            System.out.println("Deleted");
        }
    }
}
