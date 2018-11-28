package fr.ynov.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Launcher.
 * Class who start App.
 */
@SpringBootApplication
public class Launcher {
    /**
     * Main function.
     * @param args arguments
     * @throws IOException File problem
     * @throws GeneralSecurityException Security problem
     */
    public static void main(final String[] args) throws IOException, GeneralSecurityException {
        SpringApplication.run(Launcher.class, args);
    }
}
