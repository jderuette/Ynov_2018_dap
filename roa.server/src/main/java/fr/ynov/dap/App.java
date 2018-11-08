package fr.ynov.dap;

import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Launcher de l'application, lance le serveur via spring.
 */
@SpringBootApplication
public class App {
    /**
     * Fonction Main, lance le serveur tomcat via spring.
     * @param args list of parameters.
     */
    public static void main(final String[] args) {
        SpringApplication.run(App.class, args);
        LogManager.getLogger().info("App lancée");
    }
}
