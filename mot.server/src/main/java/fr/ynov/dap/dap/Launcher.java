package fr.ynov.dap.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * The Class Launcher.
 */
@SpringBootApplication
public class Launcher {
	
	/**
	 * Main Launcher.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
    public static void main(String... args) throws IOException, GeneralSecurityException {
    	SpringApplication.run(Launcher.class, args); 
    }
    
    
    /**
     * Handle the Google response.
     *
     * @return Config
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @Bean
    public static Config loadConfig() throws IOException, GeneralSecurityException {
    	Config maConfig = new Config();
    	
    	//TODO mot by Djer Utilise la Javadoc de la classe pour indiquer ce genre d'informations
    	/**
    	 * TO USE ZERO CONF
    	 * maConfig.setTokensDirectory("{Your path}");
    		maConfig.setCredentialFilePath("{Your path}");
    	 */
    	return maConfig;
    }
}

