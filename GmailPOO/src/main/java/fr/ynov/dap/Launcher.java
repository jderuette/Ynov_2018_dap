package fr.ynov.dap;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
  
 
@SpringBootApplication  
public class Launcher implements CommandLineRunner {
	static int nbEvent;
	public static String defaulUser = "monCompte@gmail.com";
	static ConfigurableApplicationContext context;
	private static final Logger logger = LogManager.getLogger();
 
	public static void main(String[] args) throws IOException, GeneralSecurityException, URISyntaxException,
			InstantiationException, IllegalAccessException {
		SpringApplication.run(Launcher.class, args);
	}

	@Bean
	public static Config loadConfig() {

		return new Config();
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("lancement de l'APP");

	}
}
