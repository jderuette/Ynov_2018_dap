
package fr.ynov.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Launcher {

    public String defaultUser = "anthony.delorme81@gmail.com";
    private static Logger logger = LogManager.getLogger();

    @Autowired
    //FIXME dea by Djer si tu "autowired" c'est que tu exiges qu'il existe déja une instance. 
    //Ne jamais faire de "new" sur un objet injecté ! (sauf éventuellment dans un "client" et lors de "tests")
    private static Config config = new Config();

    /**
     * Fonction lancée au démarrage.
     * 
     * @param args
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public static void main(String... args) throws GeneralSecurityException, IOException {
        //TODO dea by Djer Pourrais être dans méthode qui construit LA configuration
        //config.setRacineFolder("C:\\Users\\antod\\eclipse-workspace\\dap");
        config.setRacineFolder(System.getProperty("user.home") + System.getProperty("file.separator") + "dap"
                + System.getProperty("file.separator") + "dea" + System.getProperty("file.separator"));
        //config.setCredentialFolder("src\\main\\resources");

        SpringApplication.run(Launcher.class, args);
    }

    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            Launcher.logger.info("inFo : Lacement de spring");

        };
    }

    /**
     * Charge la variable config
     * 
     * @return
     */
    @Bean
    public static Config loadConfig() {
        //TODO dea by Djer ici renvoie un nouvelle Objet. Pas besoin de le"stocker" dans un attribut, Spring s'occupera de le "stocker" et de le rendre disponible.
        config.setApplicationName("Application de Anthony");

        return config;
    }
}
