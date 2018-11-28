package fr.ynov.dap.dap;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The Class launcher.
 */
@SpringBootApplication
public class launcher {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public static void main(String... args) throws IOException, GeneralSecurityException {
		SpringApplication.run(launcher.class, args);
		loadConfig();
	}

	/**
	 * Load config.
	 *
	 * @return the config
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	@Bean
	public static Config loadConfig() throws IOException, GeneralSecurityException {
		/**
		 * Définir un dossier projet par défault dap
		 * Définir un dossier racine par défaut racine
		 * Définir un dossier tokens par défaut tokens
		 * L appli placera les storedCredentials dans le dossier tokens défini
		 * Placer votre fichier credentials.json dans la variable credentials ici le fichier s appelle cred.json
		 */
		Config configuration = new Config();
		String homePath = System.getProperty("user.home");
		String path = homePath+ File.separator +"dap/cred.json";
		String project = "dap";
		new java.io.File(homePath, project).mkdirs();
		String racinePath = homePath + File.separator + project;
		String racine = "racine";
		new java.io.File(racinePath, racine).mkdirs();
		String tokensPath = racinePath + File.separator + racine + File.separator;
		String tokens = "tokens";
		new java.io.File(tokensPath, tokens).mkdirs();
		String pathHome = homePath + File.separator + project + File.separator + racine + File.separator;
		String credentials = "cred.json";

		
		return configuration;
	}

}