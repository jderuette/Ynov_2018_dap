package fr.ynov.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Launcher {
	
	
	private static final Logger logger = LogManager.getLogger();
	/**
	 * Chargement de la configuration pour l'injection de dépendance dans les services
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@Bean
	public Config loadConfig() throws IOException, GeneralSecurityException {
		logger.debug("Chargement de la configuration au demarrage");
		return new Config();
	}
	
	public static void main(String[] args) throws IOException, GeneralSecurityException {
		SpringApplication.run(Launcher.class, args);		
	}



}
