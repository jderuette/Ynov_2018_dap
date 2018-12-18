
package fr.ynov.dap;


import java.io.IOException;
import java.security.GeneralSecurityException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


/**
 * Classe de démarrage de l'application
 * 
 * @author antod
 *
 */
@SpringBootApplication
public class Launcher
{
  /**
   * Variable pour logger des informations
   */
    //TODO dea by Djer |Log4J| Devrait être static
  private Logger logger = LogManager.getLogger();

  /**
   * Fonction lancée au démarrage.
   * 
   * @param args
   * @throws IOException
   * @throws GeneralSecurityException
   */
  public static void main(String... args) throws GeneralSecurityException, IOException
  {
    SpringApplication.run(Launcher.class, args);
  }

  /**
   * Fonction commandLineRunner
   * 
   * @param ctx
   * @return
   */
  public CommandLineRunner commandLineRunner(ApplicationContext ctx)
  {

    return args -> {

      this.logger.info("inFo : Lacement de spring");

    };
  }

  /**
   * Charge la variable config
   * 
   * @return
   */
  //@Bean
  public Config loadConfig()
  {
    Config config = new Config();

    config.setApplicationName("Application de Anthony");
    config.setRacineFolder(System.getProperty("user.home") + System.getProperty("file.separator") + "eclipse-workspace"
        + System.getProperty("file.separator") + "data");
    config.setCredentialFolder("");

    return config;
  }
  
  @Bean
  public Config loadConfigDjer()
  {
    Config config = new Config();

    config.setApplicationName("Application de Jérémie");
    config.setRacineFolder(System.getProperty("user.home") + System.getProperty("file.separator") + "dap"
        + System.getProperty("file.separator") + "dea");
    config.setCredentialFolder("");

    return config;
  }
}
