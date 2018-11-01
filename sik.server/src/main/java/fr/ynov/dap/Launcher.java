package fr.ynov.dap;

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
    //TODO sik by Djer attention, si pas de modifier, celui de la class est utilisé donc ici ton LOGGER est public
    //En plus il n'est pas utilisé dans le projet, mais ton IDE ne peut pas te l'indiquer, étant public il ne peu
    // pas savoir si un autre projet serait succeptible de l'utiliser....
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
     */
    @Bean
    public Config loadConfig() {
        return new Config();
    }

}
