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
		//TODO brs by Djer Pas besoin de l'appeler. Spring va détecter le @Bean et appeler la méthode
		// puis "stocker" le retour, pour pouvoir l'injecter par la suite.
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
	//TODO brs by Djer Tu n'a pas besoi nde l'appeller dans le "main" et donc pas besoin que cette méthode soit static
	public static config loadConfig() throws IOException, GeneralSecurityException {
		/**
		 * Définir un dossier projet par défault dap
		 * Définir un dossier racine par défaut racine
		 * Définir un dossier tokens par défaut tokens
		 * L appli placera les storedCredentials dans le dossier tokens défini
		 * Placer votre fichier credentials.json dans la variable credentials ici le fichier s appelle cred.json
		 */
	    //TODO brs by Djer La Config devrait avoir des valeur par defaut "raisonablement juste", pour limiter le code ci-dessous
	    // (lorque la configuration par defaut est acceptable)
		config configuration = new config();
		String homePath = System.getProperty("user.home");
		String project = "dap";
		new java.io.File(homePath, project).mkdirs();
		String racinePath = homePath + File.separator + project;
		//TODO brs by Djer le sous dossier "racine" ne semble pas très utile. c'est "{user.home}/dap" que l'appel "dossier racine".
		String racine = "racine";
		new java.io.File(racinePath, racine).mkdirs();
		String tokensPath = racinePath + File.separator + racine + File.separator;
		String tokens = "tokens";
		new java.io.File(tokensPath, tokens).mkdirs();
		//TODO brs by Djer le debut de "path" est Racine non ?
		String path = homePath + File.separator + project + File.separator + racine + File.separator;
		String credentials = "cred.json";

		configuration.setCREDENTIALS_FILE_PATH(path + credentials);
		configuration.setTOKENS_DIRECTORY_PATH(tokensPath + tokens);
		configuration.setAPPLICATION_NAME("DAP");
		return configuration;
	}

}