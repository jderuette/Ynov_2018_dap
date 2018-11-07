package fr.ynov.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.ynov.dap.config.Config;

/**
 * . This class launch your application, in our case it's our server
 *
 * @author Dom
 *
 */
@SpringBootApplication
public class Launcher {

    /**
     * . Launch class
     *
     * @param args .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    public static void main(final String... args) throws IOException, GeneralSecurityException {

        SpringApplication.run(Launcher.class, args);
    }

    /**
     * . loadConfig is call at the beginning of the launch application and inject
     * all the field with annotation Autowired
     *
     * @return .
     */
    @Bean
    public Config loadConfig() {
        return new Config();
    }

    /*
     * Ici un utilisateur pourra passer sa propre config
     *
     * @Bean public Config loadConfig(String applicationName,String
     * credentialsFilePath,String tokensDirectoryPath) { return new
     * Config(applicationName,credentialsFilePath,tokensDirectoryPath); }
     */

}
