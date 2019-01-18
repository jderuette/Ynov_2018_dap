package fr.ynov.dap;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Launcher implements CommandLineRunner {
    //TODO bes by Djer |POO| Plus utile (tant mieux !). Si tu l'avais déclaré en "private" ton IDE aurais pu t'indiquer que cette attribut n'est plus utilisé.
    static int nbEvent;
    //TODO bes by Djer |POO| Cetet attribut "public !!) n'est utilisé nullPart, suprime-le.
    public static String defaulUser = "monCompte@gmail.com";
    //TODO bes by Djer |Spring| Ce context ne t'ai pas utile. SpringBoot en "génère" et configure un pour toi. Tu peux supprimer cette attribut
    static ConfigurableApplicationContext context;
    private static final Logger logger = LogManager.getLogger();

    /**
     * App entryPoint.
     * @param args User arguments
     * @throws IOException
     * @throws GeneralSecurityException
     * @throws URISyntaxException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static void main(final String[] args) throws IOException, GeneralSecurityException, URISyntaxException,
            InstantiationException, IllegalAccessException {
        SpringApplication.run(Launcher.class, args);
    }

    @Bean
    /**
     * Load a default configuration.
     * @return
     */
    public static Config loadConfig() {

        return new Config();
    }

    @Override
    /**
     * Spring entryPoint.
     */
    public void run(final String... args) throws Exception {
        logger.info("lancement de l'APP");

    }
}
