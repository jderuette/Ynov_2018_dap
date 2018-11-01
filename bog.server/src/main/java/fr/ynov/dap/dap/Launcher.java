package fr.ynov.dap.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.ynov.dap.dap.Configuration.Config;


/**
 * 
 * @author Gaël BOSSER
 * Point d'entrée de l'application serveur
 */
@SpringBootApplication
public class Launcher 
{    
	/**
	 * 
	 * @param args
	 * Run SpringBoot
	 * @throws Exception
	 */
    public static void main( String[] args ) throws Exception
    {
    	SpringApplication.run(Launcher.class, args);
    }
    /**
     * 
     * @return Config
     * LoadConfig for the application
     */
    
    @Bean
    @SuppressWarnings("PMD")
    private static Config loadConfig()
    {
    	return new Config();
    }
}
