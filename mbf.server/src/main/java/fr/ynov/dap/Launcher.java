package fr.ynov.dap;

import fr.ynov.dap.configurations.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * This is the entry point class of the server application.
 */
@SpringBootApplication
public class Launcher {

    /**
     * This method is used as the entry point of the server application.
     * @param args The different arguments passed into the command line used to execute the jar.
     */
    public static void main(String... args) {
        SpringApplication.run(Launcher.class, args);
    }

    /**
     * This methods creates Configuration for the application to use. It has a default configuration when setters are not set.
     * @return Config
     */
    @Bean
    public Config loadConfig() {
        return new Config();
    }
}