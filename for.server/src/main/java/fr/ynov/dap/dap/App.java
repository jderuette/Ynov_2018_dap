package fr.ynov.dap.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App
{
    //TODO for by Djer |IOC| Devrait être privée. Pour acceder à la config dans les autres classes, utilise de l'injection !
  public static Config config = null;

  @Bean
  private static Config loadConfig() throws IOException {
    return new Config();
  }

  
  public static void main( String[] args ) throws IOException, GeneralSecurityException {
    config = new Config();
    SpringApplication.run(App.class, args);
    //TODO for by Djer |Rest API| Evite les SysOut sur un serveur !
    System.out.println( "Hello World!" );
  }
}
