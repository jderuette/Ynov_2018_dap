package fr.ynov.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Entry point of the application.
 */
@SpringBootApplication
public class Launcher {

    /**
     * create a config used by the GoogleService.
     * You can configure it here (use the set functions)
     * @return config that will be instantiate thanks to the Autowired of Spring.
     */
    @Bean
    public Config loadConfig() {
        return new Config();
    }

    /**
     * Main function. Start of the application.
     * @param args Arguments.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

}
