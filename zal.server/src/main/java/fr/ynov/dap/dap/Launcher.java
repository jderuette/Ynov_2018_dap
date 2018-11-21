package fr.ynov.dap.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

import fr.ynov.dap.dap.data.AppUserRepository;

/**
 * Launcher for SpringBootApp.
 * @author loic
 *
 */
@SpringBootApplication
@EntityScan("fr.ynov.dap.dap.data")
public class Launcher {
    /**
     * Instantiate config for project.
     * @return Config
     */
    @Bean
    public Config loadConfig() {
       return new Config();
    }
    /**
     * Main function.
     * @param args *arguments*
     */
    public static void main(final String[] args) {
        SpringApplication.run(Launcher.class, args);
    }
}
