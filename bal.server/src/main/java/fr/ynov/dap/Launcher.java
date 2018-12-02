package fr.ynov.dap;
 
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
 
/**
 * The Class Launcher.
 */
@SpringBootApplication
public class Launcher {
	/** The log. */
    //TODO bal by Djer |IDE| Ton IDE t'indique que cette constante n'est pas utilisée. Utilise là ou sppirme la.
	private static Logger LOG = LogManager.getLogger(Launcher.class);
	
	/**
	 * Load config.
	 *
	 * @return the config
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//TODO bal by Djer |POO| Pourquoi static ?
	@Bean
	public static Config loadConfig() throws IOException {
		return new Config();
			}
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public static void main( String[] args ) throws IOException, GeneralSecurityException
    {
		SpringApplication.run(Launcher.class, args); 
    }
	
}