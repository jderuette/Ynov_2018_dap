package fr.ynov.dap;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Class launch at start.
 * @author Kévin Sibué
 *
 */
@SpringBootApplication
public class Launcher {

    /**
     * Logger instance.
     */
    //TODO sik by Djer |POO| Devrait etre static (en plus il est MAJUSCULE ce qui est trompeur !)
    //TODO sik by Djer |POO| Attention, cette atribut est public (même modifier que la classe si rien de préicser). En plus il n'est pas/plus utilisé mais ton IDE ne peut pas te l'indiquer
    static final Logger LOGGER = LogManager.getLogger();

    /**
     * Main method. Launched at startup.
     * @param args Every argument sent by system at launch.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

    /**
     * Return current API configuration.
     * @return Configuration
     * @throws IOException Exception
     */
    //TODO sik by Djer |Spring| Comme tu as mis un "@Configuration" sur la classe Config, cette méthode n'est plus utile.
    @Bean
    public Config loadConfig() throws IOException {
        return new Config();
    }

}
