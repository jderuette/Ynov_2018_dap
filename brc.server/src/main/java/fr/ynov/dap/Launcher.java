package fr.ynov.dap;

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
    //TODO brc by Djer |Spring| Plus utile car ta classe config est annotée avec @Configuration, Spring va automatiquement en faire un singleton qu'il pourra injecter là ou c'est demandé
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
