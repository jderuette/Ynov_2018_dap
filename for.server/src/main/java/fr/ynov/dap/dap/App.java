package fr.ynov.dap.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO for by DJer Evite de mélanger, tu peux mettre ton "loginSucefful" ailleur !
@RestController
@SpringBootApplication
public class App
{
    //TODO for by Djer Et utiliser de l'injection, pour éviter que toutes tes classes "metier" dépendent de App ?
  public static Config config;

  @Bean
  private static Config loadConfig() {
    return new Config();
  }

  public static void main( String[] args ) throws IOException, GeneralSecurityException {
    config = loadConfig();
    SpringApplication.run(App.class, args);
    System.out.println( "Hello World!" );
  }


  @RequestMapping("/loginSuccessful")
  private String loginSuccessful() {
    return "login successful.";
  }
}
