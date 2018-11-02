package fr.ynov.dap.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The Class Launcher.
 */
@SpringBootApplication
public class Launcher {
	
	/** The default user. */
    //TODO bot by Djer Evite de laisser trainer ton adresse email ! 
	public static String defaultUser = "thiba_aaaaaaa_@_aaaa_.com";
	
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
	 */
	@Bean
	public static Config loadConf() {
		return new Config();
	}
	
}
