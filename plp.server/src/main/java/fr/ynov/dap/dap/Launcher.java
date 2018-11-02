package fr.ynov.dap.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Pierre Plessy
 */
@SpringBootApplication
public class Launcher {
    /**
     * main function.
     *
     * @param args : args in launch jar.
     */
    public static void main(final String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

    /**
     * Instantiate config for app.
     * Can be add setter for credential folder and secret client file.
     *
     * @return Config : config app
     */
    @Bean
    public static Config loadConfig() {
        return new Config();
    }
}
