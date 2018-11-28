package fr.ynov.dap;
 
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
 
/**
 * The Class Launcher.
 */
@SpringBootApplication
public class Launcher {
	
	/** The log. */
	private static Logger LOG = LogManager.getLogger(Launcher.class);
	
	/**
	 * Load config.
	 *
	 * @return the config
	 * @throws IOException 
	 */
	@Bean
	public static Config loadConfig() throws IOException {
		return new Config();
		//LOG.info("1");
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