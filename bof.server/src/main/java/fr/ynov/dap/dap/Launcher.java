package fr.ynov.dap.dap;

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
	    //TODO bif By Djer LE TODO ci-desosus n'est plsu valide, a supprimer !
		// TODO Auto-generated method stub
		SpringApplication.run(Launcher.class, args);
	}

}
