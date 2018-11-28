package fr.ynov.dap.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.ynov.dap.dap.config.Config;

/**
 * @author Florian BRANCHEREAU
 *
 */
@SpringBootApplication
public class Launcher {
    /**
     * @param args argument launcher
     * @throws Exception fonction
     */
    public static void main(final String[] args) throws Exception {
        SpringApplication.run(Launcher.class, args);
    }

    /**
     * @return Config
     */
    @Bean
    public Config config() {
        return new Config();
    }
}
