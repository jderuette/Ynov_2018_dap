package fr.ynov.dap;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private Config config;

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
        //TODO bim by Djer |Log4J| PAS de SysOut sur un Serveur ! Utilise une Log
        System.out.println(config.getApplicationName());
        return args -> {
        };
    }
    /**
     * Return config file.
     * @return config
     */
    @Bean
    //TODO bim by Djer |Spring| Est-ce toujour utile ?
    public Config loadConfig() {
        return this.config;
    }

}
