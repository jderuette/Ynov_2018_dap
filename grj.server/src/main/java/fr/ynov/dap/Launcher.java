package fr.ynov.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Launcher {

    public static void main(String... args) {

        SpringApplication.run(Launcher.class, args);

    }

    /**
     * Return current API configuration.
     * @return Configuration
     */
    @Bean
    public Configuration loadConfig() {
        return new Configuration();
    }

}
