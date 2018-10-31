package fr.ynov.dap.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.ynov.dap.dap.config.Config;


/**
 * 
 * @author Florian BRANCHEREAU
 *
 */
@SpringBootApplication
public class Launcher 
{    
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
    public static void main( String[] args ) throws Exception
    {
    	SpringApplication.run(Launcher.class, args);
    }
    /**
     * 
     * @return Config
     */
    @Bean
    @SuppressWarnings("PMD")
    private static Config config()
    {
    	return new Config();
    }
}
