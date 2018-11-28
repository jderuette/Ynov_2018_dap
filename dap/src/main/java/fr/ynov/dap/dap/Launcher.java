package fr.ynov.dap.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author David_tepoche
 *
 */

@SpringBootApplication
public class Launcher {
    /**
     * @param args command line arg given
     */
    public static void main(final String... args) {
        SpringApplication.run(Launcher.class, args);

        System.out.println(" - - - - - - - - - - - - SERVEUR LAUNCH !!!! - - - - - - - - - - - -");
    }

    /**
     * inject config.
     *
     * @return the config
     */
    @Bean
    public Config loadConfig() {
        return new Config();
    }
}
