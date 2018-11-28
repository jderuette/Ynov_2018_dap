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
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public static void main(String... args) throws IOException, GeneralSecurityException {
		SpringApplication.run(Launcher.class, args);
	}

	/**
	 * Handle the Google response.
	 *
	 * @return Config
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	@Bean
	Config loadConfig() {
		Config maConfig = new Config();

		/**
		 * To Use Zero Conf maConfig.setCredentialPath("{Your path}");
		 *	maConfig.setTokensPath("{Your path}");
		 */
		return maConfig;
	}
}
