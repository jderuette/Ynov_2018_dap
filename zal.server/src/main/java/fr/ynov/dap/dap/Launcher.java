package fr.ynov.dap.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Launcher for SpringBootApp.
 * 
 * @author loic
 *
 */
@SpringBootApplication
public class Launcher {
	/**
	 * Instantiate config for project.
	 * 
	 * @return Config
	 */
    //TODO zal by DJer |Spring| Tu as mis un @Configuration sur ta classe Config, Spring va en faire un Singleton, cette méthode n'est plus utile
	@Bean
	public Config loadConfig() {
		return new Config();
	}

	/**
	 * Main function.
	 * 
	 * @param args
	 *            *arguments*
	 */
	public static void main(final String[] args) {
		SpringApplication.run(Launcher.class, args);
	}
}
