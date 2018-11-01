package fr.ynov.dap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * Basic class for application launch.
 * @author MBILLEMAZ
 *
 */
@SpringBootApplication
public class Launcher {

    /**
     * Create default config.
     */
    private Config config = new Config();

    /**
     * Launch application.
     * @param args application arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

    /**
     * Start application.
     * @param ctx application context
     * @return function to execute
     */
    @Bean
    public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
        return args -> {
            if (args.length > 0) {
                //TODO bim by Djer attention, l'autre @bean risque d'être appelé avant le commandLineRunner.
                // Si spring "cache" ton bean, alors le modifier ici ne fonctionnera pas.
                // tu airait une "petite" de chance plus en MODIFIANT l'instance existante
                this.config = new Config(args[0]);
            }
        };
    }

    /**
     * Return config file.
     * @return config
     */
    @Bean
    public Config loadConfig() {
        return this.config;
    }

}
