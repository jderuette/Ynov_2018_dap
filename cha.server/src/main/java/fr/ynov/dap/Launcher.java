package fr.ynov.dap;
 
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.ynov.dap.google.*;
 
/**
 * The Class Launcher.
 */
@SpringBootApplication
@RestController
//TODO cha by Djer Il ne faut pas melanger un "launcher" avec du COntroller ! 
public class Launcher {
	
	/** The Constant defaultUser. */
	public static final String defaultUser = "champolionalexandre@gmail.com";
	
	/** The log. */
	private static Logger LOG = LogManager.getLogger(Launcher.class);
	
	/**
	 * Load config.
	 *
	 * @return the config
	 */
	@Bean
	public static Config loadConfig() {
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
        //CalendarQuickstart.main(args);
        //GmailQuickstart.main(args);
    }
	
	//TODO cha by Djer si tu as vraiment besoin d'une URL de "test" place-le dans une classe dédiée
	/**
	 * Test.
	 *
	 * @return the string
	 */
	@RequestMapping(value="/cc")
	public String test() {
		return "Coucou";
	}

}