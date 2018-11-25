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
    @Bean
    public Config loadConfig() throws IOException {
        return new Config();
    }

}
