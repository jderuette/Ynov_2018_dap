package fr.ynov.dap.dap;

import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Launcher de l'application, lance le serveur via spring.
 */
@SpringBootApplication @ComponentScan(basePackages = { "fr.ynov.dap"} )
public class App {
    /**
     * Fonction Main, lance le serveur tomcat via spring.
     * @param args list of parameters.
     */
    public static void main(final String[] args) {
        SpringApplication.run(App.class, args);
        LogManager.getLogger().info("App lancée");
    }
    /**
     * Récupère la config de l'application pour l'autowire.
     * @return Config
     */
    @Bean
    private static Config getConf() {
        return new Config();
    }
}
