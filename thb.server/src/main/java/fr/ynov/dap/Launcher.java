package fr.ynov.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Launcher {
    /**
     * The main method. 
     *
     * @param args the arguments
     */
    public static void main(String... args) {
        SpringApplication.run(Launcher.class, args);
    }
    
    @Bean
	Config loadConfig() {
		return new Config();
	}
}