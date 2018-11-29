package fr.ynov.dap;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;


@RestController
@SpringBootApplication
/**
 * Classe "main" du programme, se charge de lancer l'application
 * @author abaracas
 *
 */
public class Launcher {
    
    /**
     * Fonction principale du programme, lance l'application
     * @param args args
     * @throws GeneralSecurityException exception
     * @throws IOException exception
     * @throws URISyntaxException exception
     */
    public static void main(String[] args) throws GeneralSecurityException, IOException, URISyntaxException {
	SpringApplication.run(Launcher.class, args);	
    }

    @Bean
    /**
     * Load la config, permet de la réutiliser simplement grâce à l'autowired.
     * @return la config par default
     */
    public Config loadConfig() {
	Config maConfig = new Config();
	return maConfig;
    }
}
