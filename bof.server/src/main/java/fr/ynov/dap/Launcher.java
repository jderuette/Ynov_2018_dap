package fr.ynov.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 
 * @author Florent
 * Class who launch the spring boot application
 */
@SpringBootApplication
public class Launcher {
	
	/**
	 * Method who create a new instance of config.
	 * @return a new instance of config
	 */
	@Bean
	public static Config loadConfig() {
		return new Config();
	}
	
	/**
	 * 
	 * @param args
	 * @throws Exception
	 * Method who launch springBoot app
	 */
	public static void main(String[] args) throws Exception {
	    //TODO bof by Djer |IDE| Supprime ce TO-DO s'il n'est plus vrai
		// TODO Auto-generated method stub
		SpringApplication.run(Launcher.class, args);
	}

}
