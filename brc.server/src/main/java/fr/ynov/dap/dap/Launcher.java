package fr.ynov.dap.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The Class Launcher.
 *
 * @author clement.brossette
 */
@SpringBootApplication
public class Launcher {

    /**
     * Load config.
     *
     * @return the config
     */
    @Bean
    public Config loadConfig() {
    	return new Config();
    }

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
    	SpringApplication.run(Launcher.class, args);
	} 

}
