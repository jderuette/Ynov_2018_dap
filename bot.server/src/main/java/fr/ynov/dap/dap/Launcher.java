package fr.ynov.dap.dap;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The Class Launcher.
 */
@SpringBootApplication
public class Launcher {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Launcher.class, args);
	}
	
	/**
	 * Load conf.
	 *
	 * @return the config
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Bean
	public Config loadConf() throws IOException {
		return new Config();
	}
	
}
