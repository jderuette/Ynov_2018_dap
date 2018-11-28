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
